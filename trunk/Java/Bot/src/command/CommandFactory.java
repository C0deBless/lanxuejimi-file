package command;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.PrintStackTrace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import packet.Packet;

import common.tools.ClassParser;

public class CommandFactory {

	static Logger logger = LoggerFactory.getLogger("bot");

	final Map<Integer, CommandBinder> binders = new HashMap<>();
	final String commandPackage = "command.impl";

	private void init() {
		try {

			Set<Class<?>> classes = ClassParser.getClasses(commandPackage);

			Command command = new Command();
			Field[] fields = Command.class.getDeclaredFields();
			for (Field field : fields) {
				String name = field.getName();
				int value = field.getInt(command);

				for (Class<?> cls : classes) {
					if (cls.getName().endsWith(name)) {
						logger.trace("init command executor:{}", name);
						binders.put(value, (CommandBinder) cls.newInstance());
					}
				}
			}

		} catch (Exception e) {
			PrintStackTrace.print(logger, e);
		}
	}

	public CommandFactory() {
		init();
	}

	public void pushPacket(List<Packet> packets) {
		for (Packet packet : packets) {
			CommandBinder binder = this.binders.get(packet.command());
			if (binder != null) {
				try {
					binder.execute(packet);
				} catch (Exception e) {
					PrintStackTrace.print(logger, e);
				}
			} else {
				// logger.error("cannot find commander binder, cmd:{}",
				// packet.command());
			}
		}
	}

}
