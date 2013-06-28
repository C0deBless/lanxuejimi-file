package common.struct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBase {
	private static final Logger logger = LoggerFactory
			.getLogger(UserBase.class);
	private static final List<Integer> MATERAIL_ITEM_LIST = Arrays.asList(
			130001, 130002, 130003);
	protected CommanderBase commander;
	protected FleetBase fleet;
	protected List<ShipBase> ships = new ArrayList<>();
	protected List<PCBase> pc = new ArrayList<>();
	protected List<Mission> missions = new ArrayList<>();
	protected Cash cash;
	protected List<EventLog> events = new ArrayList<>();
	protected List<Item> items = new ArrayList<>();
	protected List<UserSkill> skills = new ArrayList<>();
	protected List<FactionBase> factions = new ArrayList<>();
	protected List<Building> buildings = new ArrayList<>();
	protected List<Product> products = new ArrayList<>();
	protected List<WarehouseItem> warehouse = new ArrayList<>();
	protected List<Dock> docks = new ArrayList<>();
	protected List<BaseInfo> baselist = new ArrayList<BaseInfo>();
	protected List<String> exploreMap = new ArrayList<>();
	protected List<Integer> explorePort = new ArrayList<>();
	protected List<Integer> recipes = new ArrayList<>();
	protected List<MailBase> mails = new ArrayList<>();
	protected List<AllyBase> allys = new ArrayList<>();
	protected List<BattleCategory> categorys = new ArrayList<>();
	protected List<RevengeModel> revenges = new ArrayList<>();
	protected List<Long> interceptList = new ArrayList<>();
	protected List<AllyHelpModel> allyHelpRecords = new ArrayList<>();

	protected int mailTotalCount;
	protected int allyTotalCount;
	protected long regDate;
	protected TickBase tickBase = new TickBase(0, 0, 0);
	protected int saleMyRegGoodsCount;
	protected int amountRceivedCount;

	public UserBase() {
	}

	public UserBase(CommanderBase commander, FleetBase fleet, Cash cash) {
		this.commander = commander;
		this.fleet = fleet;
		this.cash = cash;
	}

	public UserBase(UserBase base) {
		this(base.getCommander(), base.getFleet(), base.getShip(),
				base.getPc(), base.getMissions(), base.getCash(), base
						.getEvents(), base.getItems(), base.getSkills(), base
						.getFactions(), base.getBuildings(),
				base.getProducts(), base.getWarehouse(), base.getDocks(), base
						.getBaselist(), base.getExploreMap(), base
						.getExplorePort(), base.getRecipes(), base
						.getCategorys(), base.getAllyTotalCount(),
				base.revenges, base.getTickBase(), base
						.getSaleMyRegGoodsCount(),
				base.getAmountRceivedCount(), base.getInterceptList(), base
						.getAllyHelpRecords());
		this.regDate = base.getRegDate();
	}

	public UserBase(CommanderBase commander, FleetBase fleet,
			List<ShipBase> ship, List<PCBase> pc, List<Mission> missions,
			Cash cash, List<EventLog> events, List<Item> items,
			List<UserSkill> skills, List<FactionBase> factions,
			List<Building> buildings, List<Product> products,
			List<WarehouseItem> warehouse, List<Dock> docks,
			List<BaseInfo> baselist, List<String> exploreMap,
			List<Integer> explorePort, List<Integer> recipes,
			List<BattleCategory> categorys, int allyTotalCount,
			List<RevengeModel> revenges, TickBase tickBase,
			int saleMyRegGoodsCount, int amountRceivedCount,
			List<Long> interceptList, List<AllyHelpModel> allyHelpRecords) {
		this.commander = commander;
		this.fleet = fleet;
		this.ships = ship;
		this.pc = pc;
		this.missions = missions;
		this.cash = cash;
		this.events = events;
		this.items = items;
		this.skills = skills;
		this.factions = factions;
		this.buildings = buildings;
		this.products = products;
		this.warehouse = warehouse;
		this.docks = docks;
		this.baselist = baselist;
		this.exploreMap = exploreMap;
		this.explorePort = explorePort;
		this.recipes = recipes;
		this.categorys = categorys;
		this.allyTotalCount = allyTotalCount;
		this.revenges = revenges;
		this.tickBase = tickBase;
		this.saleMyRegGoodsCount = saleMyRegGoodsCount;
		this.amountRceivedCount = amountRceivedCount;
		this.interceptList = interceptList;
		this.allyHelpRecords = allyHelpRecords;
	}

	public long getUserId() {
		return this.commander.getUser_id();
	}

	public List<RevengeModel> getRevenges() {
		return revenges;
	}

	public List<AllyHelpModel> getAllyHelpRecords() {
		return allyHelpRecords;
	}

	public void setAllyHelpRecords(List<AllyHelpModel> allyHelpRecords) {
		this.allyHelpRecords = allyHelpRecords;
	}

	public List<Long> getInterceptList() {
		return interceptList;
	}

	public void setInterceptList(List<Long> interceptList) {
		this.interceptList = interceptList;
	}

	public void setRevenges(List<RevengeModel> revenges) {
		this.revenges = revenges;
	}

	public CommanderBase getCommander() {
		return commander;
	}

	public void setCommander(CommanderBase commander) {
		this.commander = commander;
	}

	public FleetBase getFleet() {
		return fleet;
	}

	public void setFleet(FleetBase fleet) {
		this.fleet = fleet;
	}

	public List<ShipBase> getShip() {
		return ships;
	}

	public void setShip(List<ShipBase> ship) {
		this.ships = ship;
	}

	public List<BattleCategory> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<BattleCategory> categorys) {
		this.categorys = categorys;
	}

	public List<PCBase> getPc() {
		return pc;
	}

	public void setPc(List<PCBase> pc) {
		this.pc = pc;
	}

	public List<Mission> getMissions() {
		return missions;
	}

	public void setMissions(List<Mission> missions) {
		this.missions = missions;
	}

	public Cash getCash() {
		return cash;
	}

	public void setCash(Cash cash) {
		this.cash = cash;
	}

	public List<EventLog> getEvents() {
		return events;
	}

	public void setEvents(List<EventLog> events) {
		this.events = events;
	}

	public void addEventLog(EventLog eventLog) {
		this.events.add(eventLog);
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<UserSkill> getSkills() {
		return skills;
	}

	public void setSkills(List<UserSkill> skills) {
		this.skills = skills;
	}

	public List<FactionBase> getFactions() {
		return factions;
	}

	public void setFactions(List<FactionBase> factions) {
		this.factions = factions;
	}

	public FactionBase getFaction(int factionId) {

		if (this.factions == null)
			return null;
		for (FactionBase faction : factions) {
			if (faction.getF_id() == factionId)
				return faction;
		}
		return null;

	}

	public void addFaction(FactionBase faction) {
		if (this.factions == null)
			this.factions = new ArrayList<>();
		this.factions.add(faction);
	}

	public List<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}

	public void addBuilding(Building building) {
		if (this.buildings == null)
			this.buildings = new ArrayList<>();
		this.buildings.add(building);
	}

	public Building getBuilding(int seq) {
		if (this.getBuildings() == null) {
			return null;
		}
		for (Building b : this.buildings) {
			if (b.getBuilding_seq() == seq)
				return b;
		}
		return null;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void addProduct(Product product) {
		if (this.products == null)
			this.products = new ArrayList<>();
		this.products.add(product);
	}

	public Product getProduct(int buildingSeq) {
		if (this.getProducts() == null) {
			return null;
		}
		for (Product p : this.products) {
			if (p.getBuilding_seq() == buildingSeq)
				return p;
		}
		return null;
	}

	public List<WarehouseItem> getWarehouse() {
		return warehouse;
	}

	public WarehouseItem removeWarehouseItem(int portId, int itemId) {
		if ((null == this.warehouse) || this.warehouse.isEmpty()) {
			return null;
		}
		Iterator<WarehouseItem> iter = this.warehouse.iterator();
		while (iter.hasNext()) {
			WarehouseItem item = iter.next();
			if ((item.getPort_id() == portId) && (item.getItem_id() == itemId)) {
				iter.remove();
				return item;
			}
		}
		return null;
	}

	public void setWarehouse(List<WarehouseItem> warehouse) {
		if (null == warehouse) {
			warehouse = new ArrayList<>();
		}
		this.warehouse = warehouse;
	}

	public WarehouseItem getWarehouseItem(int itemId, int portId) {
		if (this.warehouse == null)
			return null;
		for (WarehouseItem item : this.warehouse) {
			if (item.getItem_id() == itemId && item.getPort_id() == portId)
				return item;
		}
		return null;
	}

	public WarehouseItem addWarehouseItem(WarehouseItem item) {
		if (this.warehouse == null) {
			this.warehouse = new ArrayList<>();
		}

		for (WarehouseItem cur : this.warehouse) {
			if (cur.getPort_id() == item.getPort_id()
					&& cur.getItem_id() == item.getItem_id()) {
				cur.addCount(item.getCount());
				return cur;
			}
		}

		this.warehouse.add(item);
		return item;
	}

	public WarehouseItem addWarehouseItem(final int portId, final int itemId,
			final int dCount) {
		if (this.warehouse == null) {
			this.warehouse = new ArrayList<>();
		}

		Iterator<WarehouseItem> iter = this.warehouse.iterator();
		while (iter.hasNext()) {
			WarehouseItem cur = iter.next();
			if ((cur.getPort_id() == portId) && (cur.getItem_id() == itemId)) {
				cur.addCount(dCount);
				if (0 >= cur.getCount()) {
					iter.remove();
				}
				return cur;
			}
		}

		if (0 < dCount) {
			WarehouseItem item = new WarehouseItem(portId, itemId, dCount, 0);
			this.warehouse.add(item);
		}
		return null;
	}

	public List<Dock> getDocks() {
		return docks;
	}

	public void setDocks(List<Dock> docks) {
		this.docks = docks;
	}

	public void addDock(Dock dock) {
		if (this.docks == null)
			this.docks = new ArrayList<>();
		this.docks.add(dock);
	}

	public List<BaseInfo> getBaselist() {
		return baselist;
	}

	public void addBaseInfo(BaseInfo base) {
		if (this.baselist == null)
			this.baselist = new ArrayList<>();
		this.baselist.add(base);
	}

	public BaseInfo getBaseInfo(int portId) {
		if (this.baselist == null)
			return null;
		for (BaseInfo base : baselist) {
			if (base.getPort_id() == portId) {
				return base;
			}
		}
		return null;
	}

	public void setBaselist(List<BaseInfo> baselist) {
		this.baselist = baselist;
	}

	public List<String> getExploreMap() {
		return exploreMap;
	}

	public void setExploreMap(List<String> exploreMap) {
		this.exploreMap = exploreMap;
	}

	public List<Integer> getExplorePort() {
		return explorePort;
	}

	public void setExplorePort(List<Integer> explorePort) {
		this.explorePort = explorePort;
	}

	public List<Integer> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Integer> recipes) {
		this.recipes = recipes;
	}

	public static Logger getLogger() {
		return logger;
	}

	public void deleteItem(int itemId) {
		if (this.items == null) {
			return;
		}
		Iterator<Item> it = this.items.iterator();
		while (it.hasNext()) {
			Item inv = it.next();
			if (inv.getId() == itemId) {
				it.remove();
			}
		}
	}

	public void deleteItem(int itemId, int count) {
		if (this.items == null) {
			return;
		}
		Iterator<Item> iter = this.items.iterator();
		while (iter.hasNext()) {
			Item inv = iter.next();
			if (inv.getId() == itemId) {
				inv.addCount(-count);
			}

			if (0 >= inv.getCount()) {
				iter.remove();
			}
		}
	}

	public boolean containsItem(int itemId) {
		if (this.items == null) {
			return false;
		}
		for (Item inv : this.items) {
			if (inv.getId() == itemId) {
				return true;
			}
		}
		return false;
	}

	public boolean containsItem(int itemId, int count) {
		if (this.items == null) {
			return false;
		}
		int sum = 0;
		for (Item inv : this.items) {
			if (inv.getId() == itemId) {
				sum += inv.getCount();
			}
		}
		if (sum >= count) {
			return true;
		}
		return false;
	}

	public Item getItem(int itemId) {
		if (this.items == null) {
			return null;
		}
		for (Item inv : this.items) {
			if (inv.getId() == itemId) {
				return inv;
			}
		}
		return null;
	}

	public Item addItem(Item item) {
		Item model = getItem(item.getId());
		if (null == model) {
			this.items.add(item);
			int count = item.getCount();
			int buy_price = item.getCount() * item.getBuy_price();
			if (0 < count) {
				buy_price = buy_price / count;
			}
			item.setBuy_price(buy_price);
			return item;
		} else {
			int oldbuy_price = model.getCount() * model.getBuy_price();
			int addbuy_price = item.getCount() * item.getBuy_price();
			int buy_price = oldbuy_price + addbuy_price;
			model.addCount(item.getCount());
			int count = model.getCount();
			if (0 < count) {
				buy_price = buy_price / count;
			}
			model.setBuy_price(buy_price);
			return model;
		}
	}

	public void addSkill(UserSkill skill) {
		if (this.skills == null)
			this.skills = new ArrayList<>();
		this.skills.add(skill);
	}

	public void removeAllSkill(int pcId) {
		Iterator<UserSkill> iter = this.skills.iterator();
		while (iter.hasNext()) {
			UserSkill s = iter.next();
			if (s.getPc_id() == pcId) {
				iter.remove();
			}
		}
	}

	private MailBase makeMail(int mailId, MailSendParameter mail, int guildSRL,
			String guildName) {
		Date d = new Date();
		long currentTime = d.getTime();
		MailBase m = new MailBase(mailId, mail.getFromName(), mail.getTitle(),
				mail.getMemo(), 'n', currentTime, mail.getItemId(),
				mail.getItemCount(), mail.getType(), guildSRL, guildName);
		return m;
	}

	public MailBase addMail_back(int mailId, MailSendParameter mail,
			int guildSRL, String guildName) {
		if (null == this.mails) {
			this.mails = new ArrayList<>();
		}

		MailBase m = makeMail(mailId, mail, guildSRL, guildName);

		this.mails.add(m);
		return m;
	}

	public MailBase addMail(int mailId, MailSendParameter mail) {
		return addMail(mailId, mail, 0, "");
	}

	public MailBase addMail(int mailId, MailSendParameter mail, int guildSRL,
			String guildName) {
		if (null == this.mails) {
			this.mails = new ArrayList<>();
		}

		boolean duple = false;
		MailBase m = makeMail(mailId, mail, guildSRL, guildName);
		for (MailBase base : this.mails) {
			if (base.getMail_id() == mailId) {
				duple = true;
				break;
			}
		}

		if (!duple) {
			this.mails.add(m);
			increaseMailTotalCount();
		}

		try {
			Collections.sort(this.mails);
			return m;
		} catch (Exception e) {
			logger.error("UserBase.addMail, msg:" + e.getMessage());
		}
		return null;

	}

	public MailBase getMail(int mailId) {
		if ((null == this.mails) || this.mails.isEmpty()) {
			return null;
		}

		for (MailBase m : this.mails) {
			if (m.getMail_id() == mailId) {
				return m;
			}
		}
		return null;
	}

	public MailBase readMail(int mailId) {
		MailBase mail = getMail(mailId);
		if (null == mail) {
			return null;
		}
		mail.setRead('y');
		return mail;
	}

	public MailBase deleteMail(int mailId) {
		if ((null == this.mails) || (this.mails.isEmpty())) {
			return null;
		}
		Iterator<MailBase> iter = this.mails.iterator();
		while (iter.hasNext()) {
			MailBase m = iter.next();
			if (m.getMail_id() == mailId) {
				iter.remove();
				decreaseMailTotalCount();
				return m;
			}
		}
		return null;
	}

	public void decreaseMailTotalCount() {
		this.mailTotalCount--;
	}

	public void increaseMailTotalCount() {
		this.mailTotalCount++;
	}

	public void setMailTotalCount(int total_count) {
		mailTotalCount = total_count;
	}

	public int getMailTotalCount() {
		if (null == this.mails || this.mails.isEmpty()) {
			return 0;
		}

		return mailTotalCount;
	}

	public Item mailItemStore(int mailId) {
		MailBase mail = getMail(mailId);
		if (null == mail) {
			return null;
		}

		int itemId = mail.getItemId();
		int count = mail.getItemCount();
		if ((0 >= itemId) || (0 >= count)) {
			return null;
		}
		mail.setItemId(0);
		mail.setItemCount(0);
		addItem(new Item(itemId, count));
		return getItem(itemId);
	}

	public int getSp() {
		return this.commander.getSp();
	}

	public void removeShip(int shipId) {

		int order = -1;
		Iterator<ShipBase> iter = this.ships.iterator();
		while (iter.hasNext()) {
			ShipBase s = iter.next();
			if (s.getShip_id() == shipId) {
				order = s.getOrder();
				iter.remove();
				break;
			}
		}

		if (-1 < order) {
			for (ShipBase s : this.ships) {
				if (s.getOrder() == -1) {
					continue;
				}
				if (order < s.getOrder()) {
					s.setOrder(s.getOrder() - 1);
				}
			}
		}
	}

	public void removeDock(final int shipId, final int portId) {
		Iterator<Dock> iter = this.docks.iterator();
		while (iter.hasNext()) {
			Dock d = iter.next();
			if ((d.getPort_id() == portId) && (d.getShip_id() == shipId)) {
				d.setShip_id(0);
				break;
			}
		}
	}

	public List<AllyBase> getAllys() {
		return allys;
	}

	public boolean isEmptyAlly() {
		if (null == this.allys) {
			return true;
		}
		return allys.isEmpty();
	}

	public void setAlly(List<AllyBase> ally) {
		this.allys = ally;
	}

	public List<AllyBase> addAllys(List<AllyBase> allyList, int allyTotalCount) {
		if (null == this.allys) {
			this.allys = new ArrayList<>();
		}

		if (null == allyList) {
			return null;
		}

		setAllyTotalCount(allyTotalCount);
		if (null == this.allys || this.allys.isEmpty()) {
			this.allys = allyList;
			return allyList;
		}

		if (this.allys.isEmpty()) {
			this.allys = allyList;
			return allyList;
		}

		List<AllyBase> list = new ArrayList<>();
		for (AllyBase newAlly : allyList) {
			boolean isDuple = false;
			for (AllyBase curAlly : this.allys) {
				if (curAlly.getAllyId() == newAlly.getAllyId()) {
					isDuple = true;
					break;
				}
			}

			if (!isDuple) {
				list.add(newAlly);
			}
		}
		this.allys.addAll(list);
		return list;

	}

	public AllyBase addAlly(AllyBase ally) {
		if (null == ally) {
			String msg = String.format(
					"UserBase.addAlly, ally is null full, userId:%d",
					commander.getUser_id());
			logger.error(msg);
			return null;
		}

		if (null == this.allys) {
			this.allys = new ArrayList<>();
		}

		for (AllyBase f : this.allys) {
			if (f.getAllyId() == ally.getAllyId()) {
				return null;
			}
		}
		this.allys.add(ally);
		return ally;
	}

	public void removeAlly(long allyId) {
		Iterator<AllyBase> iter = this.allys.iterator();
		while (iter.hasNext()) {
			AllyBase f = iter.next();
			if (f.getAllyId() == allyId) {
				iter.remove();
				return;
			}
		}
	}

	public AllyBase getAlly(long allyId) {
		if (null == this.allys) {
			this.allys = new ArrayList<>();
			return null;
		}

		for (AllyBase f : this.allys) {
			if (f.getAllyId() == allyId) {
				return f;
			}
		}
		return null;
	}

	public AllyBase getAlly(String name) {
		if (null == this.allys) {
			this.allys = new ArrayList<>();
			return null;
		}

		for (AllyBase f : this.allys) {
			if (f.getName().equalsIgnoreCase(name)) {
				return f;
			}
		}
		return null;
	}

	public boolean containsAlly(long allyId) {
		if (null == this.allys) {
			this.allys = new ArrayList<>();
			return false;
		}

		for (AllyBase f : this.allys) {
			if (f.getAllyId() == allyId) {
				return true;
			}
		}
		return false;
	}

	public boolean containsAlly(String name) {
		if (null == this.allys) {
			this.allys = new ArrayList<>();
			return false;
		}

		for (AllyBase f : this.allys) {
			if (f.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	public Item updateSetInventoryItem(Item item) {
		if (null == item) {
			return null;
		}

		if (null == this.items) {
			this.items = new ArrayList<>();
			if (0 < item.getCount()) {
				this.items.add(item);
				return item;
			}
			return null;
		}

		Iterator<Item> iter = this.items.iterator();
		while (iter.hasNext()) {
			Item invenItem = iter.next();
			if (invenItem.getId() == item.getId()) {
				invenItem.setCount(item.getCount());
				invenItem.setBuy_price(item.getBuy_price());
				if (0 >= item.getCount()) {
					iter.remove();
				}
				return invenItem;
			}
		}

		this.items.add(item);
		return item;
	}

	public Item removeInventoryItem(int itemId) {
		if (null == this.items) {
			return null;
		}

		Iterator<Item> iter = this.items.iterator();
		while (iter.hasNext()) {
			Item inv = iter.next();
			if (inv.getId() == itemId) {
				iter.remove();
				return inv;
			}
		}
		return null;
	}

	public String getName() {
		return this.commander.getName();
	}

	public int level() {
		return this.commander.getLevel();
	}

	public void setGuild_srl(int guild_srl) {
		this.commander.setGuild_srl(guild_srl);
	}

	public int getGuild_srl() {
		return this.commander.getGuild_srl();
	}

	public void setGuildName(String name) {
		this.commander.setGuildName(name);
	}

	public int getPortrait_id() {
		return this.commander.getPortrait_id();
	}

	public int getLevel() {
		return this.commander.getLevel();
	}

	public int getXp() {
		return this.commander.getXp();
	}

	public int getCombat() {
		return this.commander.getCombat();
	}

	public int getSailing() {
		return this.commander.getSailing();
	}

	public int getLuck() {
		return this.commander.getLuck();
	}

	public int getKnowledge() {
		return this.commander.getKnowledge();
	}

	public int getShip_slot() {
		return this.commander.getShip_slot();
	}

	public int getSkill_b1() {
		return this.commander.getSkill_b1();
	}

	public void setSkill_b1(int skillId) {
		this.commander.setSkill_b1(skillId);
	}

	public int getSkill_b2() {
		return this.commander.getSkill_b2();
	}

	public void setSkill_b2(int skillId) {
		this.commander.setSkill_b2(skillId);
	}

	public int getSkill_p1() {
		return this.commander.getSkill_p1();
	}

	public void setSkill_p1(int skillId) {
		this.commander.setSkill_p1(skillId);
	}

	public int getSkill_p2() {
		return this.commander.getSkill_p2();
	}

	public void setSkill_p2(int skillId) {
		this.commander.setSkill_p2(skillId);
	}

	public int getSkill_p3() {
		return this.commander.getSkill_p3();
	}

	public void setSkill_p3(int skillId) {
		this.commander.setSkill_p3(skillId);
	}

	public List<Building> getBuildingByPortId(int portId) {
		if (this.buildings == null) {
			return null;
		}
		List<Building> list = new ArrayList<>();
		for (Building building : this.buildings) {
			if (building.getPort_id() == portId) {
				list.add(building);
			}
		}
		return list;
	}

	public void sortShips() {
		if (null == this.ships) {
			logger.error("UserBase.sortShips, ships is null userId:"
					+ getUserId());
			return;
		}

		Collections.sort(this.ships);
	}

	public Item addItemCount(int itemId, int modifyCount) {
		Item item = getItem(itemId);
		if ((0 < modifyCount) && (null == item)) {
			item = new Item(itemId, 0);
			this.items.add(item);
		} else if ((0 >= modifyCount) && (null == item)) {
			String msg = String
					.format("UserBase.addItemCount, not found item, userId:%d, itemid:%d, modifyCount:%d",
							getUserId(), itemId, modifyCount);
			logger.error(msg);
			return null;
		}

		int curCount = item.getCount();
		int sum = item.addCount(modifyCount);
		if (0 < sum) {
			return item;
		} else if (0 == sum) {
			Item removedItem = removeInventoryItem(itemId);
			if (null == removedItem) {
				item.setCount(curCount);
				String msg = String
						.format("UserBase.addItemCount, failed remove item, userId:%d, itemid:%d, curCount:%d, modifyCount:%d",
								getUserId(), itemId, curCount, modifyCount);
				logger.error(msg);
				return null;
			}
			return removedItem;
		} else {
			item.setCount(curCount);
			String msg = String
					.format("UserBase.addItemCount, item count error, sum count, userId:%d, itemid:%d, curCount:%d, modifyCount:%d",
							getUserId(), itemId, curCount, modifyCount);
			logger.error(msg);
			return null;
		}
	}

	public int getInventorySlot() {
		return commander.getInventorySlot();
	}

	public void setInventorySlot(int inventorySlot) {
		this.commander.setInventorySlot(inventorySlot);
	}

	public int increaseInventorySlot() {
		return this.commander.increaseInventorySlot();
	}

	public List<Item> getMaterialItems() {
		if (this.items == null || this.items.size() <= 0) {
			return new ArrayList<>();
		} else {
			List<Item> list = new ArrayList<>();
			for (Item item : items) {
				if (MATERAIL_ITEM_LIST.contains(item.getId())) {
					list.add(item);
				}
			}
			return list;
		}
	}

	public BattleCategory getBattleCategory(int fieldId) {
		for (BattleCategory category : this.categorys) {
			if (category.getFieldId() == fieldId) {
				return category;
			}
		}
		return null;
	}

	public int getAllyTotalCount() {
		return allyTotalCount;
	}

	public void setAllyTotalCount(int allyCount) {
		this.allyTotalCount = allyCount;
	}

	public RevengeModel getRevengeModel(int srl) {
		if (this.revenges == null || this.revenges.isEmpty()) {
			return null;
		}
		for (RevengeModel model : this.revenges) {
			if (model.getSrl() == srl) {
				return model;
			}
		}
		return null;
	}

	public long getRegDate() {
		return regDate;
	}

	public void setRegDate(long regDate) {
		this.regDate = regDate;
	}

	public long getLastCareTime() {
		return tickBase.getLastCareTime();
	}

	public void setLastCareTime(long lastCareTime) {
		this.tickBase.setLastCareTime(lastCareTime);
	}

	public TickBase getTickBase() {
		return tickBase;
	}

	public void setTickBase(TickBase tickBase) {
		this.tickBase = tickBase;
	}

	public void setEnergyTick(long energyTick) {
		this.tickBase.setEnergyTick(energyTick);
	}

	public long getEnergyTick() {
		return this.tickBase.getEnergyTick();
	}

	public void addPTime(int pTime) {
		this.tickBase.setP_Time(this.tickBase.getP_Time() + pTime);
	}

	public int getSaleMyRegGoodsCount() {
		return saleMyRegGoodsCount;
	}

	public void setSaleMyRegGoodsCount(int saleMyRegGoodsCount) {
		this.saleMyRegGoodsCount = saleMyRegGoodsCount;
	}

	public int getAmountRceivedCount() {
		return amountRceivedCount;
	}

	public void setAmountRceivedCount(int amountRceivedCount) {
		this.amountRceivedCount = amountRceivedCount;
	}

	public AllyHelpModel getAllyHelpRecord(long targetUserId, int type) {
		for (AllyHelpModel model : this.allyHelpRecords) {
			if (model.getTargetUserId() == targetUserId
					&& model.getType() == type) {
				return model;
			}
		}
		return null;
	}
}
