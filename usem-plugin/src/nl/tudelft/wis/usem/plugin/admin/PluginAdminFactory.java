package nl.tudelft.wis.usem.plugin.admin;

import nl.tudelft.wis.usem.plugin.osgi.admin.OSGIPluginAdmin;

public class PluginAdminFactory {
	public static PluginAdmin getPluginManager(){
		return new OSGIPluginAdmin();
	}
	
}
