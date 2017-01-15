package pl.com.bottega.photostock.sales.infrastructure.csv;

import com.sun.deploy.util.StringUtils;
import pl.com.bottega.photostock.sales.model.client.Transaction;

import java.io.File;
import java.io.PrintWriter;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;

class CSVTransactionsRepository {

	private String folderPath;

	public CSVTransactionsRepository(String folderPath) {
		this.folderPath = folderPath;
	}

	public void saveTransactions(String clientNumber, Collection<Transaction> transactions) {
		try (
				PrintWriter writer = new PrintWriter(getFilePath(clientNumber))
		) {
			for (Transaction transaction : transactions) {
				writer.println(createRecord(transaction));
			}
		} catch (Exception ex) {
			throw new DataAccessException(ex);
		}
	}

	private String createRecord(Transaction transaction) {
		String[] attributes = {
				transaction.getHajsik().toString(),
				transaction.getDescription(),
				transaction.getTimestamp().format(DateTimeFormatter.ISO_DATE_TIME)
		};
		return StringUtils.join(Arrays.asList(attributes), ",");
	}

	public Collection<Transaction> getTransactions(String clientNumber) {
		return null;
	}

	private String getFilePath(String clientNumber) {
		return folderPath + File.separator + "transactionRepositories" + File.separator
				+ clientNumber + "-client-transactions.csv";
	}

}
