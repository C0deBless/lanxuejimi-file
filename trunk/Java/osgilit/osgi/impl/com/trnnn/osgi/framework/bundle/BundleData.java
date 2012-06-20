package com.trnnn.osgi.framework.bundle;

import java.net.URL;
import java.util.Date;
import java.util.Dictionary;

import org.osgi.framework.Version;

import com.trnnn.osgi.framework.bundle.BundleRepository.BundleEntry;

public class BundleData {

	/** The BundleData is for a fragment bundle */
	public static final int TYPE_FRAGMENT = 0x00000001;
	/** The BundleData is for a framework extension bundle */
	public static final int TYPE_FRAMEWORK_EXTENSION = 0x00000002;
	/** The BundleData is for a bootclasspath extension bundle */
	public static final int TYPE_BOOTCLASSPATH_EXTENSION = 0x00000004;
	/** The BundleData is for a singleton bundle */
	public static final int TYPE_SINGLETON = 0x00000008;
	/** The BundleData is for an extension classpath bundle */
	public static final int TYPE_EXTCLASSPATH_EXTENSION = 0x00000010;
	/** The BundleData is for a composite bundle */
	public static final int TYPE_COMPOSITEBUNDLE = 0x00000020;
	/** The BundleData is for a composite bundle surrogate */
	public static final int TYPE_SURROGATEBUNDLE = 0x00000040;

	private AbstractBundle bundle;
	private int id;
	private Version version;
	private URL location;
	private Dictionary<String, String> headers;
	private int state;
	private String symbolicName;
	private long lastModified;
	private int type = TYPE_COMPOSITEBUNDLE;

	public static BundleData wrapBundleData(int bundleId,
			Dictionary<String, String> headers, BundleEntry entry) {
		BundleData bundleData = new BundleData();
		bundleData.headers = headers;
		bundleData.id = bundleId;
		bundleData.lastModified = new Date().getTime();
		bundleData.location = entry.url;
		return bundleData;
	}

	public int getType() {
		return this.type;
	}

	public void setBundle(AbstractBundle bundle) {
		this.bundle = bundle;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public URL getLocation() {
		return location;
	}

	public void setLocation(URL location) {
		this.location = location;
	}

	public Dictionary<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Dictionary<String, String> headers) {
		this.headers = headers;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getSymbolicName() {
		return symbolicName;
	}

	public void setSymbolicName(String symbolicName) {
		this.symbolicName = symbolicName;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public AbstractBundle getBundle() {
		return bundle;
	}

	public void setType(int type) {
		this.type = type;
	}

}