package common.struct;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.serialize.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PortProductInfo implements Serializable {
	private static final Logger logger = LoggerFactory
			.getLogger(PortProductInfo.class);
	private List<PortProduct> item;
	private List<PortProduct> building;
	private List<PortProduct> ship;

	public List<PortProduct> getItem() {
		return item;
	}

	public void setItem(List<PortProduct> item) {
		this.item = item;
	}

	public List<PortProduct> getBuilding() {
		return building;
	}

	public void setBuilding(List<PortProduct> building) {
		this.building = building;
	}

	public List<PortProduct> getShip() {
		return ship;
	}

	public void setShip(List<PortProduct> ship) {
		this.ship = ship;
	}
	
	public int getShipCount() {
		if( null == ship ) {
			return 0;
		}
		return ship.size();
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		if (building == null) {
			buffer.putInt(0);
		} else {
			int size = building.size();
			buffer.putInt(size);
			for (int i = 0; i < size; i++) {
				building.get(i).serialize(buffer);
			}
		}
		if (ship == null) {
			buffer.putInt(0);
		} else {
			int size = ship.size();
			buffer.putInt(size);
			for (int i = 0; i < size; i++) {
				ship.get(i).serialize(buffer);
			}
		}
	}

	public static PortProductInfo deserialize(ByteBuffer buffer)
			throws IOException {
		PortProductInfo info = new PortProductInfo();

		int size = buffer.getInt();
		List<PortProduct> building = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			building.add(PortProduct.deserialize(buffer));
		}

		size = buffer.getInt();
		List<PortProduct> ship = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			building.add(PortProduct.deserialize(buffer));
		}

		info.building = building;
		info.ship = ship;
		return info;
	}

	public boolean isEmpty(int portId) {
		if ((null == item) || (0 >= item.size())) {
			logger.trace("PortProduct.isEmpty, item is empty, portId:"
					+ portId);
			return true;
		}

		if ((null == building) || (0 >= building.size())) {
			logger.trace("PortProduct.isEmpty, building is empty, portId:"
					+ portId);
			return true;
		}

		if ((null == ship) || (0 >= ship.size())) {
			logger.trace("PortProduct.isEmpty, ship is empty, portId:" + portId);
			return true;
		}

		return false;
	}

}
