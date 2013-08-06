package common.struct;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import common.Constants;

class AiPattern {
	int movement;
	int cognition;
	int sight_radius;
	String destination;

	public int getMovement() {
		return movement;
	}

	public void setMovement(int movement) {
		this.movement = movement;
	}

	public int getCognition() {
		return cognition;
	}

	public void setCognition(int cognition) {
		this.cognition = cognition;
	}

	public int getSight_radius() {
		return sight_radius;
	}

	public void setSight_radius(int sight_radius) {
		this.sight_radius = sight_radius;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

}

@JsonIgnoreProperties(ignoreUnknown = true)
class Status {
	static final String PLAYER = "player";
	int type;
	int speed;
	String location;
	int width;
	int height;
	int playerPos;
	int locationX;
	int locationY;

	public void initLocation(String loc) {
		if (null == loc) {
			return;
		}
		String[] arr = loc.split(",");
		if ((null == arr) || (2 != arr.length)) {
			return;
		}
		this.locationX = Integer.parseInt(arr[0]);
		this.locationY = Integer.parseInt(arr[1]);
	}

	public Status clon() {
		Status obj = new Status();
		obj.type = this.type;
		obj.speed = this.speed;
		obj.location = this.location;
		obj.height = this.height;
		obj.width = this.width;
		obj.locationX = this.locationX;
		obj.locationY = this.locationY;
		return obj;
	}

	public Map<String, Object> prepare(int x, int y) {
		Map<String, Object> data = new HashMap<>();
		data.put(Constants.TYPE, this.type);
		data.put(Constants.SPEED, this.speed);
		if (1 == playerPos) {
			data.put(Constants.X_pos, x);
			data.put(Constants.Y_pos, y);
		} else {
			data.put(Constants.X_pos, this.locationX);
			data.put(Constants.Y_pos, this.locationY);
		}
		data.put(Constants.WIDTH, this.width);
		data.put(Constants.HEIGHT, this.height);
		return data;
	}

	public void serialize(ByteBuffer bb, int x, int y) {
		bb.putInt(this.type);
		bb.putInt(this.speed);
		if (playerPos == 1) {
			bb.putInt(x);
			bb.putInt(y);
		} else {
			bb.putInt(this.locationX);
			bb.putInt(this.locationY);
		}
		bb.putInt(width);
		bb.putInt(height);
	}

	public boolean isGeneratePlayerPos() {
		if (1 == this.playerPos) {
			return true;
		}
		return false;
	}

	public static Status makeUseAdminShadowPVP() {
		Status obj = new Status();
		obj.type = 0;
		obj.speed = 0;
		obj.location = "";
		obj.height = 0;
		obj.width = 0;
		obj.locationX = 0;
		obj.locationY = 0;

		return obj;

	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getPlayerPos() {
		return playerPos;
	}

	public void setPlayerPos(int playerPos) {
		this.playerPos = playerPos;
	}

	public int getLocationX() {
		return locationX;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}

}

public class ObjAction {
	public static final int STAT_TYPE_SAILING = 0;
	public static final int STAT_TYPE_FLYING = 1;

	public static final int MOVEMENT_NONE = 0;
	public static final int MOVEMENT_EXPLORE = 1;
	public static final int MOVEMENT_LOCATION = 2;
	public static final int MOVEMENT_PATROL = 3;

	public static final int COGNITION_NONE = 0;
	public static final int COGNITION_RUNAWAY = 1;
	public static final int COGNITION_LOCATION = 2;
	public static final int COGNITION_CHASE = 3;
	public static final int COGNITION_WAIT = 4;

	AiPattern ai_pattern;
	Status stat;

	public AiPattern getAi_pattern() {
		return ai_pattern;
	}

	public void setAi_pattern(AiPattern ai_pattern) {
		this.ai_pattern = ai_pattern;
	}

	public Status getStat() {
		return stat;
	}

	public void setStat(Status stat) {
		this.stat = stat;
	}

	public int height() {
		return this.stat.height;
	}

	public int width() {
		return this.stat.width;
	}

}
