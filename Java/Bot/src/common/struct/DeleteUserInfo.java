package common.struct;

import java.util.List;

public class DeleteUserInfo {
	int guildSRL;
	List<Long> allys;
	
	public DeleteUserInfo( int guildSrl, List<Long> allys ) {
		this.guildSRL = guildSrl;
		this.allys = allys;
	}

	public int getGuildSRL() {
		return guildSRL;
	}

	public List<Long> getAllys() {
		return allys;
	}	
}
