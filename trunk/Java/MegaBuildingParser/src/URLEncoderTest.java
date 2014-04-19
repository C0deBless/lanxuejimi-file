import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


public class URLEncoderTest {
	public static void main(String[] args) throws UnsupportedEncodingException {
		int buildingId = 65548264;
		String postData = "cached%5B0%5D%5Bago%5D=1&auth%5Fkey=2b8935a2a5af824c4019f11b14b412eb&cached%5B0%5D%5Broom%5Fid%5D=0&cached%5B0%5D%5Bcommand%5D=clean&user%5Fid=100003075441353&room%5Fid=0&serv%5Fver=0&avg%5Fmem=614162432&session%5Fkey=1397565641%2E8855171%3Anweb06%2D19040&cached%5B0%5D%5Bitem%5Fid%5D="
				+ buildingId
				+ "&time%5Fpl=1562037&version=136&rand=0%2E12341511296108365&avg%5Ffps=15&client%5Fperformance%5Fstats=%7B%22daily%22%3A28%2E839285714285715%7D&cached%5B0%5D%5Bexp%5D=10114490&cached%5B0%5D%5Bvsemogutor%5Fhash%5D=0&revision=4635b8&cached%5B0%5D%5Buser%5Fid%5D=100003075441353&cached%5B0%5D%5Broll%5Fcounter%5D=1429";
		String str = URLDecoder.decode(postData, "utf-8");
		System.out.println(str);
		
		System.out.println(URLEncoder.encode(str));
	}
}
