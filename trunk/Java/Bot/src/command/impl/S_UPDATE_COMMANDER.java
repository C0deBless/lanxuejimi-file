package command.impl;

import java.util.Map;

import main.BotMain;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import packet.Packet;

import command.CommandBinder;
import common.tools.JsonHelper;

public class S_UPDATE_COMMANDER implements CommandBinder {

	static Logger logger = LoggerFactory.getLogger("bot");

	@Override
	public void execute(Packet packet) throws Exception {
		String json = new String(packet.getByte());
		logger.info("update energy, data:{}", json);
		Map<String, Object> values = JsonHelper.deserialize(json,
				new TypeReference<Map<String, Object>>() {
				});
		int energy = (int) values.get("energy");
		BotMain.context.getUser().getCommander().setEnergy(energy);
	}

}
