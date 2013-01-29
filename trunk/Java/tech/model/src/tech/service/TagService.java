package tech.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import tech.cache.Global;
import tech.model.Tag;

public class TagService {

	public void loadTag() {
		Global.TAGS.clear();

		try {
			String sql = "select * from tag_root";
			ResultSet rs = DataBaseService.getService().doSelectQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String value = rs.getString("value");
				Tag tag = new Tag();
				tag.setId(id);
				tag.setName(name);
				tag.setValue(value);
				Global.TAGS.put(id, tag);
			}
			sql = "select * from tag_sub";
			rs = DataBaseService.getService().doSelectQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int parent = rs.getInt("parent");
				String name = rs.getString("name");
				String value = rs.getString("value");
				Tag ptag = Global.TAGS.get(parent);
				Tag tag = new Tag();
				tag.setId(id);
				tag.setName(name);
				tag.setValue(value);
				ptag.addSub(tag);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
