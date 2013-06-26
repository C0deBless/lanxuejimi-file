package command;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

import main.BotContext;
import main.PrintStackTrace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import packet.Packet;

import common.tools.ClassParser;

public class CommandFactory implements Runnable {

	static Logger logger = LoggerFactory.getLogger("bot");

	LinkedBlockingDeque<Packet> packetQueue = new LinkedBlockingDeque<>();

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

	final BotContext context;

	public CommandFactory(BotContext context) {
		this.context = context;
		init();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Packet packet = this.packetQueue.take();
			} catch (InterruptedException e) {
				PrintStackTrace.print(logger, e);
			}
		}
	}
}
