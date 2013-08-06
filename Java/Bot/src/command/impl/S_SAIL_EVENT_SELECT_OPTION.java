package command.impl;

import java.util.Map;

import main.BotContext;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import packet.Packet;
import command.CommandBinder;
import common.tools.JsonHelper;

public class S_SAIL_EVENT_SELECT_OPTION implements CommandBinder {

	static Logger logger = LoggerFactory
			.getLogger(S_SAIL_EVENT_SELECT_OPTION.class);

	@Override
	public void execute(Packet packet) throws Exception {
		String json = new String(packet.getByte());
		Map<String, Object> values = JsonHelper.deserialize(json,
				new TypeReference<Map<String, Object>>() {
				});
		int res = (int) values.get("res");
		if (res == 1) {
			@SuppressWarnings("unchecked")
			Map<String, Object> pattern = (Map<String, Object>) values
					.get("pattern");
			int id = (int) pattern.get("id");
			BotContext context = packet.getSession().getContext();
			context.handleEventPattern(id);
		} else {
			logger.error("service error:{}", json);
			System.exit(1);
		}
	}

}
