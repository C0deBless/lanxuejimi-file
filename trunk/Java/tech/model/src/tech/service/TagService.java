package tech.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tech.cache.Global;
import tech.model.Tag;
import tech.util.JsonHelper;

public class TagService {

	static Logger logger = LoggerFactory.getLogger(TagService.class);

	public void loadTag() {
		Global.TAGS.clear();
		try {
			String sql = "select * from tags";
			ResultSet rs = DataBaseService.getService().doSelectQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String value = rs.getString("value");
				int parent = rs.getInt("parent");
				Tag tag = new Tag(id, name, value, parent);
				Global.TAGS.add(tag);
			}

			if (Global.TAGS.isEmpty()) {
				loadTagsFromJson();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadTagsFromJson() {
		logger.info("cannot find tag data in database, load it from tags.json");
		URL uri = TagService.class.getResource("/../tags.json");
		try {
			File file = new File(uri.toURI());
			FileInputStream fis = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = reader.readLine()) != null) {
				buffer.append(str);
			}
			reader.close();
			String json = buffer.toString();
			List<Tag> tags = JsonHelper.deserialize(json,
					new TypeReference<List<Tag>>() {
					});
			Global.TAGS.addAll(tags);
			for (Tag tag : tags) {
				String query = "insert into tags(`id`,`name`,`value`,`parent`) values(%d,'%s','%s',%d)";
				query = String.format(query, tag.getId(), tag.getName(),
						tag.getValue(), tag.getParent());
				DataBaseService.getService().doExecuteInsertQuery(query);
			}

			// DataBaseService.getService().
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		List<Tag> list = new ArrayList<>();
		list.add(new Tag(1, ".NET", "net", 0));
		list.add(new Tag(2, "C#", "csharp", 1));
		list.add(new Tag(3, "Java", "java", 0));
		list.add(new Tag(4, "Java SE", "javase", 3));
		String json = JsonHelper.serialize(list);
		System.out.println(json);
	}
}
