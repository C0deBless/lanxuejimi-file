package common.struct;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Constants;

class ObjTime {
	static final int COOL_TIME_OFFSET = 1000 * 60;
	static final int INVISIBLE_TIME_OFFSET = 1000;

	int cool;
	int invisible;

	public int getCool() {
		return cool;
	}

	public void setCool(int cool) {
		this.cool = cool;
	}

	public int getInvisible() {
		return invisible;
	}

	public void setInvisible(int invisible) {
		this.invisible = invisible;
	}

	public ObjTime() {
	}

	public ObjTime(int cool, int invisible) {
		this.cool = cool;
		this.invisible = invisible;
	}

	public ObjTime clon() {
		ObjTime obj = new ObjTime(this.cool, this.invisible);
		return obj;
	}

	public Map<String, Object> prepare() {
		Map<String, Object> data = new HashMap<>();
		data.put(Constants.COOL, this.cool);
		data.put(Constants.INVISIBLE, this.invisible);
		return data;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(this.cool);
		bb.putInt(this.invisible);
	}
}

class ObjEffect {
	int create;
	int invisible;
	int remove;

	public ObjEffect() {
	}

	public ObjEffect(int create, int invisible, int remove) {
		super();
		this.create = create;
		this.invisible = invisible;
		this.remove = remove;
	}

	public ObjEffect clon() {
		ObjEffect obj = new ObjEffect(this.create, this.invisible, this.remove);
		return obj;
	}

	public Map<String, Object> prepare() {
		Map<String, Object> data = new HashMap<>();
		data.put(Constants.CREATE, this.create);
		data.put(Constants.INVISIBLE, this.invisible);
		data.put(Constants.REMOVE, this.remove);
		return data;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(this.create);
		bb.putInt(this.invisible);
		bb.putInt(this.remove);
	}

	public int getCreate() {
		return create;
	}

	public void setCreate(int create) {
		this.create = create;
	}

	public int getInvisible() {
		return invisible;
	}

	public void setInvisible(int invisible) {
		this.invisible = invisible;
	}

	public int getRemove() {
		return remove;
	}

	public void setRemove(int remove) {
		this.remove = remove;
	}

}

@JsonIgnoreProperties(ignoreUnknown = true)
public class MapObjectData {

	private static final Logger logger = LoggerFactory
			.getLogger(MapObjectData.class);
	public static final int TYPE_EVENT = 0;
	public static final int TYPE_FLEET = 1;
	public static final int TYPE_BOSS = 2;
	public static final int TYPE_PORT = 3;
	public static final int TYPE_BASE = 4;

	private boolean isDestroy = false;

	String mapId;

	ObjTime time;
	ObjEffect effect;
	Condition condition;
	ChildObject childObject;
	ObjAction action;
	EventResult eventResult;
	List<Integer> itemBufSkills;

	private Info info;

	public MapObjectData() {

	}

	public ObjTime getTime() {
		return time;
	}

	public void setTime(ObjTime time) {
		this.time = time;
	}

	public EventResult getEventResult() {
		return eventResult;
	}

	public void setEventResult(EventResult eventResult) {
		this.eventResult = eventResult;
	}

