package common.struct;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryPatternModel {
	private static final Logger logger = LoggerFactory
			.getLogger(InventoryPatternModel.class);

	private List<Integer> patterns;
	private List<Double> value1;
	private List<Double> value2;
	private List<Peer> peers = new ArrayList<>();

	public List<Integer> getPatterns() {
		return patterns;
	}

	public void setPatterns(List<Integer> patterns) {
		this.patterns = patterns;
	}

	public List<Double> getValue1() {
		return value1;
	}

	public void setValue1(List<Double> values) {
		this.value1 = values;
	}

	public List<Double> getValue2() {
		return value2;
	}

	public void setValue2(List<Double> value2) {
		this.value2 = value2;
	}

	public List<Peer> getPeers() {
		return peers;
	}

	public void generatePeer() {
		if (patterns.size() != value1.size()) {
			logger.error("InventoryPatternModel.generatePeer, the size of keys and value1 are not equal.");
			return;
		}
		this.peers.clear();
		for (int i = 0; i < this.patterns.size(); i++) {
			int pattern = this.patterns.get(i);
			double v1 = this.value1.get(i);
			Peer peer = new Peer();
			peer.pattern = pattern;
			peer.value1 = v1;
			if (this.value2.size() >= i + 1) {
				peer.value2 = this.value2.get(i);
			}
			this.peers.add(peer);
		}
	}

	public class Peer {
		public int pattern;
		public double value1;
		public double value2;
	}
}
