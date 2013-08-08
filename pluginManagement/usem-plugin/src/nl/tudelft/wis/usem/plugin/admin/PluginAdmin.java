package nl.tudelft.wis.usem.plugin.admin;

import java.io.InputStream;
import java.util.List;

import nl.tudelft.wis.usem.plugin.admin.PluginDetails;


public interface PluginAdmin {
	
	public void installPlugin(String name, InputStream plugin) throws Exception;
	
	public List<PluginDetails> listPlugins();
	
	public boolean deletePlugin(String id);

}
