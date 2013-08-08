package nl.tudelft.wis.usem.plugin.access_management;

import nl.tudelft.wis.usem.plugin.osgi.access_management.OSGIPluginAccessManager;

public class PluginAccessManagerFactory {

	private static PluginAccessManager pluginAccessManager;

	public static PluginAccessManager getPluginManager() {
		if (pluginAccessManager == null)
			pluginAccessManager = new OSGIPluginAccessManager();
		return pluginAccessManager;
	}
}
