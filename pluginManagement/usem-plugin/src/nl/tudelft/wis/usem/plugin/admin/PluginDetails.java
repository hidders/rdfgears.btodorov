package nl.tudelft.wis.usem.plugin.admin;

import org.osgi.framework.Version;

public class PluginDetails {

	private long bundleId;
	private String symbolicName;
	private Version version;
	private String vendor;

	public PluginDetails(long bundleId, String symbolicName, Version version, String vendor) {
		this.bundleId = bundleId;
		this.symbolicName = symbolicName;
		this.version = version;
		this.vendor = vendor;
	}

	public long getBundleId() {
		return bundleId;
	}

	public String getSymbolicName() {
		return symbolicName;
	}
	
	public Version getVersion() {
		return version;
	}
	
	public String getVendor() {
		return vendor;
	}

}
