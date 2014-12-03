import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileRenameMain {

	public static void main(String[] args) {
		File file = new File("E:\\迅雷下载\\妖精的尾巴");
		File[] subFiles = file.listFiles();
		Pattern pattern = Pattern.compile("\\d{2,}");
		for (File sub : subFiles) {
			String name = sub.getName();
			String ext = name.substring(name.lastIndexOf("."), name.length());
			System.out.println(ext);
			Matcher matcher = pattern.matcher(name);
			if (matcher.find()) {
				String str = matcher.group(0);
				System.out.println("pattern match:" + str);
				sub.renameTo(new File("E:\\迅雷下载\\妖精的尾巴\\妖精的尾巴-" + str + "."
						+ ext));
			}
			System.out.println(name);
		}
	}

}
