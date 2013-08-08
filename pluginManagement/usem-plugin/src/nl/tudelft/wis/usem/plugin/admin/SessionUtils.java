package nl.tudelft.wis.usem.plugin.admin;

import javax.servlet.http.HttpSession;

public class SessionUtils {

	public static PluginAdmin getPluginAdminFromSession(HttpSession session){
		PluginAdmin pluginAdmin = (PluginAdmin) session.getAttribute("pluginAdmin");
		if(pluginAdmin == null){
			pluginAdmin = PluginAdminFactory.getPluginManager();
			session.setAttribute("pluginAdmin", pluginAdmin);
		}
		return pluginAdmin;
	}
}
