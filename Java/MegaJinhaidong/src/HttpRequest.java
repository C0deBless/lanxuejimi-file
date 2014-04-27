import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpRequest {
	private static URL url;
	private static HttpURLConnection httpURLConn;

	private static String getPostData() throws IOException {
		int roomId = 2;

		StringBuilder sb = new StringBuilder();
		sb.append("&time_pl=435273&auth_key=31364512e3345d11e407004af6e40ef5&user_id=1440289155&serv_ver=0&revision=20615f&rand=0.5701348534785211&avg_fps=22&avg_mem=548085760&version=19&session_key=1397960731.8917983:nweb05-496&cached[0][exp]=10686228&client_performance_stats={\"daily\":25.181818181818183}");

		sb.append("&room_id=" + roomId);

		int count = 0;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				HttpRequest.class.getResourceAsStream(roomId + ".txt")))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				int buildingId = Integer.parseInt(line);
				sb.append("&cached[" + count + "][ago]=4");
				sb.append("&cached[" + count + "][room_id]=" + roomId);
				sb.append("&cached[" + count + "][command]=clean");
				sb.append("&cached[" + count + "][item_id]=" + buildingId);// 65548264
				sb.append("&cached[" + count + "][exp]=10114490");
				sb.append("&cached[" + count + "][vsemogutor_hash]=0");
				sb.append("&cached[" + count + "][user_id]=1440289155");
				sb.append("&cached[" + count + "][roll_counter]=3463");
				count++;
			}

		}
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {

		// int buildingId = 65548264;
		// String postData =
		// "cached%5B0%5D%5Bago%5D=1&auth%5Fkey=2b8935a2a5af824c4019f11b14b412eb&cached%5B0%5D%5Broom%5Fid%5D=0&cached%5B0%5D%5Bcommand%5D=clean&user%5Fid=100003075441353&room%5Fid=0&serv%5Fver=0&avg%5Fmem=614162432&session%5Fkey=1397565641%2E8855171%3Anweb06%2D19040&cached%5B0%5D%5Bitem%5Fid%5D="
		// + buildingId
		// +
		// "&time%5Fpl=1562037&version=136&rand=0%2E12341511296108365&avg%5Ffps=15&client%5Fperformance%5Fstats=%7B%22daily%22%3A28%2E839285714285715%7D&cached%5B0%5D%5Bexp%5D=10114490&cached%5B0%5D%5Bvsemogutor%5Fhash%5D=0&revision=4635b8&cached%5B0%5D%5Buser%5Fid%5D=100003075441353&cached%5B0%5D%5Broll%5Fcounter%5D=1429";

		String postData = getPostData();
		postData = URLEncoder.encode(postData, "utf-8");
		postData = postData.replace("_", "%5F");
		postData = postData.replace("%3D", "=");
		postData = postData.replace("%26", "&");

		System.out.println(postData);
		String temp = new String();
		url = new URL(
				"https://nweb05.socialquantum.com/check_and_perform?uid=1440289155&crev=20615f&fp=WIN%2013,0,0,182&rn=7");
		httpURLConn = (HttpURLConnection) url.openConnection();
		httpURLConn.setDoOutput(true);
		httpURLConn.setRequestMethod("POST");
		httpURLConn
				.setRequestProperty("Referer",
						"https://city-fb.socialquantum.com/assets/loader11.swf?r=1397840364");
		httpURLConn.setRequestProperty("User-Agent", "test");
		httpURLConn
				.setRequestProperty("Content-Length", "" + postData.length());
		httpURLConn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		httpURLConn
				.setRequestProperty(
						"Cookie",
						"fbm_150241438424387=base_domain=.socialquantum.com; fbsr_150241438424387=A808TZ6j26DGYMeuWxd1oRsnVblgQFSqIS5s4f4nO6w.eyJhbGdvcml0aG0iOiJITUFDLVNIQTI1NiIsImNvZGUiOiJBUUJTdGNyc1hKVnRYakd3aktvTTFMQmZXQm1XSFEtM2ZpaXBOZkgwT3J0M0pmNHd1dy1udG1XZnE1eUhqRVJCQXZjLVJ0bUh2a0ZPX25fLWF3bnB3blMybVhUUW1QUHBzcVQ4MFRxYXZfdXI5bGdQbjJxSGg4bjlqekpUQ25jUE9rVGdkd0w5Mkc0b0ljWi00NmROQ2F4RDduMUdTTnBwMDlNZVRVMWktV0RmYXQ0V3RMWXVSU09BM3U4U3Z0Sk1IZHVzLVpGblFvVnIyUE01WVYteUljZHZFWnJZeHowMUV2SENFMTNnT2Jyem9VQmVMNlU2aUlFejF1WWU2dkhiT2hjb01yTjFKU09LTXd5NlNPUjdZRlluaEFmeE1yU1FyV1R4TFJiN0JYQ3JFeVB6WlJ2ZjAtZ3BaVmNxWHNybGFiYyIsImlzc3VlZF9hdCI6MTM5Nzk2MDMwOSwidXNlcl9pZCI6IjE0NDAyODkxNTUifQ");
		httpURLConn.setRequestProperty("Host", "nweb05.socialquantum.com");
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
		InputStream in = null;
		try {
			in = httpURLConn.getInputStream();
		} catch (Exception e) {
			in = httpURLConn.getErrorStream();
		}
		BufferedReader bd = new BufferedReader(new InputStreamReader(in));
		while ((temp = bd.readLine()) != null) {
			System.out.println(temp);
		}
	}
}
