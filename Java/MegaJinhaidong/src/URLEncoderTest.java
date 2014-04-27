import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


public class URLEncoderTest {
	public static void main(String[] args) throws UnsupportedEncodingException {
		int buildingId = 65548264;
		String postData = "cached%5B0%5D%5Buser%5Fid%5D=1440289155&time%5Fpl=435273&auth%5Fkey=31364512e3345d11e407004af6e40ef5&user%5Fid=1440289155&room%5Fid=0&serv%5Fver=0&cached%5B0%5D%5Broll%5Fcounter%5D=3463&revision=20615f&rand=0%2E5701348534785211&avg%5Ffps=22&avg%5Fmem=548085760&version=19&cached%5B0%5D%5Bcommand%5D=clean&cached%5B0%5D%5Bitem%5Fid%5D=74047902&cached%5B0%5D%5Broom%5Fid%5D=0&cached%5B0%5D%5Bvsemogutor%5Fhash%5D=0&cached%5B0%5D%5Bago%5D=4&session%5Fkey=1397960731%2E8917983%3Anweb05%2D496&cached%5B0%5D%5Bexp%5D=10686228&client%5Fperformance%5Fstats=%7B%22daily%22%3A25%2E181818181818183%7D";
		String str = URLDecoder.decode(postData, "utf-8");
		System.out.println(str);
	}
}
