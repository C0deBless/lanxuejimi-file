package com.trnnn.osgi.admin.bundle.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.osgi.framework.BundleException;

import com.trnnn.osgi.admin.BundleFactory;
import com.trnnn.tools.FileParser;

public class InstallBundle extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String bundleRoot = "E:\\bundles\\";

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		try {

			DiskFileItemFactory fileFactory = new DiskFileItemFactory();

			ServletFileUpload fileUpload = new ServletFileUpload(fileFactory);
			List<FileItem> files = fileUpload.parseRequest(request);
			for (FileItem item : files) {
				String uploadFileName = item.getName();
				FileParser fileParser = new FileParser();
				String fileExt = fileParser.getFileExt(item.getName());
				File bundleRootFile = new File(bundleRoot);
				if (!bundleRootFile.exists()) {
					bundleRootFile.mkdirs();
				}
				String filePath;
				if (item.getName().indexOf(":\\") >= 0) {
					uploadFileName = uploadFileName.substring(
							uploadFileName.lastIndexOf("\\"),
							uploadFileName.length());
				} else {
					uploadFileName = item.getName();
				}
				filePath = bundleRoot + uploadFileName;
				File file = new File(filePath);
				item.write(file);
				if (fileExt.equalsIgnoreCase("jar")
						|| fileExt.equalsIgnoreCase("war")) {

					BundleFactory.install(filePath);
				} else if (fileExt.equalsIgnoreCase("zip")) {
					// Install Web Bundle here
					// String tmpRoot = bundleRoot + "temp\\";
					// String bundlePath = tmpRoot
					// + uploadFileName.substring(0,
					// uploadFileName.lastIndexOf(".")) + "\\";
					// ZipReader zipReader = new ZipReader(filePath, tmpRoot);
					// zipReader.read();
					// ProjectCompiler projectCompiler = new ProjectCompiler(
					// bundlePath, "test");
					// projectCompiler.compile(false);
					// BundleFactory.install(bundlePath);
				}
			}
			out.print(true);
		} catch (FileUploadException e1) {
			out.print(false);
			e1.printStackTrace();
		} catch (BundleException e) {
			out.print(false);
			e.printStackTrace();
		} catch (Exception e) {
			out.print(false);
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
