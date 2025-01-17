package nl.tudelft.wis.usem.plugin.repository.localrepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import nl.tudelft.wis.usem.plugin.repository.PluginRepository;

import org.apache.tomcat.util.http.fileupload.IOUtils;

public class LocalPluginRepository implements PluginRepository {
	
	private static final String REPOSITORY_FOLDER = "../temp/pluginRepository";

	@Override
	public String listRepositoryContents(Map<String, Object> properties) {

		File repoFolder = new File(REPOSITORY_FOLDER);

		System.out.println(repoFolder.getAbsolutePath());

		if (!repoFolder.isDirectory()) {
			return "";
		}

		JSONArray arr = new JSONArray();

		browseFolder(repoFolder, repoFolder, arr);

		return arr.toString();
	}

	private void browseFolder(File root, File folder, JSONArray arr) {

		for (File f : folder.listFiles()) {
			if (f.isFile() && f.getName().endsWith(".jar")) {
				JSONObject obj = new JSONObject();
				obj.accumulate("title", f.getName());
				obj.accumulate("key",
						folder.getPath().replace(root.getPath(), "")
								+ File.separator + f.getName());
				arr.add(obj);
			} else if (f.isDirectory()) {
				JSONObject obj = new JSONObject();
				obj.accumulate("title", f.getName());
				obj.accumulate("isFolder", true);
				JSONArray children = new JSONArray();
				browseFolder(root, f, children);
				obj.accumulate("children", children);
				arr.add(obj);
			}
		}
	}

	@Override
	public InputStream getPlugin(Map<String, Object> properties, String key) throws Exception {
		File plugin = new File(LocalPluginRepository.REPOSITORY_FOLDER + key);

		return new FileInputStream(plugin);
	}
	
	@Override
	public String getPluginName(Map<String, Object> properties, String key) throws Exception {
		return  new File(LocalPluginRepository.REPOSITORY_FOLDER + key).getName();
	}
	
	public boolean publishlPlugin(String folder, String pluginName, InputStream plugin) {
		try {
			File publishFolder = new File(REPOSITORY_FOLDER + File.separator
					+ folder.trim());

			if (!publishFolder.exists()) {
				publishFolder.mkdirs();
			}

			IOUtils.copy(plugin, new FileOutputStream(new File(publishFolder,
					pluginName)));

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
