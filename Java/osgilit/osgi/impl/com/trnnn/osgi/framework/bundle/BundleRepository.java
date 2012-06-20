package com.trnnn.osgi.framework.bundle;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipException;

import com.trnnn.osgi.manifest.ManifestFactory;

public class BundleRepository {

	private final Set<URL> repository = Collections
			.synchronizedSet(new HashSet<URL>());
	private final Set<BundleEntry> entrys = Collections
			.synchronizedSet(new HashSet<BundleEntry>());
	private final ManifestFactory manifestFactory = new ManifestFactory();

	private List<AbstractBundle> bundles;
	private int repositorySize = 20;

	public BundleRepository(URL[] urls, int repositorySize) {
		this(urls);
		this.repositorySize = repositorySize;
	}

	public BundleRepository(URL[] urls) {

		if (urls == null)
			throw new IllegalArgumentException("urls cannot be null value");

		this.repository.clear();
		for (URL url : urls) {
			this.repository.add(url);
		}
		build();
	}

	public void addBundle(AbstractBundle bundle) {
		this.bundles.add(bundle);
	}

	private void build() {
		this.entrys.clear();
		this.bundles = new ArrayList<AbstractBundle>(repositorySize);
		for (URL url : this.repository) {
			String protocol = url.getProtocol();
			// url.openConnection().get

			if (protocol.equalsIgnoreCase("http")
					|| protocol.equalsIgnoreCase("ftp")) {
				URLConnection conn;
				try {
					conn = url.openConnection();
					conn.connect();
					// InputStream in = conn.getInputStream();
					// TODO: handle remote bundle here;
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else if (protocol.equalsIgnoreCase("file")) {
				try {
					File file = new File(url.toURI());
					if (!file.exists()) {
						continue;
					}
					if (file.isDirectory()) {
						this.entrys.add(new BundleEntry(file, url));
					} else if (file.isFile()) {
						try {
							JarFile jar = new JarFile(file);
							// this.jarFiles.add(jar);
							this.entrys.add(new BundleEntry(jar, url));
						} catch (ZipException e) {

						}

					}
				} catch (URISyntaxException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public Set<BundleEntry> entrys() {
		return this.entrys;
	}

	public static void main(String[] args) throws IOException {
		// URL url = new URL("http://www.baidu.com");
		// URLConnection conn = url.openConnection();
		// conn.connect();
		// InputStream in = conn.getInputStream();
		// BufferedReader reader = new BufferedReader(new
		// InputStreamReader(in));
		// String str;
		// while ((str = reader.readLine()) != null) {
		// System.out.println(str);
		// }
		// File file = new File("d:\\N46_E009_SERVER.XML");
		// JarFile jar = new JarFile(file);
	}

	public class BundleEntry {
		public URL url;
		public Object entry;
		public Manifest manifest;

		private BundleEntry(Object object, URL url) {
			if (object == null)
				throw new IllegalArgumentException(
						"object parameter cannot be null value");
			if (url == null)
				throw new IllegalArgumentException(
						"url parameter cannot be null value");

			this.entry = object;
			this.url = url;
			build();
		}

		private void build() {

			// ManifestFactory manifestFactory =
			// BundleRepository.this.manifestFactory;

			if (entry instanceof File) {
				File file = (File) entry;
				if (!file.isDirectory()) {
					throw new IllegalArgumentException("directory only");
				}
				this.manifest = manifestFactory.getManifest(file.getPath());
			} else if (entry instanceof JarFile) {
				try {
					this.manifest = manifestFactory
							.getManifest((JarFile) entry);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				throw new IllegalStateException("unknown entry type. ->> "
						+ entry.getClass());
			}

		}

		// private void buildBundleData() {
		// if (this.manifest != null) {
		// // this.bundleData = BundleData.build(this.manifest);
		// }
		// }
	}

}
