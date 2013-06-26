package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.serialize.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipBase implements Comparable<ShipBase>, Serializable {
	static final Logger logger = LoggerFactory.getLogger(ShipBase.class);
	public static int MAX_CARGO = 20;

	protected char flag = 'n';

	protected int ship_id;
	protected int order;

	protected long captain;
	protected int id;

	protected int hp;

	// item

	protected int part_m;
	protected int part_s;
	protected int part_t;
	protected int part_b;

	protected int part_u;
	protected int part_e;

	protected int kills;
	protected int dealt_dam;
	
	protected int enchantLev;
	protected double enchantExp;

	public ShipBase() {
	}

	public ShipBase(ShipBase base) {
		this(base.getFlag(), base.getShip_id(), base.getOrder(), base
				.getCaptain(), base.getId(), base.getHp(), base.getPart_m(),
				base.getPart_s(), base.getPart_t(), base.getPart_b(), 
				base.getPart_u(), base.getPart_e(), base.getKills(), base.getDealt_dam(),
				base.getEnchantLev(), base.getEnchantExp());

	}

	public ShipBase(char flag, int ship_id, int order, long captain, int id,
			int hp, int part_m, int part_s, int part_t, int part_b, int part_u,
			int part_e, int kills, int dealt_dam, int strengthenLev, double strengthenExp) {
		this.flag = flag;
		this.ship_id = ship_id;
		this.order = order;
		this.captain = captain;
		this.id = id;
		this.hp = hp;
		this.part_m = part_m;
		this.part_s = part_s;
		this.part_t = part_t;
		this.part_b = part_b;
		this.part_u = part_u;
		this.part_e = part_e;
		this.kills = kills;
		this.dealt_dam = dealt_dam;
		this.enchantLev = strengthenLev;
		this.enchantExp = strengthenExp;
	}

	public int getPart_e() {
		return part_e;
	}

	public void setPart_e(int part_e) {
		this.part_e = part_e;
	}

	public int getPart_m() {
		return part_m;
	}

	public void setPart_m(int part_m) {
		this.part_m = part_m;
	}

	public int getPart_s() {
		return part_s;
	}

	public void setPart_s(int part_s) {
		this.part_s = part_s;
	}

	public int getPart_t() {
		return part_t;
	}

	public void setPart_t(int part_t) {
		this.part_t = part_t;
	}

	public int getPart_b() {
		return part_b;
	}

	public void setPart_b(int part_b) {
		this.part_b = part_b;
	}

	public int getPart_u() {
		return part_u;
	}

	public void setPart_u(int part_u) {
		this.part_u = part_u;
	}

	public int getShip_id() {
		return ship_id;
	}

	public void setShip_id(int ship_id) {
		this.ship_id = ship_id;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public char getFlag() {
		return flag;
	}

	public void setFlag(char flag) {
		this.flag = flag;
	}

	public long getCaptain() {
		return captain;
	}

	public void setCaptain(long captain) {
		this.captain = captain;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHp() {
		return hp;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDealt_dam() {
		return dealt_dam;
	}

	public void setDealt_dam(int dealt_dam) {
		this.dealt_dam = dealt_dam;
	}

	public void increaseKills() {
		this.kills++;
	}

	public void addDealtDam(int dam) {
		this.dealt_dam += dam;
	}

	public void addHp(int hp) {
		this.hp += hp;
	}

	public int getEnchantLev() {
		return enchantLev;
	}

	public void setEnchantLev(int enchantLev) {
		this.enchantLev = enchantLev;
	}

	public double getEnchantExp() {
		return enchantExp;
	}

	public void setEnchantExp(double enchantExp) {
		this.enchantExp = enchantExp;
	}

	@Override
	public int compareTo(ShipBase o) {
		if (o == null) {
			return -1;
		}
		if (this.order > o.getOrder()) {
			return 1;
		}
		if (this.order == o.getOrder()) {
			return 0;
		}
		if (this.order < o.getOrder()) {
			return -1;
		}
		return 0;
	}

	public void serialize(ByteBuffer bb) {
		bb.putChar(flag);
		bb.putInt(ship_id);
		bb.putInt(order);
		bb.putLong(captain);
		bb.putInt(id);
		bb.putInt(hp);
		bb.putInt(part_m);
		bb.putInt(part_s);
		bb.putInt(part_t);
		bb.putInt(part_b);
		bb.putInt(part_u);
		bb.putInt(part_e);
		bb.putInt(kills);
		bb.putInt(dealt_dam);
		bb.putInt(enchantLev);
		bb.putDouble(enchantExp);
	}

	public static ShipBase deserialize(ByteBuffer bb) {
		char flag = bb.getChar();
		int ship_id = bb.getInt();
		int order = bb.getInt();
		long captain = bb.getLong();
		int id = bb.getInt();
		int hp = bb.getInt();
		int part_m = bb.getInt();
		int part_s = bb.getInt();
		int part_t = bb.getInt();
		int part_b = bb.getInt();
		int part_u = bb.getInt();
		int part_e = bb.getInt();
		int kills = bb.getInt();
		int dealt_dam = bb.getInt();
		int enchantLev = bb.getInt();
		double enchantExp = bb.getDouble();

		return new ShipBase(flag, ship_id, order, captain, id, hp, part_m,
				part_s, part_t, part_b, part_u, part_e, kills, dealt_dam, 
				enchantLev, enchantExp);
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void fillHp() {
	}

	public int getTier() {
		return -1;
	}

	public static List<ShipBase> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<ShipBase> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			ShipBase b = ShipBase.deserialize(bb);
			list.add(b);
		}
		return list;
	}
	
	public int getPower() {
		return 0;
	}
}
