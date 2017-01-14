package pl.com.bottega.photostock.sales.model;

import java.util.Collection;

public interface LightBoxRepository {
	void put (LightBox lightBox);
	Collection<LightBox> getFor(Client client);

	LightBox findLightBox(Client client, String lightBoxName);

	Collection<String> getNamesFor(Client client);
}
