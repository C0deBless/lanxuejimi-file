package command.impl;

import java.util.Map;

import main.BotMain;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import packet.Packet;

import command.CommandBinder;
import common.tools.JsonHelper;

public class S_LOGIN implements CommandBinder {

	static Logger logger = LoggerFactory.getLogger("bot");

	@Override
	public void execute(Packet packet) throws Exception {
		String json = new String(packet.getByte());
		Map<String, Object> values = JsonHelper.deserialize(json,
				new TypeReference<Map<String, Object>>() {
				});
		int res = (int) values.get("res");
		if (res == 1)
			BotMain.context.requestUserData();
		else {
			String code = values.get("code").toString();
			logger.error("server response error:{}", code);
		}
	}
}
