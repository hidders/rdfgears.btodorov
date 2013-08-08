package nl.tudelft.wis.usem.plugin.access_management;

import java.util.List;


public interface PluginAccessManager {
	
	public <E> List<E> getServices(Class<E> type);

	public void refresh();

}
