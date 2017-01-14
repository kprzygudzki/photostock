package pl.com.bottega.photostock.sales.presentation.trash;

import pl.com.bottega.photostock.sales.infrastructure.InMemoryProductRepository;
import pl.com.bottega.photostock.sales.model.client.Address;
import pl.com.bottega.photostock.sales.model.client.Client;
import pl.com.bottega.photostock.sales.model.lightbox.LightBox;
import pl.com.bottega.photostock.sales.model.money.Money;
import pl.com.bottega.photostock.sales.model.product.Product;
import pl.com.bottega.photostock.sales.model.product.ProductRepository;

public class LightBoxTest {
	public static void main(String[] args) {

		ProductRepository productRepository = new InMemoryProductRepository();
		Product picture1 = productRepository.get("1");
		Product picture2 = productRepository.get("2");
		Product picture3 = productRepository.get("3");
		Product clip1 = productRepository.get("4");
		Product clip2 = productRepository.get("5");

		Client client1 = new Client("Zenon", new Address(), Money.valueOf(100));
		Client client2 = new Client("Justyna", new Address(), Money.valueOf(100));

		LightBox lb1 = new LightBox(client1, "Samochody");
		LightBox lb2 = new LightBox(client2, "Konie");
		LightBox lb3 = new LightBox(client1, "Kobiety");

		lb1.add(picture1);
		lb1.add(picture3);
		lb1.add(clip2);

		picture3.deactivate();

		lb2.add(picture1);
		lb2.add(picture2);
		lb2.add(clip1);

		lb3.add(picture1);

		LightBox lb4 = LightBox.joined(client1, "nowy", lb1, lb2, lb3);

		printLightboxes(lb1, lb2, lb3, lb4);
	}

	private static void printLightboxes(LightBox... lightBoxes) {
		int number = 1;
		for (LightBox lightBox : lightBoxes) {
			System.out.println(String.format("%d. %s - %s", number, lightBox.getName(), lightBox.getOwner().getName()));
			printLightbox(lightBox);
			number++;
		}
	}

	private static void printLightbox(LightBox lightBox) {
		for (Product product : lightBox) {
			System.out.println(
					String.format("%s%s | %s",
							product.isActive() ? "" : "X ",
							product.getNumber(),
							product.calculatePrice(lightBox.getOwner())
					)
			);
		}
	}
}