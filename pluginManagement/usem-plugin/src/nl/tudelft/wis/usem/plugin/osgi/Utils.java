package nl.tudelft.wis.usem.plugin.osgi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import org.eclipse.osgi.framework.internal.core.Constants;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

public class Utils {
	
	public static Framework startFramework(Map<String, String> additionalConfig) throws BundleException {
		FrameworkFactory frameworkFactory = ServiceLoader
				.load(FrameworkFactory.class).iterator().next();
		Map<String, String> config = new HashMap<String, String>();
		config.put(Constants.FRAMEWORK_STORAGE, getPluginDir());
		
		if(additionalConfig != null)
			config.putAll(additionalConfig);

		Framework framework = frameworkFactory.newFramework(config);
		framework.start();
		return framework;
	}

	public static void copyToFolder(InputStream inputStream, File dest)
			throws IOException {

		// write the inputStream to a FileOutputStream
		OutputStream out = new FileOutputStream(dest);

		int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = inputStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}

		inputStream.close();
		out.flush();
		out.close();
	}
	
	public static String getPluginDir() {
		File destdir = new File(System.getProperty("java.io.tmpdir")
			+ "/rdfgears/plugins");
		if (!destdir.exists())
		    destdir.mkdirs();

		return destdir.getAbsolutePath();
	    }

}
