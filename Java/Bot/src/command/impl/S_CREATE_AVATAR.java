package command.impl;

import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import packet.Packet;
import session.AioSession;
import command.CommandBinder;
import common.tools.JsonHelper;

public class S_CREATE_AVATAR implements CommandBinder {

	static Logger logger = LoggerFactory.getLogger(S_CREATE_AVATAR.class);

	@Override
	public void execute(Packet packet) throws Exception {
		AioSession session = packet.getSession();
		long userId = session.getContext().getUserId();

		String json = new String(packet.getByte());
		Map<String, Object> values = JsonHelper.deserialize(json,
				new TypeReference<Map<String, Object>>() {
				});
		int res = (int) values.get("res");
		if (res == 1) {
			logger.info("account created, userId:{}", userId);
			session.getContext().requestUserData();
		} else {
			String code = values.get("code").toString();
			logger.error("server response error:{}", code);
		}
	}
}