	public List<Integer> getItemBufSkills() {
		return itemBufSkills;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public MapObjectData(String mapId) {
		this.mapId = mapId;
	}

	public String getMapId() {
		return this.mapId;
	}

	public String getName() {
		return info.getName();
	}

	public int getEmblem() {
		return info.getEmblem();
	}

	public int getPower() {
		return info.getPower();
	}

	public int getInvisible() {
		return info.getInvisible();
	}

	public int getId() {
		return info.getId();
	}

	public int getObjectId() {
		return info.getObject_id();
	}

	public int getType() {
		return info.getType();
	}

	public int getArt() {
		return info.getArt();
	}

	public int getEvent() {
		return info.getEvent();
	}

	public long getFleet() {
		return info.getFleet_id();
	}

	public long getCoolTime() {
		return time.cool * ObjTime.COOL_TIME_OFFSET;
	}

	public long getInvisibleTime() {
		return time.invisible * ObjTime.INVISIBLE_TIME_OFFSET;
	}

	public void initTime(int cool, int invisible) {
		this.time = new ObjTime(cool, invisible);
	}

	public ObjEffect getEffect() {
		return effect;
	}

	public void setEffect(ObjEffect effect) {
		this.effect = effect;
	}

	public void initEffect(int create, int invisible, int remove) {
		this.effect = new ObjEffect(create, invisible, remove);
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public ChildObject getChildObject() {
		return childObject;
	}

	public ChildInfo getEventSuccessChildObject(int selectId) {
		return childObject.getEventSuccess(selectId);
	}

	public ChildInfo getEventFailureChildObject(int selectId) {
		return childObject.getEventFailure(selectId);
	}

	public void setChildObject(ChildObject childObject) {
		this.childObject = childObject;
	}

	public boolean isEmptyGeneratedCreate() {
		if (null == this.childObject) {
			return true;
		}
		return this.childObject.isEmptyGeneratedCreate();
	}

	public List<Integer> generatedCreateChild() {
		if (null == this.childObject) {
			return null;
		}
		return this.childObject.getGenerated().getCreate();
	}

	public List<Integer> generatedRemoveChild() {
		if (null == this.childObject) {
			return null;
		}
		return this.childObject.getGenerated().getRemove();
	}

	public boolean isEmptyGeneratedRemove() {
		if (null == this.childObject) {
			return true;
		}
		return this.childObject.isEmptyGeneratedRemove();
	}

	public ObjAction getAction() {
		return action;
	}

	public void setAction(ObjAction action) {
		this.action = action;
	}

	public void initEventResult(int success, int failure) {
		this.eventResult = new EventResult(success, failure);
	}

	public int getChance() {
		return this.condition.getChance();
	}

	public int getMove() {
		return this.condition.getMove();
	}

	public boolean isTransform() {
		return condition.isTransform();
	}

	public int getTransId() {
		return condition.getTransId();
	}

	public int getMissionId() {
		return info.getMission();
	}

	public static int calculationShipRate(int userPower, int enemyPower) {
		if (0 == userPower) {
			logger.warn("MapObjectData.calculationShipRate, userPower zero");
			return 0;
		}
		int result = (int) Math.round((enemyPower * 1.0 / userPower) * 100.0);

		if ((0 <= result) && (20 >= result)) {
			return Constants.ENERMY_RATE_0;
		} else if ((21 <= result) && (50 >= result)) {
			return Constants.ENERMY_RATE_1;
		} else if ((51 <= result) && (90 >= result)) {
			return Constants.ENERMY_RATE_2;
		} else if ((91 <= result) && (110 >= result)) {
			return Constants.ENERMY_RATE_3;
		} else if ((111 <= result) && (150 >= result)) {
			return Constants.ENERMY_RATE_4;
		} else if (151 <= result) {
			return Constants.ENERMY_RATE_5;
		} else {
			return Constants.ENERMY_RATE_0;
		}
	}

	public EventResultObjectAction getEventResultAction(EventResultType result) {
		if (EventResultType.SUCCESS == result) {
			return this.eventResult.getSuccess();
		} else {
			return this.eventResult.getFailure();
		}
	}

	public int getNoMissionId() {
		return this.condition.no_mission;
	}

	public int getNpcId() {
		return this.condition.npc;
	}

	public int getItemId() {
		return this.condition.item;
	}

	public boolean contains(List<MapObjectCore> list) {
		for (MapObjectCore core : list) {
			if (core.getId() == getId()) {
				return true;
			}
		}
		return false;
	}

	public void setItemBufSkills(List<Integer> skills) {
		this.itemBufSkills = skills;
	}

	public boolean isDestroy() {
		return isDestroy;
	}

	public void setDestroy(boolean isDestroy) {
		this.isDestroy = isDestroy;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}
}
