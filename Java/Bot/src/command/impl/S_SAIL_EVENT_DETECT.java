package command.impl;

import java.util.List;
import java.util.Map;

import main.BotContext;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import packet.Packet;
import command.CommandBinder;
import common.tools.JsonHelper;

public class S_SAIL_EVENT_DETECT implements CommandBinder {

	static Logger logger = LoggerFactory.getLogger(S_SAIL_EVENT_DETECT.class);

	@Override
	public void execute(Packet packet) throws Exception {
		String json = new String(packet.getByte());
		Map<String, Object> values = JsonHelper.deserialize(json,
				new TypeReference<Map<String, Object>>() {
				});
		int res = (int) values.get("res");
		if (res == 1) {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) values
					.get("opts");
			for (Map<String, Object> map : list) {
				int id = (int) map.get("id");
//				int display = (int) map.get("display");
				@SuppressWarnings("unchecked")
				Map<String, Object> cost = (Map<String, Object>) map
						.get("cost");
				int costId = (int) cost.get("id");
				int costCount = (int) cost.get("count");

				if (id == 1) {
					BotContext context = packet.getSession().getContext();
					context.selectOption(id, costId, costCount);
					break;
				}
			}
		} else {
			logger.error("service error, data:{}", json);
			System.exit(1);
		}
	}
}
