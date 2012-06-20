package com.trnnn.osgi.framework.bundle;

import java.util.Dictionary;
import java.util.Set;

import com.trnnn.osgi.framework.Framework;
import com.trnnn.osgi.framework.bundle.BundleRepository.BundleEntry;
import com.trnnn.osgi.manifest.ManifestFactory;

public class BundleInstaller {

	private Framework framework;
	private final ManifestFactory manifestFactory = new ManifestFactory();

	public BundleInstaller(Framework framework) {
		this.framework = framework;
	}

	public void installBundle(BundleData bundleData) {
		AbstractBundle bundle = AbstractBundle.createBundle(bundleData,
				framework, true);
		framework.bundleRepository().addBundle(bundle);
	}

	public void installAll() {
		this.framework.setFrameworkState(Framework.FRAMEWORK_STATE_LOADING);
		BundleRepository bundleRepository = this.framework.bundleRepository();
		Set<BundleEntry> entrys = bundleRepository.entrys();
		for (BundleEntry entry : entrys) {
			framework.console().println(
					"[BundleInstaller.installAll] installing "
							+ entry.url.toString());
			Dictionary<String, String> headers = manifestFactory
					.parseHeaders(entry.manifest);
			int bundleId = this.framework.generateBundleId();
			BundleData bundleData = BundleData.wrapBundleData(bundleId,
					headers, entry);
			BundleInstaller installer = this.framework.bundleInstaller();
			installer.installBundle(bundleData);
		}
		this.framework.setFrameworkState(Framework.FRAMEWORK_STATE_LOADED);
	}
}
