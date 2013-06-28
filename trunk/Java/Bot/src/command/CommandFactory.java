package command;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
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

			Thread thread = new Thread(this, "CommandFactory");
			thread.start();
		} catch (Exception e) {
			PrintStackTrace.print(logger, e);
		}
	}

	final BotContext context;

	public CommandFactory(BotContext context) {
		this.context = context;
		init();
	}

	public void pushPacket(List<Packet> packets) {
		this.packetQueue.addAll(packets);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Packet packet = this.packetQueue.take();
				CommandBinder binder = this.binders.get(packet.command());
				if (binder != null) {
					try {
						binder.execute(packet);
					} catch (Exception e) {
						PrintStackTrace.print(logger, e);
					}
				} else {
					logger.error("cannot find commander binder, cmd:{}",
							packet.command());
				}
			} catch (InterruptedException e) {
				PrintStackTrace.print(logger, e);
			}
		}
	}
}
