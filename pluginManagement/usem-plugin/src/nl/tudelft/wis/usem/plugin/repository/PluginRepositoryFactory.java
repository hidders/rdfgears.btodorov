package nl.tudelft.wis.usem.plugin.repository;

import nl.tudelft.wis.usem.plugin.repository.localrepository.LocalPluginRepository;

public class PluginRepositoryFactory {
	public static PluginRepository getRepository() {
		return new LocalPluginRepository();
	}
}
