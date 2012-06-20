package simplegs.command;

import simplegs.packet.Packet;

public interface ICommandFactory {
	public void execute(Packet packet);
}
