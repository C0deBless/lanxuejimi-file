import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

public class HttpCall {

	public static void main(String[] args) throws IOException,
			URISyntaxException, InterruptedException {
		for (int i = 1; i <= 300; i++) {
			System.out.println("send request:" + i);
			Thread.sleep(50);
			call("https://apps.facebook.com/fbml/ajax/dialog/apprequests");
		}

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
		conn.setRequestProperty(
				"Cookie",
				"c_user=100003075441353; datr=wiyGUSxH_3wA--mMYFl7RN1l; lu=ggwc76Rh-swBHzRfXSW4MUBg; s=Aa4FplgyTcCIqEUY.BSYlg5; xs=88%3AcXheSxAygH6wBQ%3A0%3A1382176825%3A10682; fr=0RfVkae2QY8KOpMDu.AWX0C6pr3xbDyBiK4J3OnPQGaMo.BRhizL.X9.FJi.AWWs5rdw; presence=EM382176822EuserFA21B03075441353A2EstateFDutF0Et2F_5b_5dEuct2F1382176225BElm2FnullEtrFnullEtwF2358019991EatF1382176822173Esb2F0CEchFDp_5f1B03075441353F1051CC; sub=72; p=192; locale=en_US; wd=1460x870; act=1382176849719%2F1; _e_0Mj8_1=%5B%220Mj8%22%2C1382176849722%2C%22act%22%2C1382176849719%2C1%2C%22ok_clicked%22%2C%22click%22%2C%22click%22%2C%22-%22%2C%22r%22%2C%22%2Fplayarmada%2F%3Ffb_source%3Dbookmark_apps%26ref%3Dbookmarks%26count%3D0%26fb_bmpos%3D2_0%22%2C%7B%22ft%22%3A%7B%7D%2C%22gt%22%3A%7B%7D%7D%2C865%2C344%2C0%2C1433%2C%22kgv13q%22%2C17%5D");
		conn.setRequestProperty("Origin", "https://apps.facebook.com");
		conn.setRequestProperty(
				"Referer",
				"https://apps.facebook.com/playarmada/?fb_source=bookmark_apps&ref=bookmarks&count=0&fb_bmpos=3_0");
		conn.setRequestProperty(
				"user-agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.62 Safari/537.36");
		conn.setRequestProperty("x-svn-rev", "924754");
		conn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(
				conn.getOutputStream());
		
		//_path=apprequests&app_id=127395434040269&redirect_uri=https%3A%2F%2Farmada.nwzcloud.com%2F&sdk=joey&display=async&message=trnnn%EB%8B%98%EC%9D%B4%20%EC%95%84%EB%A5%B4%EB%A7%88%EB%8B%A4%EC%97%90%EC%84%9C%20%EC%84%A0%EB%AC%BC%EC%9D%84%20%EB%B3%B4%EB%83%88%EC%8A%B5%EB%8B%88%EB%8B%A4.&to=1260392565&data=0_gift_809701&e2e=%7B%7D&locale=en_US&frictionless=true&__d=1&__user=100003075441353&__a=1&__dyn=7n8ahyj34fzpQ9UmWWuUGy1m9Ay8&__req=g&from_post=1&default_setting=true&ok_clicked=Send%20Request&fb_dtsg=AQASPvn0&ttstamp=2658165838011811048
		String formData = "_path=apprequests&app_id=127395434040269&redirect_uri=https%3A%2F%2Farmada.nwzcloud.com%2F&sdk=joey&display=async&message=trnnn%EB%8B%98%EC%9D%B4%20%EC%95%84%EB%A5%B4%EB%A7%88%EB%8B%A4%EC%97%90%EC%84%9C%20%EC%84%A0%EB%AC%BC%EC%9D%84%20%EB%B3%B4%EB%83%88%EC%8A%B5%EB%8B%88%EB%8B%A4.&to=100004979255608&data=0_support_809914&e2e=%7B%7D&locale=en_US&frictionless=true&__d=1&__user=100003075441353&__a=1&__dyn=7n8ahyj34fzpQ9UmWWiKaEwlyp8y&__req=a&from_post=1&default_setting=true&ok_clicked=Send%20Request&fb_dtsg=AQAFrxnE&ttstamp=26581657011412011069";
		writer.write(formData);
		writer.flush();
		writer.close();
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
