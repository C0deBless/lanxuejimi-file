package command.impl;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import main.BotContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import packet.Packet;
import command.CommandBinder;
import common.serialize.SerializationHelper;
import common.struct.Condition;
import common.struct.Info;
import common.struct.MapObjectData;
import common.tools.BooleanUtil;
import common.tools.StringUtil;

public class S_SAIL_MOVE implements CommandBinder {

	static Logger logger = LoggerFactory.getLogger(S_SAIL_MOVE.class);

	@Override
	public void execute(Packet packet) throws Exception {
		ByteBuffer buffer = packet.getByteBuffer();
		int res = buffer.getInt();
		if (res != 1) {
			logger.error("move map failed");
			System.exit(1);
			return;
		}
		boolean fail = BooleanUtil.getBoolean(buffer);
		int energy = buffer.getInt();
		String ns = StringUtil.getString(buffer);
		String ew = StringUtil.getString(buffer);
		String mapId = ns + "_" + ew;
		String pos = StringUtil.getString(buffer);

		int size = buffer.getInt();
		List<MapObjectData> objects = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			MapObjectData obj = new MapObjectData(mapId);
			Condition condition = new Condition();
			Info info = new Info();
			obj.setInfo(info);
			obj.setCondition(condition);
			info.setId(buffer.getInt());
			info.setArt(buffer.getInt());
			info.setType(buffer.getInt());
			info.setEvent(buffer.getInt());
			obj.getCondition().setMission(buffer.getInt());
			info.setEmblem(buffer.getInt());
			info.setFleet_id((long) buffer.getDouble());
			info.setName(StringUtil.getString(buffer));
			info.setObject_id(buffer.getInt());
			// info.setObject_id(info.getId());
			info.setInvisible(buffer.getInt());
			info.setPower(buffer.getInt());
			buffer.getInt();// rate
			SerializationHelper.deserializeIntegerList(buffer);// itemBufSkills
			buffer.getInt();// time cool
			buffer.getInt();// time invisible
			buffer.getInt();// object effect
			buffer.getInt();// object effect
			buffer.getInt();// object effect

			buffer.getInt();// pattern
			buffer.getInt();// pattern
			buffer.getInt();// pattern
			StringUtil.getString(buffer);// pattern
			buffer.getInt();// action
			buffer.getInt();// action
			buffer.getInt();// action
			buffer.getInt();// action
			buffer.getInt();// action
			buffer.getInt();// action

			buffer.getInt();// portrait_id
			buffer.getInt();// enchant_level
			objects.add(obj);
		}

		BotContext context = packet.getSession().getContext();
		context.updateMapObjects(mapId, objects, true);
	}
}
