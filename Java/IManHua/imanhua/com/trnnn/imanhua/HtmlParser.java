package com.trnnn.imanhua;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HtmlParser {

	public static final String rootPath = "http://www.imanhua.com";

	public static final String narutoPath = "http://www.imanhua.com/comic/54/";

	public static HtmlParser instance = new HtmlParser();

	private HtmlParser() {
	}

	public static HtmlParser getInstance() {
		return instance;
	}

	public String[] getBookList(String url) {
		try {
			String content = this.callGET(url);
			Document doc = Jsoup.parse(content);
			Element el = doc.getElementById("subBookList");
			Elements list = el.getElementsByTag("a");
			for (Element link : list) {
				String linkHref = rootPath + link.attr("href");
				String linkText = link.text();
				System.out.println(linkText + "-" + linkHref);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String[] {};
	}

	public String[] getImageList(String bookUrl) {
		return new String[] {};
	}

	public Bitmap getHttpBitmap(String url, String referer) {

		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Referer", referer);
			conn.connect();
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(is);
				is.close();
			} else {
				Log.e("IManHua", "response code " + conn.getResponseCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;

	}

	private String callGET(String url) throws IOException {
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept-Charset", "GBK");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setDoOutput(false);

		int code = conn.getResponseCode();
		if (code != 200) {
			System.err.println("response code " + conn.getResponseCode());
		}

		BufferedReader readBuffer = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "GBK"));

		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = readBuffer.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

}
