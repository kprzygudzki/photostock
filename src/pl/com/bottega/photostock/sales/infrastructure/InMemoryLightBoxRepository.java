package pl.com.bottega.photostock.sales.infrastructure;

import com.sun.org.apache.regexp.internal.RE;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.LightBoxRepository;

import java.util.*;

public class InMemoryLightBoxRepository implements LightBoxRepository {

	private static final Map<Client, Collection<LightBox>> REPOSITORY = new HashMap<>();

	@Override
	public void put(LightBox lightBox) {
		Client owner = lightBox.getOwner();
		Collection<LightBox> lightBoxes = REPOSITORY.getOrDefault(owner, new HashSet<>());
		lightBoxes.add(lightBox);
		REPOSITORY.put(owner, lightBoxes);	// to jest chyba niepotrzebne?
	}

	@Override
	public Collection<LightBox> getFor(Client client) {
		return REPOSITORY.get(client);
	}

	@Override
	public LightBox findLightBox(Client client, String lightBoxName) {
		Collection<LightBox> lightBoxes = getFor(client);
		if (!(lightBoxes == null))
			for (LightBox lightBox : lightBoxes)
				if (lightBox.getName().equals(lightBoxName))
					return lightBox;
		return null;
	}

	@Override
	public Collection<String> getNamesFor(Client client) {
		Collection<String> lightBoxNames = new LinkedList<>();
		for (LightBox lightBox : getFor(client))
			lightBoxNames.add(lightBox.getName());
		return lightBoxNames;
	}
}
