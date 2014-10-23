package text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class TextMain {
	public static void main(String[] args) throws IOException {

		boolean foundMine = false;
		boolean foundSperate = false;

		FileInputStream inputStream = new FileInputStream("D:\\MainScreenScene.unity");
		FileOutputStream outputStream = new FileOutputStream("D:\\rec.unity",
				false);
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				outputStream))) {
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(inputStream))) {
				String line = null;
				while ((line = reader.readLine()) != null) {

					if (line.equals("<<<<<<< .mine")) {
						foundMine = true;
					} else if (line.equals("=======")) {
						foundSperate = true;
					} else if (line.startsWith(">>>>>>>")) {
						foundSperate = false;
						foundMine = false;
					} else {
						if (foundMine) {
							if (!foundSperate) {
								writer.append(line);
								writer.newLine();
							} else {
								continue;
							}
						} else {
							writer.append(line);
							writer.newLine();
						}
					}
				}
			}
		}

	}
}
