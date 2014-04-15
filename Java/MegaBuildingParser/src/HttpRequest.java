import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
	private static URL url;
	private static HttpURLConnection httpURLConn;

	public static void main(String[] args) throws IOException {

		int buildingId = 65548264;
		String postData = "cached%5B0%5D%5Bago%5D=1&auth%5Fkey=2b8935a2a5af824c4019f11b14b412eb&cached%5B0%5D%5Broom%5Fid%5D=0&cached%5B0%5D%5Bcommand%5D=clean&user%5Fid=100003075441353&room%5Fid=0&serv%5Fver=0&avg%5Fmem=614162432&session%5Fkey=1397565641%2E8855171%3Anweb06%2D19040&cached%5B0%5D%5Bitem%5Fid%5D="
				+ buildingId
				+ "&time%5Fpl=1562037&version=136&rand=0%2E12341511296108365&avg%5Ffps=15&client%5Fperformance%5Fstats=%7B%22daily%22%3A28%2E839285714285715%7D&cached%5B0%5D%5Bexp%5D=10114490&cached%5B0%5D%5Bvsemogutor%5Fhash%5D=0&revision=4635b8&cached%5B0%5D%5Buser%5Fid%5D=100003075441353&cached%5B0%5D%5Broll%5Fcounter%5D=1429";

		String temp = new String();
		url = new URL(
				"https://nweb06.socialquantum.com/check_and_perform?uid=100003075441353&crev=4635b8&fp=WIN%2013,0,0,182&rn=23");
		httpURLConn = (HttpURLConnection) url.openConnection();
		httpURLConn.setDoOutput(true);
		httpURLConn.setRequestMethod("POST");
		httpURLConn
				.setRequestProperty("Referer",
						"https://city-fb.socialquantum.com/assets/loader11.swf?r=1397551639");
		httpURLConn.setRequestProperty("User-Agent", "test");
		httpURLConn
				.setRequestProperty("Content-Length", "" + postData.length());
		httpURLConn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		httpURLConn
				.setRequestProperty(
						"Cookie",
						"fbm_150241438424387=base_domain=.socialquantum.com; fbsr_150241438424387=7BaSNZ4fD4EVTfg0KeA7NuD6UYbBExWXr9x5LfO4U5k.eyJhbGdvcml0aG0iOiJITUFDLVNIQTI1NiIsImNvZGUiOiJBUUNvdERIREJ6LXh5ZzdVODdIT181TjUxUEQ5VVdSc3hadzRLcXFORFhkaTNGODRSVDg5Z25KczNNbEZ2UTBZZkZfRzREUkszNmM4WjBIMTNIY3NOMFE4ZXJvUE90b0pPTDdxd2pQMVBsN2VqM1V0N2F1Q1kzcW5qNU83a2lTRDBHbGdpbHRzX1V1WlUzOUxRRVEwUlhDeldqa29DV21KbGJHX0wtcnlsaW9XbHA0SmNVd2pwTnhaMDJibzVsWjltSEFwbWhXMHhqUVQ5RGpydHFnTEJmZEhLbXk4cFBMamJQSlBGRDllMDlneDZlMkdSQzdmQm9LWHlIQmxSQm1SemRrM01aYWtHb1l2TFNrYnB5YmJBSmxXd3Fmdlpad3lRRzRrM3J3Z19EbGJQZlU1aXhlOVRoM09xVmtkTjBmNkpnMmZfM3JUUGRCXy1NejJaTk43UjJ0ayIsImlzc3VlZF9hdCI6MTM5NzU2NTkyMSwidXNlcl9pZCI6IjEwMDAwMzA3NTQ0MTM1MyJ9");
		httpURLConn.setRequestProperty("Host", "nweb06.socialquantum.com");
		httpURLConn.setRequestProperty("Origin",
				"https://city-fb.socialquantum.com");
		httpURLConn
				.setRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.116 Safari/537.36");

		httpURLConn.connect();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				httpURLConn.getOutputStream()));
		writer.write(postData);
		writer.flush();
		writer.close();
		InputStream in = httpURLConn.getInputStream();
		BufferedReader bd = new BufferedReader(new InputStreamReader(in));
		while ((temp = bd.readLine()) != null) {
			System.out.println(temp);
		}
	}
}
