package com.trnnn.osgi.admin.bundle.model;


public class BundleInfoModel {

	private long bundleId;
	private String bundleName;
	private String bundleSymbolicName;
	private String bundleVersion;
	private String bundleStatus;
	private String bundlePath;
	private ServiceReferenceMin[] serviceReferences;
	private ServiceReferenceMin[] serviceInUse;
	private String manifestVersion;
	private String bundleManifestVersion;
	private String bundleClassPath;
	private String bundleActivator;
	private String bundleLocalization;
	private String exportPackage;
	private String bundleVendor;
	private String eclipseSourceReferences;
	private String eclipseSystemBundle;
	private String bundleRequiredExecutionEnvironment;
	private String bundleDocUrl;
	private String bundleCopyright;
	private String bundleDescription;
	private String bundleExportService;
	private String eclipseExtensibleAPI;
	private String eclipseBundleShape;
	private String importPackage;
	private String createdBy;
	private String antVersion;
	private String requireBundle;
	private String bundleClasspath;


	public String getBundleActivator() {
		return bundleActivator;
	}

	public void setBundleActivator(String bundleActivator) {
		this.bundleActivator = bundleActivator;
	}

	public String getBundleLocalization() {
		return bundleLocalization;
	}

	public void setBundleLocalization(String bundleLocalization) {
		this.bundleLocalization = bundleLocalization;
	}

	public String getEclipseSourceReferences() {
		return eclipseSourceReferences;
	}

	public void setEclipseSourceReferences(String eclipseSourceReferences) {
		this.eclipseSourceReferences = eclipseSourceReferences;
	}

	public String getEclipseSystemBundle() {
		return eclipseSystemBundle;
	}

	public void setEclipseSystemBundle(String eclipseSystemBundle) {
		this.eclipseSystemBundle = eclipseSystemBundle;
	}

	public String getBundleRequiredExecutionEnvironment() {
		return bundleRequiredExecutionEnvironment;
	}

	public void setBundleRequiredExecutionEnvironment(
			String bundleRequiredExecutionEnvironment) {
		this.bundleRequiredExecutionEnvironment = bundleRequiredExecutionEnvironment;
	}

	public String getBundleDocUrl() {
		return bundleDocUrl;
	}

	public void setBundleDocUrl(String bundleDocUrl) {
		this.bundleDocUrl = bundleDocUrl;
	}

	public String getBundleCopyright() {
		return bundleCopyright;
	}

	public void setBundleCopyright(String bundleCopyright) {
		this.bundleCopyright = bundleCopyright;
	}

	public String getBundleDescription() {
		return bundleDescription;
	}

	public void setBundleDescription(String bundleDescription) {
		this.bundleDescription = bundleDescription;
	}

	public String getBundleExportService() {
		return bundleExportService;
	}

	public void setBundleExportService(String bundleExportService) {
		this.bundleExportService = bundleExportService;
	}

	public String getEclipseExtensibleAPI() {
		return eclipseExtensibleAPI;
	}

	public void setEclipseExtensibleAPI(String eclipseExtensibleAPI) {
		this.eclipseExtensibleAPI = eclipseExtensibleAPI;
	}

	public String getEclipseBundleShape() {
		return eclipseBundleShape;
	}

	public void setEclipseBundleShape(String eclipseBundleShape) {
		this.eclipseBundleShape = eclipseBundleShape;
	}

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public String getBundleVersion() {
		return bundleVersion;
	}

	public void setBundleVersion(String bundleVersion) {
		this.bundleVersion = bundleVersion;
	}

	public long getBundleId() {
		return bundleId;
	}

	public void setBundleId(long bundleId) {
		this.bundleId = bundleId;
	}

	public String getBundleStatus() {
		return bundleStatus;
	}

	public void setBundleStatus(String bundleStatus) {
		this.bundleStatus = bundleStatus;
	}

	public String getBundlePath() {
		return bundlePath;
	}

	public void setBundlePath(String bundlePath) {
		this.bundlePath = bundlePath;
	}

	public void setServiceReferences(ServiceReferenceMin[] serviceReferences) {
		this.serviceReferences = serviceReferences;
	}

	public ServiceReferenceMin[] getServiceReferences() {
		return serviceReferences;
	}

	public void setServiceInUse(ServiceReferenceMin[] serviceInUse) {
		this.serviceInUse = serviceInUse;
	}

	public ServiceReferenceMin[] getServiceInUse() {
		return serviceInUse;
	}

	public String getBundleSymbolicName() {
		return bundleSymbolicName;
	}

	public void setBundleSymbolicName(String bundleSymbolicName) {
		this.bundleSymbolicName = bundleSymbolicName;
	}

	public String getImportPackage() {
		return importPackage;
	}

	public void setImportPackage(String importPackage) {
		this.importPackage = importPackage;
	}

	public String getBundleManifestVersion() {
		return bundleManifestVersion;
	}

	public void setBundleManifestVersion(String bundleManifestVersion) {
		this.bundleManifestVersion = bundleManifestVersion;
	}

	public String getExportPackage() {
		return exportPackage;
	}

	public void setExportPackage(String exportPackage) {
		this.exportPackage = exportPackage;
	}

	public String getBundleVendor() {
		return bundleVendor;
	}

	public void setBundleVendor(String bundleVendor) {
		this.bundleVendor = bundleVendor;
	}

	public void setManifestVersion(String manifestVersion) {
		this.manifestVersion = manifestVersion;
	}

	public String getManifestVersion() {
		return manifestVersion;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setAntVersion(String antVersion) {
		this.antVersion = antVersion;
	}

	public String getAntVersion() {
		return antVersion;
	}

	public void setBundleClassPath(String bundleClassPath) {
		this.bundleClassPath = bundleClassPath;
	}

	public String getBundleClassPath() {
		return bundleClassPath;
	}

	public void setRequireBundle(String requireBundle) {
		this.requireBundle = requireBundle;
	}

	public String getRequireBundle() {
		return requireBundle;
	}

	public void setBundleClasspath(String bundleClasspath) {
		this.bundleClasspath = bundleClasspath;
	}

	public String getBundleClasspath() {
		return bundleClasspath;
	}

}
