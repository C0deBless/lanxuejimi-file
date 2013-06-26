package common.struct;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import common.serialize.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CPortDataReqModel implements Serializable {

	private int port_id;
	private PortProductInfo port;

	@Override
	public void serialize(ByteBuffer buffer) {
		buffer.putInt(port_id);
		port.serialize(buffer);
	}

	public static CPortDataReqModel deserialize(ByteBuffer buffer)
			throws IOException {
		CPortDataReqModel model = new CPortDataReqModel();
		model.port_id = buffer.getInt();
		model.port = PortProductInfo.deserialize(buffer);
		return model;
	}

	public PortProductInfo getPort() {
		return port;
	}
	
	public List<PortProduct> getShip() {
		if( null == this.port ) {
			return null;
		}
		else {
			return port.getShip();
		}
	}
	
	public List<PortProduct> getBuilding() {
		if( null == this.port ) {
			return null;
		}
		else {
			return port.getBuilding();
		}
	}

	public List<PortProduct> getItem() {
		if (null == this.port) {
			return null;
		}
		return port.getItem();
	}

	public void setPort(PortProductInfo port) {
		this.port = port;
	}

	public int getPort_id() {
		return port_id;
	}

	public void setPort_id(int port_id) {
		this.port_id = port_id;
	}

	public boolean isEmpty(int portId) {
		if (null == port) {
			return true;
		}
		return port.isEmpty(portId);
	}
}
