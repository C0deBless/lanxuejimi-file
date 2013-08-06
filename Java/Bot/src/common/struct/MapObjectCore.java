package common.struct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapObjectCore {
	private static final Logger logger = LoggerFactory
			.getLogger(MapObjectCore.class);

	public enum STATUS {
		NONE, ENABLED, INVISIBLE, EVENT_CREATED, EVENT_IN_PROGRESS, EVENT_COMPLETED
	}

	MapObjectData mapObj;
	long createdTime;
	long time;
	STATUS status;

	public MapObjectCore(MapObjectData mapObj, long createdTime, long time) {
		super();
		this.mapObj = mapObj;
		this.createdTime = createdTime;
		this.time = time;
	}

	public String getMapId() {
		return this.mapObj.getMapId();
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public int getObjectId() {
		return this.mapObj.getId();
	}

	public int getArtId() {
		return this.mapObj.getArt();
	}

	public MapObjectData getMapObj() {
		return mapObj;
	}

	public void setMapObj(MapObjectData mapObj) {
		this.mapObj = mapObj;
	}

	public long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean isInvisibleTimeout(long currentTime) {
		if ((currentTime - this.time) >= (mapObj.getInvisibleTime() - 500)) {
			return true;
		}
		return false;
	}

	public int getMissionId() {
		return mapObj.getMissionId();
	}

	public int getEventId() {
		return mapObj.getEvent();
	}

	public long getFleetId() {
		return mapObj.getFleet();
	}

	public long getCoolTime() {
		return this.mapObj.getCoolTime();
	}

	public long getInvisibleTime() {
		return this.mapObj.getInvisibleTime();
	}

	public ChildInfo getEventChildInfo(EventResultType eventResult, int selectId) {
		ChildInfo child = null;
		if (EventResultType.SUCCESS == eventResult) {
			child = mapObj.getEventSuccessChildObject(selectId);
		} else if (EventResultType.FAILURE == eventResult) {
			child = mapObj.getEventFailureChildObject(selectId);
		}

		if (null == child) {
			int id = Integer.MIN_VALUE;
			if (null != this.mapObj) {
				logger.trace("MapObjectCore.getEventChildInfo, mapObj is null");
				id = this.mapObj.getId();
			}
			logger.trace(String.format(
					"MapObjectCore.getEventChildInfo, child Object data is null, objectId:%d, "
							+ "eventResult:%s, selectId:%d", id,
					eventResult.toString(), selectId));
		}
		return child;
	}

	public EventResultObjectAction getEventResutlAction(
			EventResultType eventResult) {
		return this.mapObj.getEventResultAction(eventResult);

	}

	public int getNoMissionId() {
		return this.mapObj.getNoMissionId();
	}

	public int getNpcId() {
		return this.mapObj.getNpcId();
	}

	public int getItemId() {
		return this.mapObj.getItemId();
	}

	public int getTransId() {
		return this.mapObj.getTransId();
	}

	public boolean isRandomObj() {
		if ((0 < this.mapObj.getChance())
				&& (Condition.MOVE_MAP_IN == this.mapObj.getMove())
				&& (0 == this.getMapObj().getMissionId())) {
			return true;
		}
		return false;
	}

	public int getId() {
		return this.mapObj.getId();
	}

	public int getEmblem() {
		return this.mapObj.getEmblem();
	}

}
