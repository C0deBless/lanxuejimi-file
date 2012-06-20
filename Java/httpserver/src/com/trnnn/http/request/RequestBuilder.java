package com.trnnn.http.request;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.trnnn.http.console.Console;
import com.trnnn.http.session.ClientSession;

public class RequestBuilder {

	private ByteBuffer readBuffer;

	// private ClientSession session;

	public RequestBuilder(ByteBuffer readBuffer, ClientSession session) {
		this.readBuffer = readBuffer;
		// this.session = session;
		this.builder = new Builder(session);
		;
	}

	private Builder builder;

	public void queue() {
		// System.out.println(this.readBuffer.toString());
		// if (readBuffer.hasRemaining()) {
		readBuffer.flip();
		byte[] dst = new byte[readBuffer.remaining()];
		readBuffer.get(dst);
		readBuffer.clear();
		handleAppend(dst);
		// }
	}

	private void handleAppend(byte[] data) {
		builder.append(data);
	}

	class Builder {
		private boolean isHeaderRead = false;
		private boolean isContentRead = false;
		private StringBuffer headerBuffer = new StringBuffer();
		private StringBuffer contentBuffer = new StringBuffer();
		// private int contentSize = 0;
		// private int contentReadCursor = 0;
		// private Charset readCharset;
		// private Charset writeCharset;
		private ClientSession session;
		private String requestUrl;

		private String verb = null;

		public Builder(ClientSession session) {
			this.session = session;
		}

		public void append(byte[] data) {
			try {
				System.out.print(new String(data, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (!isHeaderRead) {
				readHeader(data);
			} else if (!isContentRead) {
				if (this.verb == null) {
					Console.output("Builder-append", "verb is null");
				} else if (this.verb.equalsIgnoreCase("post")) {
					readContent(data);
				}
			} else {

			}
		}

		public void resetBuilder() {
			this.requestUrl = null;
			isHeaderRead = false;
			isContentRead = false;
			headerBuffer = new StringBuffer();
			contentBuffer = new StringBuffer();
		}

		public void sendResponse() {
			if (this.requestUrl.equals("/favicon.ico")) {
				File file = new File("D:\\test.ico");
				try {
					FileInputStream input = new FileInputStream(file);
					int size = input.available();
					byte[] data = new byte[size];
					input.read(data);
					StringBuffer sb = new StringBuffer();
					sb.append("HTTP/1.1 200 OK\r\n");
					sb.append("Server: Microsoft-IIS/4.0\r\n");
					sb.append("Date: Mon, 3 Jan 2005 13:13:33 GMT\r\n");
					sb.append("Content-Type: image/x-icon\r\n");
					sb.append("Last-Modified: Mon, 11 Jan 2004 13:23:42 GMT\r\n");
					sb.append("Content-Length: " + size);
					sb.append("\r\n\r\n");
					sb.append(new String(data));
					sb.append("\r\n\r\n");
					Console.output("Builder-append", "write data to client");
					session.pendingWrite(sb.toString().getBytes());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				// StringBuffer content = new StringBuffer();
				// content.append("<html>");
				// content.append("<head>");
				// content.append("<title>解读HTTP包示例</title></head><body>");
				// content.append("<form action='/fileupload' enctype='multipart/form-data' method='post'><input type='file' name='file1'/><input type='file' name='file2'/><input type='submit'/></form>");
				// content.append("</body>");
				// content.append("</html>");
				StringBuffer content = new StringBuffer();
				content.append("<html>");
				content.append("<head>");
				content.append("<title>解读HTTP包示例</title></head><body>");
				content.append("<form action='/fileupload' method='post'><input type='text' name='name'/><input type='submit'/></form>");
				content.append("</body>");
				content.append("</html>");
				StringBuffer sb = new StringBuffer();
				sb.append("HTTP/1.1 200 OK\r\n");
				sb.append("Server: Microsoft-IIS/4.0\r\n");
				sb.append("Date: Mon, 3 Jan 2005 13:13:33 GMT\r\n");
				sb.append("Content-Type: text/html\r\n");
				sb.append("Last-Modified: Mon, 11 Jan 2004 13:23:42 GMT\r\n");
				sb.append("Content-Length: " + content.length());
				sb.append("\r\n\r\n");
				sb.append(content);
				sb.append("\r\n\r\n");
				Console.output("Builder-append", "write data to client");
				session.pendingWrite(sb.toString().getBytes());

			}
			this.resetBuilder();
		}

		public void readHeader(byte[] data) {
			String str = new String(data);
			// System.out.println(str);
			headerBuffer.append(str);
			if (verb == null) {
				int i = str.indexOf("/");
				if (i >= 0) {
					verb = str.substring(0, i);
					if (this.verb.equalsIgnoreCase("get")) {
						this.isContentRead = true;
					}
				}
			}

			String tmp = headerBuffer.toString();
			int k = tmp.indexOf("HTTP");
			if (this.requestUrl == null && k >= 0) {
				this.requestUrl = tmp.substring(str.indexOf("/"), k).trim();
				Console.output("Builder-readReader", "requestUrl ->> "
						+ requestUrl);
			}

			int j = tmp.indexOf("\r\n\r\n");
			if (j >= 0) {
				this.contentBuffer.append(tmp.substring(j));
				this.headerBuffer = new StringBuffer();
				this.headerBuffer.append(tmp.substring(0, j));
				this.isHeaderRead = true;
				Console.output("Builder-readHeader",
						"read header complete. verb=" + this.verb);

				parseHeaderData();
			}
		}

		private void parseHeaderData() {
			handleRequest();
		}

		private void handleRequest() {

		}

		private void readContent(byte[] data) {
			Console.output("Builder-readContent", "read content");
		}
	}

	public static void main(String[] args) {
		long i = Long.parseLong("0ffffff", 16);
		System.out.println(i);
	}
}
