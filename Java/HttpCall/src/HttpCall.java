import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class HttpCall {

	public static void main(String[] args) {

	}

	private static String call(String url) throws IOException,
			URISyntaxException {
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept-Charset", "UTF-8");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("content-type",
				"application/x-www-form-urlencoded");
		HttpCookie cookie1 = new HttpCookie("xs",
				"2%3AYAKK9gNJNl-NxA%3A2%3A1378108285%3A17422");
		HttpCookie cookie2 = new HttpCookie("s", "Aa5Xh5Qimecq5aW0.BSJEN9");
		HttpCookie cookie3 = new HttpCookie("lu", "ggNjy-WBcwZL8xPjLBRYKskw");
		HttpCookie cookie4 = new HttpCookie("datr", "k_fPUNidc-XE7pl9bh5EjXHe");
		HttpCookie cookie5 = new HttpCookie("csm", "2");
		HttpCookie cookie6 = new HttpCookie("c_user", "100004979255608");
		HttpCookie cookie7 = new HttpCookie("fr",
				"08y4moPYqbJQc0uEK.AWVoylbbKKzmJDHLN6hGmSWWMtU.BQTUQZ.6T.FIk.AWV2oWeQ");
		HttpCookie cookie8 = new HttpCookie("sub", "266240");
		HttpCookie cookie9 = new HttpCookie("p", "78");
		HttpCookie cookie10 = new HttpCookie(
				"presence",
				"EM378108537EuserFA21B04979255608A2EstateFDsb2F0Et2F_5b_5dElm2FnullEuct2F1378107689BEtrFnullEtwF2800235194EatF1378108485152G378108537789CEchFDp_5f1B04979255608F4CC; locale=ko_KR; wd=1439x840; act=1378108562097%2F7; _e_0arK_6=%5B%220arK%22%2C1378108562099%2C%22act%22%2C1378108562097%2C7%2C%22ok_clicked%22%2C%22click%22%2C%22click%22%2C%22-%22%2C%22r%22%2C%22%2Fplayarmada%2F%3Ffb_source%3Dbookmark_apps%26ref%3Dbookmarks%26count%3D0%26fb_bmpos%3D3_0%22%2C%7B%22ft%22%3A%7B%7D%2C%22gt%22%3A%7B%7D%7D%2C0%2C0%2C0%2C1412%2C16%5D");
		// CookieManager manager = new CookieManager();
		// manager.getCookieStore().add(new URI(url), cookie1);
		// manager.getCookieStore().add(new URI(url), cookie2);
		// manager.getCookieStore().add(new URI(url), cookie3);
		// manager.getCookieStore().add(new URI(url), cookie4);
		// manager.getCookieStore().add(new URI(url), cookie5);
		// manager.getCookieStore().add(new URI(url), cookie6);
		// manager.getCookieStore().add(new URI(url), cookie7);
		// manager.getCookieStore().add(new URI(url), cookie8);
		// manager.getCookieStore().add(new URI(url), cookie9);
		// manager.getCookieStore().add(new URI(url), cookie10);

		conn.setDoOutput(false);
		// OutputStreamWriter writer = new OutputStreamWriter(
		// conn.getOutputStream());
		// writer.flush();
		// writer.close();
		int code = conn.getResponseCode();
		String msg = conn.getResponseMessage();
		if (code != 200) {
			System.out.println("error code:" + code);
			return msg;
		}

		BufferedReader readBuffer = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));

		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = readBuffer.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

}
