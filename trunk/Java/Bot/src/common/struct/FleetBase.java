package common.struct;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FleetBase {
	private String whr;
	private int port_id;
	private String ns;
	private String ew;
	private String pos;
	private int total_win;
	private int total_lose;
	private int victory;
	private int max_victory;
	private int basepvp_win;
	private int basepvp_lose;
	private int sell_count;

	public String getWhr() {
		return whr;
	}

	public void setWhr(String whr) {
		this.whr = whr;
	}

	public int getPort_id() {
		return port_id;
	}

	public void setPort_id(int port_id) {
		this.port_id = port_id;
	}

	public String getNs() {
		return ns;
	}

	public void setNs(String ns) {
		this.ns = ns;
	}

	public String getEw() {
		return ew;
	}

	public void setEw(String ew) {
		this.ew = ew;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public int getTotal_win() {
		return total_win;
	}

	public void setTotal_win(int total_win) {
		this.total_win = total_win;
	}

	public int getTotal_lose() {
		return total_lose;
	}

	public void setTotal_lose(int total_lose) {
		this.total_lose = total_lose;
	}

	public int getVictory() {
		return victory;
	}

	public void setVictory(int victory) {
		this.victory = victory;
	}

	public int getMax_victory() {
		return max_victory;
	}

	public void setMax_victory(int max_victory) {
		this.max_victory = max_victory;
	}

	public int getBasepvp_win() {
		return basepvp_win;
	}

	public void setBasepvp_win(int basepvp_win) {
		this.basepvp_win = basepvp_win;
	}

	public int getBasepvp_lose() {
		return basepvp_lose;
	}

	public void setBasepvp_lose(int basepvp_lose) {
		this.basepvp_lose = basepvp_lose;
	}

	public int getSell_count() {
		return sell_count;
	}

	public void setSell_count(int sell_count) {
		this.sell_count = sell_count;
	}

}