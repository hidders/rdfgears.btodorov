package nl.tudelft.wis.usem.plugin.osgi.access_management;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.tudelft.wis.usem.plugin.access_management.PluginAccessManager;
import nl.tudelft.wis.usem.plugin.osgi.Utils;

import org.eclipse.osgi.framework.internal.core.Constants;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.util.tracker.ServiceTracker;

public class OSGIPluginAccessManager implements PluginAccessManager {
	
	private Framework framework;

	public OSGIPluginAccessManager(){
		System.out.println("OSGI plugin access framework started!");
		try {
			Map<String, String> config = new HashMap<String, String>();
			config.put(
					Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA,
					"nl.tudelft.rdfgears.engine,nl.tudelft.rdfgears.rgl.datamodel.type,nl.tudelft.rdfgears.rgl.datamodel.value,nl.tudelft.rdfgears.rgl.function,nl.tudelft.rdfgears.util.row,nl.tudelft.rdfgears.plugin");
			framework = Utils.startFramework(config);

			BundleContext context = framework.getBundleContext();

			for (Bundle bundle : context.getBundles()) {
				bundle.start();
			}
		} catch (BundleException e) {
			e.printStackTrace();
		}
	}

	@Override
	public <E> List<E> getServices(Class<E> type) {
		Object[] services;
		try {
			ServiceTracker tracker = new ServiceTracker(
					framework.getBundleContext(), type, null);
			tracker.open();
			services = tracker.getServices();

		} catch (Exception e) {
			e.printStackTrace();
			return Collections.EMPTY_LIST;
		}

		List<E> result = new ArrayList<E>();

		if (services != null) {
			for (Object obj : services) {
				result.add((E) obj);
			}
		}

		return result;
	}
	
	@Override
	public void refresh() {
		try {
			framework.update();
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
