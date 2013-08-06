package main;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import session.AioSession;
import command.Command;
import command.CommandFactory;
import common.struct.MapObjectData;
import common.struct.UserBase;
import common.tools.StringUtil;

public class BotContext {

	static Logger logger = LoggerFactory.getLogger("bot");
	private final AioSession session;

	public static final CommandFactory commandFactory = new CommandFactory();
	private final long userId;
	private UserBase user;
	private String currentMap = "";
	private List<MapObjectData> objects = new ArrayList<>();

	final String leftTopNS = "N62";
	final String leftTopEW = "W023";
	final String rightTopNS = "N62";
	final String rightTopEW = "W019";
	final String leftBottomNS = "N46";
	final String leftBottomEW = "W023";
	final String rightBottomNS = "N46";
	final String rightBottomEW = "W019";

	public int[] getNextMapId() {
		String[] strs = currentMap.split("_");

		String ns = strs[0];
		int nsn = Integer.valueOf(ns.substring(1));
		String ew = strs[1];
		int ewn = Integer.valueOf(ew.substring(1));
		if (ewn == 19) {
			if (nsn == 46) {
				return new int[] { 46, 23 };
			} else {
				return new int[] { nsn - 4, 19 };
			}
		} else {
			if (nsn == 62) {
				return new int[] { 62, 19 };
			} else {
				return new int[] { nsn + 4, 23 };
			}
		}
	}

	private List<Integer> objectIdList = Arrays.asList(532020, 532007, 330030);
	private boolean isDoingBattle = false;

	public BotContext(AioSession session, long userId) {
		this.session = session;
		this.userId = userId;
	}

	public AioSession getSession() {
		return this.session;
	}

	public void login(long userId, String sig) {
		logger.info("do login, userId:{}, sig:{}", userId, sig);
		String json = String.format("{\"user_id\":%d,\"sig\":\"%s\"}", userId,
				sig);
		session.responseJson(Command.C_LOGIN, json);
	}

	public void updateMapObjects(String mapId, List<MapObjectData> objects,
			boolean clear) {
		this.currentMap = mapId;
		this.objects.clear();
		this.objects.addAll(objects);
		logger.info("update map objects, mapId:{}, size:{}", mapId,
				objects.size());
	}

	public CommandFactory getCommandFactory() {
		return commandFactory;
	}

	public void requestUserData() {
		session.responseJson(Command.C_UDATAREQ, "");
	}

	public void requestCreateAvatar() {
		session.responseJson(Command.C_CREATE_AVATAR, "");
	}

	public long getUserId() {
		return userId;
	}

	public UserBase getUser() {
		return user;
	}

	public void setUser(UserBase user) {
		this.user = user;
		String ns = user.getFleet().getNs();
		String ew = user.getFleet().getEw();
		String mapId = ns + "_" + ew;
		logger.error("current map={}", mapId);

		if (user.getObjects().size() > 0)
			this.updateMapObjects(mapId, user.getObjects(), true);

		startTimer();
	}

	Timer timer = null;

	private void startTimer() {
		if (timer == null)
			timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				updateContext();
			}
		}, new Date(), 1000);
	}

	MapObjectData currentObject = null;

	private void updateContext() {
		if (this.isDoingBattle || this.currentObject != null) {
			return;
		}
		boolean containsObject = false;
		// int energy = this.user.getCommander().getEnergy();
		for (MapObjectData object : this.objects) {
			int artId = object.getArt();
			if (this.objectIdList.contains(artId)) {
				logger.info(
						"contain my objects:{}, event:{}, obj_id:"
								+ object.getObjectId(), artId,
						object.getEvent());
				this.handleBattleObject(object);
				containsObject = true;
				break;
			}
		}
		if (!containsObject) {
			moveMap();
		}
	}

	private void moveMap() {
		int energy = this.user.getCommander().getEnergy();
		if (energy < 1) {
			return;
		}
		int[] ints = this.getNextMapId();
		String ns = "N" + ints[0];
		String ew = "W0" + ints[1];
		String pos = "500,500";
		String exit = "500,500";
		ByteBuffer buffer = ByteBuffer.allocate(Short.MAX_VALUE);
		buffer.putInt(1);
		StringUtil.putString(buffer, ns);
		StringUtil.putString(buffer, ew);
		StringUtil.putString(buffer, pos);
		StringUtil.putString(buffer, exit);
		session.responseBinary(Command.C_SAIL_MOVE, buffer);
	}

	private void handleBattleObject(MapObjectData object) {
		currentObject = object;
		this.setDoingBattle(true);
		this.sendObjectCollision();
	}

	private void sendObjectCollision() {
		// int event_id = this.currentObject.getEvent();
		// int mission = this.currentObject.getMissionId();
		// long fleet = this.currentObject.getFleet();
		int objectId = this.currentObject.getObjectId();
		int x = 500;
		int y = 500;
		ByteBuffer buffer = ByteBuffer.allocate(Short.MAX_VALUE);
		buffer.putInt(objectId);
		buffer.putInt(x);
		buffer.putInt(y);
		this.session.responseBinary(Command.C_SAIL_EVENT_DETECT, buffer);
	}

	public boolean isDoingBattle() {
		return isDoingBattle;
	}

	public void setDoingBattle(boolean isDoingBattle) {
		this.isDoingBattle = isDoingBattle;
	}

	public void selectOption(int id, int costId, int costCount) {
		if (costId != 990004) {
			logger.error("receive illegal cost id:{}", costId);
			System.exit(1);
		}
		int energy = this.getUser().getCommander().getEnergy();
		if (energy > -costCount) {
			// battle
			String json = "{\"player_toward\":{\"y\":0.5,\"x\":0.5},\"opt_id\":1,\"event_toward\":{\"y\":0.5,\"x\":0.5}}";
			session.responseJson(Command.C_SAIL_EVENT_SELECT_OPTION, json);
		} else {
			// escape
			String json = "{\"player_toward\":{\"y\":0.5,\"x\":0.5},\"opt_id\":2,\"event_toward\":{\"y\":0.5,\"x\":0.5}}";
			session.responseJson(Command.C_SAIL_EVENT_SELECT_OPTION, json);
		}
	}

	public void handleEventPattern(int eventPattern) {
		if (eventPattern == 510004) {
			// win battle
			String json = "{\"ship\":[],\"win\":1}";
			session.responseJson(Command.C_BATTLE_END, json);
		}
		this.objects.remove(this.currentObject);
		this.currentObject = null;
		this.setDoingBattle(false);
	}
}
