package nl.tudelft.wis.usem.plugin.repository;

import java.io.InputStream;
import java.util.Map;

public interface PluginRepository {

	public String listRepositoryContents(Map<String, Object> properties);

	public InputStream getPlugin(Map<String, Object> properties, String key) throws Exception;
	
	public String getPluginName(Map<String, Object> properties, String key) throws Exception;

}
