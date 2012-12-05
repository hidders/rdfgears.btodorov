package nl.tudelft.wis.usem.plugin.admin;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.tudelft.wis.usem.plugin.repository.PluginRepository;
import nl.tudelft.wis.usem.plugin.repository.PluginRepositoryFactory;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

public class PluginAdminUIHandler {

	public boolean installPlugin(HttpServletRequest request) {
		try {
			String user = null;

			InputStream plugin = null;
			String pluginName = null;

			List<FileItem> items = new ServletFileUpload(
					new DiskFileItemFactory()).parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					String fieldname = item.getFieldName();

					if (fieldname.equals("user")) {
						user = item.getString();
					}

				} else {
					if (item.getFieldName().equals("pluginfile")) {
						pluginName = item.getName();
						plugin = item.getInputStream();
					}
				}
			}
			
			PluginAdmin pluginAdmin = SessionUtils.getPluginAdminFromSession(request.getSession());
			pluginAdmin.installPlugin(pluginName,
					plugin);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean installFromRepository(HttpServletRequest request, String key) {
		PluginRepository repo = PluginRepositoryFactory.getRepository();

		try {
			PluginAdmin pluginAdmin = SessionUtils.getPluginAdminFromSession(request.getSession());
			
			pluginAdmin.installPlugin(
					repo.getPluginName(null, key), repo.getPlugin(null, key));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
