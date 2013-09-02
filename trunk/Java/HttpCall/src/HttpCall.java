import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

public class HttpCall {

	public static void main(String[] args) throws IOException,
			URISyntaxException {
		call("https://apps.facebook.com/fbml/ajax/dialog/apprequests");
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
				"i_id=%3Aasync_conf; c_user=100004979255608; csm=2; datr=wiyGUSxH_3wA--mMYFl7RN1l; lu=RgjLisbXV--qTn1CPzGaauVw; s=Aa75A4IXjCmhRrvO.BSJHA3; xs=61%3Aqqm3KFt0_PdDig%3A2%3A1378119735%3A17422; sub=536870912; p=78; fr=0RfVkae2QY8KOpMDu.AWUjE2QyjiXaBYKxrqoiM3pF6vs.BRhizL.X9.FIk.AWUEDBQ2; presence=EM378119745EuserFA21B04979255608A2EstateFDsb2F0Et2F_5b_5dElm2FnullEuct2F137811914B0EtrFnullEtwF2186511273EatF1378119741707G378119745370CEchFDp_5f1B04979255608F5CC; locale=ko_KR; wd=1474x836; act=1378119782744%2F0; _e_0KUs_1=%5B%220KUs%22%2C1378119782746%2C%22act%22%2C1378119782744%2C0%2C%22ok_clicked%22%2C%22click%22%2C%22click%22%2C%22-%22%2C%22r%22%2C%22%2Fplayarmada%2F%3Ffb_source%3Dbookmark_apps%26ref%3Dbookmarks%26count%3D0%26fb_bmpos%3D3_0%22%2C%7B%22ft%22%3A%7B%7D%2C%22gt%22%3A%7B%7D%7D%2C0%2C0%2C0%2C1447%2C16%5D");
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
		String formData = "_path=apprequests&app_id=127395434040269&redirect_uri=https%3A%2F%2Farmada.nwzcloud.com%2F&sdk=joey&display=async&message=TRNNNN%EB%8B%98%EC%9D%B4%20%EC%95%84%EB%A5%B4%EB%A7%88%EB%8B%A4%EC%97%90%EC%84%9C%20%EC%84%A0%EB%AC%BC%EC%9D%84%20%EB%B3%B4%EB%83%88%EC%8A%B5%EB%8B%88%EB%8B%A4.&to=1547439183&data=0_gift_"
				+ 855047
				+ "&e2e=%7B%7D&locale=en_US&frictionless=true&__d=1&__user=100004979255608&__a=1&__dyn=7n8ahyj34fxl2u5F97Keobo&__req=4&from_post=1&default_setting=true&ok_clicked=Send%20Request&fb_dtsg=AQCEENq4&ttstamp=265816769697811352";
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
