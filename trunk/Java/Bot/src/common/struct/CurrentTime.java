package common.struct;

import java.util.Calendar;

public class CurrentTime {
	int hour;
	long currentTime;
	
	public CurrentTime() {
		Calendar oCalendar = Calendar.getInstance();
		this.hour = oCalendar.get(Calendar.HOUR_OF_DAY);
		this.currentTime = oCalendar.getTime().getTime();
	}
	
	public int getHour() {
		return hour;
	}
	public long getCurrentTime() {
		return currentTime;
	}
}
