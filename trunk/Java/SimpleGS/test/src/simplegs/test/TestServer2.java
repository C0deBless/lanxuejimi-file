package simplegs.test;

import java.io.IOException;

import simplegs.GameServer;
import simplegs.command.ICommandFactory;
import simplegs.jmx.ProfileServer;
import simplegs.log.Log;
import simplegs.packet.Packet;

public class TestServer2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			GameServer gameserver = new GameServer(8000, new ICommandFactory() {

				@Override
				public void execute(Packet packet) {
					Log.info(
							"execute packet. command:{}, packetId:{},  data:{}",
							packet.command(), packet.packetId(), packet.data());
				}
			});
			gameserver.run();
			ProfileServer profileServer = new ProfileServer();
			profileServer.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
