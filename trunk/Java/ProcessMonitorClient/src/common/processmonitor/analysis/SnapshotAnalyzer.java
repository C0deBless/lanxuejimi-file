package common.processmonitor.analysis;

import common.processmonitor.analysis.snapshot.InvocationSnapshot;
import common.processmonitor.handler.Packet;
import common.processmonitor.json.JsonHelper;

public class SnapshotAnalyzer {

	public static void analyze(Packet packet) {
		String strJson = new String(packet.getData());
		InvocationSnapshot snapshot = JsonHelper.deserialize(strJson,
				InvocationSnapshot.class);
		parseSnapshot(snapshot);
	}

	private static void parseSnapshot(InvocationSnapshot snapshot) {

	}
}
