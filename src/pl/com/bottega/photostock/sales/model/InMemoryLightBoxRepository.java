package pl.com.bottega.photostock.sales.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class InMemoryLightBoxRepository implements LightBoxRepository {

	private static final Map<Client, Collection<LightBox>> REPOSITORY = new HashMap<>();

	@Override
	public void put(LightBox lightBox) {
		Client owner = lightBox.getOwner();
		REPOSITORY.putIfAbsent(owner, new HashSet<>());
	}

	@Override
	public Collection<LightBox> getFor(Client client) {
		Collection<LightBox> temporaryStorage = new HashSet<>();

		if (REPOSITORY.containsKey(client))
			temporaryStorage.addAll(REPOSITORY.get(client));
		else
			throw new IllegalArgumentException("There are no LightBoxes stored for this client.");

		return temporaryStorage;
	}
}
