package nl.tudelft.wis.usem.plugin.osgi.admin;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.wis.usem.plugin.admin.PluginAdmin;
import nl.tudelft.wis.usem.plugin.admin.PluginDetails;
import nl.tudelft.wis.usem.plugin.osgi.Utils;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;

public class OSGIPluginAdmin implements PluginAdmin{
	
	private Framework framework;

	public OSGIPluginAdmin(){
		try {
			System.out.println("OSGI plugin framework started!");
			framework = Utils.startFramework(null);
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<PluginDetails> listPlugins() {
		try {
			BundleContext context = framework.getBundleContext();

			List<PluginDetails> boundles = new ArrayList<PluginDetails>();
			for (Bundle bundle : context.getBundles()) {
				boundles.add(new PluginDetails(bundle.getBundleId(), bundle
						.getSymbolicName(), bundle.getVersion(), "N/A"));
			}

			return boundles;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deletePlugin(String id) {
		try {
			BundleContext context = framework.getBundleContext();

			context.getBundle(Long.valueOf(id)).uninstall();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void installPlugin(String name, InputStream plugin) throws Exception {
		BundleContext context = framework.getBundleContext();
		Bundle bundle = context.installBundle(name, plugin);
		
		framework.update();
	}
	
}
