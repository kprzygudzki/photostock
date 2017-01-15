package pl.com.bottega.photostock.sales.infrastructure.csv;

import com.sun.deploy.util.StringUtils;
import pl.com.bottega.photostock.sales.model.client.*;
import pl.com.bottega.photostock.sales.model.money.Money;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class CSVClientRepository implements ClientRepository {

	private final String tempFilePath;
	private String filePath;
	private String folderPath;

	public CSVClientRepository(String folderPath) {
		this.folderPath = folderPath;
		this.filePath = folderPath + File.separator + "clients.csv";
		this.tempFilePath = folderPath + File.separator + "clients.temp.csv";
	}

	@Override
	// number,name,active,status,balance,creditLimit
	public Client get(String clientNumber) {
		try (
				BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))
		) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] attributes = line.trim().split(",");
				String number = attributes[0];
				if (number.equals(clientNumber)) {
					return createClient(attributes, number);
				}
			}
			return null;
		} catch (Exception ex) {
			throw new DataAccessException(ex);
		}
	}

	@Override
	public void update(Client client) {
		try (
				BufferedReader reader = new BufferedReader(new FileReader(filePath));
				PrintWriter writer = new PrintWriter(new FileWriter(tempFilePath))
		) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] attributes = line.trim().split(",");
				String number = attributes[0];
				if (number.equals(client.getNumber())) {
					writeClient(client, writer);
				} else
					writer.println(line);
			}

		} catch (Exception ex) {
			throw new DataAccessException(ex);
		}
		replaceFiles();
		updateTranscations(client);
	}

	private void updateTranscations(Client client) {
		CSVTransactionsRepository transactionsRepository = new CSVTransactionsRepository(folderPath);
		transactionsRepository.saveTransactions(client.getNumber(), client.getTransactions());
	}

	// number,name,active,status,balance,debitLimit
	private void writeClient(Client client, PrintWriter writer) {
		String[] attributes = {
				client.getNumber(),
				client.getName(),
				String.valueOf(client.isActive()),
				String.valueOf(client.getStatus()),
				String.valueOf(client.getBalance()),
				""
		};
		if (client instanceof VIPClient) {
			VIPClient vipClient = (VIPClient) client;
			attributes[5] = String.valueOf(vipClient.getDebitLimit());
		}
		writer.println(StringUtils.join(Arrays.asList(attributes), ","));

	}

	private Client createClient(String[] attributes, String number) {
		String name = attributes[1];
		boolean active = Boolean.valueOf(attributes[2]);
		ClientStatus status = ClientStatus.valueOf(attributes[3]);
		Money creditsBalance = Money.valueOf(attributes[4]);
		if (status.equals(ClientStatus.VIP)) {
			Money debitLimit = Money.valueOf(attributes[5]);
			return new VIPClient(number, name, new Address(), creditsBalance, debitLimit, active,
					new LinkedList<>());
		} else
			return new Client(number, name, new Address(), status, creditsBalance, active,
					new LinkedList<>());
	}

	private void replaceFiles() {
		File file = new File(tempFilePath);
		file.renameTo(new File(filePath));
	}
}
