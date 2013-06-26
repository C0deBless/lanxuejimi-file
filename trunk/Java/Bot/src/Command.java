
public class Command {
	public static final int NULL = -9999;
	public static final int S_PING = 1310;
	public static final int D_PING = 2310;
	public static final int DC_PING = 3310;
	public static final int S_ERROR = 1311;

	public static final int C_CHECK_EVENT = 312;
	public static final int S_CHECK_EVENT = 1312;

	public static final int C_REQ_PORTDATA = 313;
	public static final int S_REQ_PORTDATA = 1313;
	public static final int C_REQ_ITEMLIST = 314;
	public static final int S_REQ_ITEMLIST = 1314;
	public static final int C_REQ_SKILLLIST = 315;
	public static final int S_REQ_SKILLLIST = 1315;
	public static final int C_REQ_NPCLIST = 316;
	public static final int S_REQ_NPCLIST = 1316;

	public static final int C_FB_FIRENDS = 317;

	// Login
	public static final int C_LOGIN = 300; // log
	public static final int C_UDATAREQ = 301; // log
	public static final int C_CREATE_AVATAR = 306;

	public static final int C_RELOAD_CASH_ITEM = 308;
	public static final int S_RELOAD_CASH_ITEM = 1308;

	// GOLD, ENERGY EXCHANGE
	public static final int C_ENERGY_EXCHANGE = 320;
	public static final int S_ENERGY_EXCHANGE = 1320;
	public static final int C_GOLD_EXCHANGE = 323;
	public static final int S_GOLD_EXCHANGE = 1323;

	public static final int C_SHOP_BUY = 324;
	public static final int S_SHOP_BUY = 1324;

	public static final int C_PROTECTION_TIME_EXCHANGE = 325;
	public static final int S_PROTECTION_TIME_EXCHANGE = 1325;

	// Sailing
	public static final int C_SAIL_MOVE = 400; // log
	public static final int C_SAIL_ARRIVE_PORT = 401; // log
	public static final int C_SAIL_LEAVE_PORT = 402; // log
	public static final int C_SAIL_EVENT_DETECT = 403;
	public static final int C_SAIL_EVENT_SELECT_OPTION = 404;
	public static final int C_SHADOW_PVP_SELECT = 408;

	public static final int C_BUY_INVENTORY_SLOT = 460;

	// guild
	public static final int C_GUILD_INVITE_CONFIRM = 465;
	public static final int S_GUILD_INVITE_CONFIRM = 1465;

	public static final int C_GUILD_SEARCH = 468;
	public static final int S_GUILD_SEARCH = 1468;

	public static final int C_GUILD_SET_PERMISSION = 469;
	public static final int S_GUILD_SET_PERMISSION = 1469;

	public static final int C_GUILD_CREATE = 470;
	public static final int S_GUILD_CREATE = 1470;

	public static final int C_GUILD_INFO = 471;
	public static final int S_GUILD_INFO = 1471;

	public static final int C_GUILD_JOIN_REQUEST = 472;
	public static final int S_GUILD_JOIN_REQUEST = 1472;

	public static final int C_GUILD_LIST = 473;
	public static final int S_GUILD_LIST = 1473;

	public static final int C_GUILD_JOIN_CONFIRM = 474;
	public static final int S_GUILD_JOIN_CONFIRM = 1474;

	public static final int C_GUILD_EXILE = 475;
	public static final int S_GUILD_EXILE = 1475;

	public static final int C_GUILD_MEMBER_INFO = 476;
	public static final int S_GUILD_MEMBER_INFO = 1476;

	public static final int C_GUILD_MAIL_SEND = 477;
	public static final int S_GUILD_MAIL_SEND = 1477;

	public static final int C_GUILD_DESTROY = 478;
	public static final int S_GUILD_DESTROY = 1478;

	public static final int C_GUILD_WRITE_NOTICE = 479;
	public static final int S_GUILD_WRITE_NOTICE = 1479;

	// ally
	public static final int C_ALLY_ADD = 480;
	public static final int S_ALLY_ADD = 1480;

	public static final int C_ALLY_LIST = 481;
	public static final int S_ALLY_LIST = 1481;

	public static final int C_ALLY_DELETE = 482;
	public static final int S_ALLY_DELETE = 1482;

	// public static final int C_ALLY_DAILY_SUPPORT = 483;
	// public static final int S_ALLY_DAILY_SUPPORT = 1483;

	// public static final int C_ALLY_UNLOCK_SLOT = 485;
	// public static final int S_ALLY_UNLOCK_SLOT = 1485;

	public static final int C_ALLY_REQUEST = 486;
	public static final int S_ALLY_REQUEST = 1486;

	public static final int C_ALLY_CONFIRM = 487;
	public static final int S_ALLY_CONFIRM = 1487;

	// mail box
	public static final int C_MAIL_SEND = 490;
	public static final int S_MAIL_SEND = 1490;

	public static final int C_MAIL_LIST = 491;
	public static final int S_MAIL_LIST = 1491;

	public static final int C_MAIL_READ = 492;
	public static final int S_MAIL_READ = 1492;

	public static final int C_MAIL_DELETE = 493;
	public static final int S_MAIL_DELETE = 1493;

	public static final int C_MAIL_ITEM_STORE = 494;
	public static final int S_MAIL_ITEM_STORE = 1494;

	// Trade
	public static final int C_SELL_ALL_REQ = 502;
	public static final int C_BUY_SHIP_REQ = 503;
	public static final int C_SELL_SHIP_REQ = 504;
	public static final int C_TRADE_REQ = 505;
	public static final int C_BUY_SHIP_SLOT = 506;
	public static final int S_BUY_SHIP_SLOT = 1506;

	// enchant
	public static final int C_SHIP_ENCHANT = 520;
	public static final int S_SHIP_ENCHANT = 1520;

	// auction
	public static final int C_AUCTION_LIST = 530;
	public static final int S_AUCTION_LIST = 1530;
	public static final int C_AUCTION_REG_GOODS = 531;
	public static final int S_AUCTION_REG_GOODS = 1531;
	public static final int C_AUCTION_MY_GOODS = 532;
	public static final int S_AUCTION_MY_GOODS = 1532;
	public static final int C_AUCTION_BID = 533;
	public static final int S_AUCTION_BID = 1533;
	public static final int C_AUCTION_GET_GOODS_INFO = 534;
	public static final int S_AUCTION_GET_GOODS_INFO = 1534;
	public static final int C_AUCTION_MY_BID_LIST = 535;
	public static final int S_AUCTION_MY_BID_LIST = 1535;
	public static final int C_AUCTION_GAIN_ITEM_PAY = 536;
	public static final int S_AUCTION_GAIN_ITEM_PAY = 1536;

	// Battle
	public static final int C_BATTLE_BEGIN = 600;
	public static final int S_BATTLE_BEGIN = 1600;

	public static final int C_BATTLE_END = 601;
	public static final int S_BATTLE_END = 1601;

	public static final int C_BATTLE_MOVE = 602;
	public static final int S_BATTLE_MOVE = 1602;

	public static final int C_BATTLE_ATTACK = 603;
	public static final int S_ATTACK = 1603;

	public static final int C_BATTLE_SKILL = 604;
	public static final int S_BATTLE_SKILL = 1604;

	public static final int C_TRIGGER = 609;
	public static final int S_TRIGGER = 1609;

	public static final int C_CUTSCENE = 610;
	public static final int S_CUTSCENE = 1610;

	public static final int C_BATTLE_ALLY_SUPPORT = 612;
	public static final int S_BATTLE_ALLY_SUPPORT = 1612;

	// mission command
	public static final int C_TASK_DONE_WITHCASH = 702;
	public static final int C_MISSOIN_CANCEL = 703;
	public static final int C_MISSION_RETRY = 707;
	// action command
	public static final int C_CLICK = 800;

	public static final int C_NPC_LEVEL_UP = 813;
	public static final int C_BONUS_USE = 814;

	// npc
	public static final int C_HIRE_REQ = 902;
	public static final int C_FIRE_REQ = 903;
	public static final int C_PUB_TALK_REQ = 904;
	public static final int C_COMMISSION_SHIP_PC_REQ = 905;
	public static final int C_DISMISSAL_SHIP_PC_REQ = 906;
	public static final int C_BUY_LICENSE = 907;
	public static final int C_COMMISSION_SHIP_PC_SIMUL = 908;

	// ship management
	public static final int C_RENAME_SHIP = 910;

	public static final int S_LOGIN = 1300;
	public static final int S_UDATAREQ = 1301;
	public static final int S_CREATE_AVATR = 1306;

	// sail
	public static final int S_SAIL_MOVE = 1400;
	public static final int S_SAIL_ARRIVE_PORT = 1401;
	public static final int S_SAIL_LEAVE_PORT = 1402;
	public static final int S_SAIL_EVENT_DETECT = 1403;
	public static final int S_SAIL_EVENT_SELECT_OPTION = 1404;
	public static final int S_SHADOW_PVP_SELECT = 1408;

	public static final int S_BUY_INVENTORY_SLOT = 1460;

	// Trade
	public static final int S_OBJECT_UPDATE = 1405;
	public static final int S_SELL_ALL_REQ = 1502;
	public static final int S_BUY_SHIP_REQ = 1503;
	public static final int S_SELL_SHIP_REQ = 1504;
	public static final int S_TRADE_REQ = 1505;

	public static final int S_MISSION_ADD = 1700;
	public static final int S_MISSION_DONE = 1701;
	public static final int S_TASK_DONE_WITHCASH = 1702;
	public static final int S_MISSOIN_CANCEL = 1703;
	public static final int S_MISSION_TIMEOUT = 1704;
	public static final int S_TASK_DONE = 1705;
	public static final int S_MISSION_FAIL = 1706;
	public static final int S_MISSION_RETRY = 1707;

	public static final int C_OFFICE_MISSION_LIST = 708;
	public static final int S_OFFICE_MISSION_LIST = 1708;

	public static final int C_OFFICE_MISSION_ACCEPT = 709;
	public static final int S_OFFICE_MISSION_ACCEPT = 1709;

	public static final int C_MISSION_GET_PROMOTION_REWARD = 710;
	public static final int S_MISSION_GET_PROMOTION_REWARD = 1710;

	public static final int C_MISSION_LOAD_CUTSCENE = 711;
	public static final int S_MISSION_LOAD_CUTSCENE = 1711;

	public static final int C_BATTLE_ADMIN_VICTORY = 613;
	public static final int C_BATTLE_ADMIN_DEFEAT = 614;

	//
	public static final int S_CLICK = 1800;

	public static final int S_LEVELUP = 1810;
	public static final int S_UPDATEXP = 1811;
	public static final int S_NPC_LEVEL_CAN_UP = 1812;
	public static final int S_NPC_LEVEL_UP = 1813;
	public static final int S_BONUS_USE = 1814;

	// npc
	public static final int S_HIRE_REQ = 1902;
	public static final int S_FIRE_REQ = 1903;
	public static final int S_PUB_TALK_REQ = 1904;
	public static final int S_COMMISSION_SHIP_PC_REQ = 1905;
	public static final int S_DISMISSAL_SHIP_PC_REQ = 1906;
	public static final int S_BUY_LICENSE = 1907;
	public static final int S_COMMISSION_SHIP_PC_SIMUL = 1908;

	// ship management
	public static final int S_RENAME_SHIP = 1910;

	public static final int C_ADMIN_CMD = 4300;
	public static final int S_ADMIN_CMD = 5300;

	public static final int S_UPDATE_COMMANDER = 5301;

	// Item
	public static final int C_ITEM_BUY = 830;
	public static final int S_ITEM_BUY = 1830;

	public static final int C_ITEM_SELL = 831;
	public static final int S_ITEM_SELL = 1831;

	public static final int C_ITEM_USE = 832;
	public static final int S_ITEM_USE = 1832;

	public static final int C_ITEM_EQUIP = 833;
	public static final int S_ITEM_EQUIP = 1833;

	public static final int C_ITEM_BUF_SKILL_EQUIP = 834;
	public static final int S_ITEM_BUF_SKILL_EQUIP = 1834;

	public static final int C_ITEM_BUF_SKILL_UNEQUIP = 835;
	public static final int S_ITEM_BUF_SKILL_UNEQUIP = 1835;

	public static final int C_ITEM_WARP = 836;
	public static final int S_ITEM_WARP = 1836;

	public static final int C_ITEM_RESET_STAT = 837;
	public static final int S_ITEM_RESET_STAT = 1837;

	public static final int C_ITEM_CACHA_USE = 838;
	public static final int S_ITEM_CACHA_USE = 1838;

	public static final int C_CARE = 840;
	public static final int S_CARE = 1840;

	// faction
	public static final int C_FACTION_MEET = 321;
	public static final int S_FACTION_MEET = 1321;

	public static final int S_FACTION_UPDATE = 1322;

	// building
	public static final int C_BUILD_BUILDING = 331;
	public static final int S_BUILD_BUILDING = 1331;

	public static final int C_REMOVE_BUILDING = 333;
	public static final int S_REMOVE_BUILDING = 1333;

	public static final int C_UPGRADE_BUILDING = 334;
	public static final int S_UPGRADE_BUILDING = 1334;

	public static final int C_BUILD_COMPLETE = 335;
	public static final int S_BUILD_COMPLETE = 1335;

	public static final int C_BUY_SPACE = 336;
	public static final int S_BUY_SPACE = 1336;

	// public static final int C_MAKE_PRODUCT = 337;
	// public static final int S_MAKE_PRODUCT = 1337;
	public static final int S_PRODUCT_UPDATE = 1337;

	public static final int C_HARVEST_PRODUCT = 338;
	public static final int S_HARVEST_PRODUCT = 1338;

	public static final int C_BASE_DATAREQ = 339;
	public static final int S_BASE_DATAREQ = 1339;

	public static final int C_MOVE_ITEM = 340;
	public static final int S_MOVE_ITEM = 1340;

	public static final int C_DOCK_SHIP = 342;
	public static final int S_DOCK_SHIP = 1342;

	public static final int C_BUILD_SHIP = 345;
	public static final int S_BUILD_SHIP = 1345;

	public static final int C_BUILD_SHIP_COMPLETE = 346;
	public static final int S_BUILD_SHIP_COMPLETE = 1346;

	public static final int C_CRAFTING_START = 347;
	public static final int S_CRAFTING_START = 1347;

	public static final int C_CRAFTING_COMPLETE = 348;
	public static final int S_CRAFTING_COMPLETE = 1348;

	public static final int C_ORDER_SHIP = 350;
	public static final int S_ORDER_SHIP = 1350;

	public static final int C_SANDGLASS_USECASH = 354;
	public static final int S_SANDGLASS_USECASH = 1354;

	public static final int S_RECIPE_NEW = 1353;

	public static final int C_MANAGER_EQUIP = 356;
	public static final int S_MANAGER_EQUIP = 1356;

	public static final int S_BUILDING_UPDATE = 1355;

	public static final int C_PRODUCT_CHANGE = 357;
	public static final int S_PRODUCT_CHANGE = 1357;

	public static final int C_BUILDING_MOVE = 358;
	public static final int S_BUILDING_MOVE = 1358;

	public static final int C_MANAGER_UNEQUIP = 359;
	public static final int S_MANAGER_UNEQUIP = 1359;

	public static final int C_COLONY_PVP_START = 620;
	public static final int S_COLONY_PVP_START = 1620;

	public static final int C_COLONY_PVP_PLUNDER = 615;
	public static final int S_COLONY_PVP_PLUNDER = 1615;

	public static final int S_COLONY_PVP_END = 1617;

	public static final int C_COLONY_PVP_DATAREQ = 621;
	public static final int S_COLONY_PVP_DATAREQ = 1621;

	public static final int C_REPAIR_BUILDING = 622;
	public static final int S_REPAIR_BUILDING = 1622;

	public static final int S_NOTIFICATION = 1623;

	public static final int C_REMOVE_TRAP = 624;
	public static final int S_REMOVE_TRAP = 1624;

	public static final int C_SHIP_SWITCH = 625;
	public static final int S_SHIP_SWITCH = 1625;

	public static final int C_DOCKSHIP_MOVE = 626;
	public static final int S_DOCKSHIP_MOVE = 1626;

	public static final int S_PROTECT_MODE_UPDATE = 1627;

	// explore cmd
	public static final int S_EXPLORE_REWARD = 1360;
	public static final int S_MOVE_FAILED = 1361;

	// world map cmd
	public static final int C_EXPLORE_DATAREQ = 370;
	public static final int S_EXPLORE_DATAREQ = 1370;

	public static final int C_MISSION_OBJECTREQ = 371;
	public static final int S_MISSION_OBJECTREQ = 1371;

	// skill
	public static final int C_SKILL_EQUIP = 380;
	public static final int S_SKILL_EQUIP = 1380;
	public static final int S_SKILL_TIMEOUT = 1381;
	public static final int S_SKILL_NEW = 1382;

	public static final int C_SKILL_PRACTICE = 383;
	public static final int S_SKILL_PRACTICE = 1383;

	public static final int C_SKILL_EXCHANGE = 384;
	public static final int S_SKILL_EXCHANGE = 1384;

	public static final int C_SKILL_ABANDON = 385;
	public static final int S_SKILL_ABANDON = 1385;

	public static final int C_NPC_SKILL_EQUIP = 386;
	public static final int S_NPC_SKILL_EQUIP = 1386;

	public static final int C_NPC_SKILL_SIMUL = 387;
	public static final int S_NPC_SKILL_SIMUL = 1387;

	public static final int C_SKILL_ACCEPT = 388;
	public static final int S_SKILL_ACCEPT = 1388;

	// stat data req
	public static final int C_NPC_STAT_REQ = 410;
	public static final int S_NPC_STAT_REQ = 1410;

	public static final int C_SHIP_STAT_REQ = 411;
	public static final int S_SHIP_STAT_REQ = 1411;

	public static final int C_CHAT = 990;
	public static final int S_CHAT = 1990;

	public static final int C_TUTORIAL_PROGRESS = 991;
	public static final int S_TUTORIAL_PROGRESS = 1991;

	public static final int C_BLOCK_USER = 992;
	public static final int S_BLOCK_USER = 1992;

	public static final int C_ESCAPE = 993;
	public static final int S_ESCAPE = 1993;

	public static final int C_CHAT_CHANGE_CHANNEL = 994;
	public static final int S_CHAT_CHANGE_CHANNEL = 1994;

	// multi play 420+
	public static final int C_MULTI_ROOM_LIST = 420;
	public static final int S_MULTI_ROOM_LIST = 1420;

	public static final int C_MULTI_ROOM_MAKE = 421;
	public static final int S_MULTI_ROOM_MAKE = 1421;

	public static final int C_MULTI_ROOM_INFO = 422;
	public static final int S_MULTI_ROOM_INFO = 1422;

	public static final int C_MULTI_ROOM_JOIN = 423;
	public static final int S_MULTI_ROOM_JOIN = 1423;

	public static final int S_MULTI_ROOM_IN_UPDATE = 1424;

	public static final int C_MULTI_ROOM_OUT = 425;
	public static final int S_MULTI_ROOM_OUT = 1425;

	public static final int S_MULTI_ROOM_OUT_UPDATE = 1426;

	public static final int C_MULTI_ROOM_REMOVE_USER = 427;
	public static final int S_MULTI_ROOM_REMOVE_USER = 1427;

	public static final int C_MULTI_ROOM_SEARCH = 428;
	public static final int S_MULTI_ROOM_SEARCH = 1428;

	public static final int C_MULTI_ROOM_INVITE = 429;
	public static final int S_MULTI_ROOM_INVITE = 1429;

	public static final int S_MULTI_ROOM_INVITE_PUSH = 1430;

	public static final int C_MULTI_ROOM_INVITABLE_LIST = 431;
	public static final int S_MULTI_ROOM_INVITABLE_LIST = 1431;

	public static final int C_MULTI_ROOM_QUICK_MATCHING = 432;
	public static final int S_MULTI_ROOM_QUICK_MATCHING = 1432;

	public static final int C_MULTI_ROOM_START_LOADING = 435;
	public static final int S_MULTI_ROOM_START_LOADING = 1435;

	public static final int S_MULTI_ROOM_START_LOADING_PUSH = 1436;

	public static final int C_MULTI_ROOM_START_READY = 438;
	public static final int S_MULTI_ROOM_START_READY = 1438;

	public static final int S_MULTI_ROOM_START_PUSH = 1439;

	public static final int C_MULTI_ROOM_CMD = 440;
	public static final int S_MULTI_ROOM_CMD = 1440;

	public static final int S_MULTI_ROOM_CMD_PUSH = 1441;

	public static final int S_MULTI_BATTLE_END_PUSH = 1442;

	public static final int C_MULTI_ROOM_READY = 443;
	public static final int S_MULTI_ROOM_READY = 1443;

	public static final int S_MULTI_ROOM_READY_PUSH = 1444;

	public static final int C_MULTI_ROOM_SELECT_SHIP = 445;
	public static final int S_MULTI_ROOM_SELECT_SHIP = 1445;

	public static final int S_MULTI_ROOM_SELECT_SHIP_PUSH = 1446;

	public static final int C_REVENGE_DELETE = 447;
	public static final int S_REVENGE_DELETE = 1447;

	public static final int S_REVENGE_TIMEOUT = 1448;

	public static final int C_REVENGE_DATA_REQ = 449;
	public static final int S_REVENGE_DATA_REQ = 1449;

	public static final int C_REVENGE_RAID = 451;
	public static final int S_REVENGE_RAID = 1451;

	public static final int C_COLONY_SCOUNT = 452;
	public static final int S_COLONY_SCOUNT = 1452;

	public static final int C_REVENGE_HELP_LIST = 453;
	public static final int S_REVENGE_HELP_LIST = 1453;

	public static final int C_REVENGE_REQUEST_HELP = 454;
	public static final int S_REVENGE_REQUEST_HELP = 1454;

	public static final int C_REVENGE_ACCEPT_HELP = 455;
	public static final int S_REVENGE_ACCEPT_HELP = 1455;

	// pvp
	public static final int C_MULTI_PVP_MATCHING_START = 540;
	public static final int S_MULTI_PVP_MATCHING_START = 1540;

	public static final int C_MULTI_PVP_MATCHING_CANCEL = 541;
	public static final int S_MULTI_PVP_MATCHING_CANCEL = 1541;

	public static final int S_MULTI_PVP_READY = 1542;

	public static final int C_MULTI_PVP_ACCEPT = 543;
	public static final int S_MULTI_PVP_ACCEPT = 1543;

	public static final int C_MULTI_PVP_REJECT = 544;
	public static final int S_MULTI_PVP_REJECT = 1544;

	public static final int S_MULTI_PVP_DESTROY = 1545;

	public static final int S_MULTI_PVP_ACCEPT_TIMEOUT = 1546;

	// FACEBOOK REQUEST
	public static final int C_FB_REQUEST_PROCESS = 560;

	public static final int C_FB_REQUEST_IDS = 561;
	public static final int S_FB_REQUEST_IDS = 1561;

	public static final int C_CLIENT_STATUS = 562;

	// HUD
	public static final int C_INTERCEPTION_ADD = 570;
	public static final int S_INTERCEPTION_ADD = 1570;

	public static final int C_INTERCEPTION_REMOVE = 571;
	public static final int S_INTERCEPTION_REMOVE = 1571;

	public static final int C_INTERCEPTION_LIST = 572;
	public static final int S_INTERCEPTION_LIST = 1572;

	public static final int C_HUD_HELPABLE_LIST = 573;
	public static final int S_HUD_HELPABLE_LIST = 1573;

	public static final int C_HUD_HELP = 574;
	public static final int S_HUD_HELP = 1574;

	/*
	 * GS - DBC
	 */

	public static final int RELOAD_CONFIG = 100;
	public static final int D_LOGIN = 2300;
	public static final int DC_LOGIN = 3300;

	public static final int D_UDATAREQ = 2301;
	public static final int DC_UDATAREQ = 3301;

	public static final int D_PORTDATAREQ = 2302;
	public static final int DC_PORTDATAREQ = 3302;

	public static final int D_UPDATE_FLEET_POSITION = 2304;
	public static final int DC_UPDATE_FLEET_POSITION = 3304;

	public static final int D_UPDATE_SHIP_ORDER_CAPTAIN = 2305;
	public static final int DC_UPDATE_SHIP_ORDER_CAPTAIN = 3305;

	public static final int D_CREATE_AVATAR = 2306;
	public static final int DC_CREATE_AVATAR = 3306;

	public static final int D_LOGOUT = 2307;
	public static final int DC_LOGOUT = 3307;

	public static final int D_RELOAD_CASH_ITEM = 2308;
	public static final int DC_RELOAD_CASH_ITEM = 3308;

	public static final int D_DELETE_SHIP = 2311;
	public static final int DC_DELETE_SHIP = 3311;

	public static final int D_UPDATETASKDATA = 2400;
	public static final int DC_UPDATETASKDATA = 3400;

	public static final int D_MISSION_COMPLETE = 2401;
	public static final int DC_MISSION_COMPLETE = 3401;

	public static final int D_MISSION_ADD = 2402;
	public static final int DC_MISSION_ADD = 3402;

	public static final int D_MISSION_DONE_WITHCASH = 2405;
	public static final int DC_MISSION_DONE_WITHCASH = 3405;

	public static final int D_MISSION_CANCEL = 2406;
	public static final int DC_MISSION_CANCEL = 3406;

	public static final int D_MISSION_RETRY = 2407;
	public static final int DC_MISSION_RETRY = 3407;

	public static final int D_MISSION_DELETE_PROMOTION = 2408;
	public static final int DC_MISSION_DELETE_PROMOTION = 3408;

	public static final int D_MISSION_NEW_UPDATE = 2409;
	public static final int DC_MISSION_NEW_UPDATE = 3409;

	public static final int D_BUY_INVENTORY_SLOT = 2460;
	public static final int DC_BUY_INVENTORY_SLOT = 3460;

	// guild
	public static final int D_GUILD_INVITE_CONFIRM = 2465;
	public static final int DC_GUILD_INVITE_CONFIRM = 3465;

	public static final int D_GUILD_UPDATE_POINT = 2466;
	public static final int DC_GUILD_UPDATE_POINT = 3466;

	public static final int D_GUILD_LOAD_GUILDS = 2467;
	public static final int DC_GUILD_LOAD_GUILDS = 3467;

	// public static final int D_GUILD_SEARCH = 2468;
	// public static final int DC_GUILD_SEARCH = 3468;

	public static final int D_GUILD_SET_PERMISSION = 2469;
	public static final int DC_GUILD_SET_PERMISSION = 3469;

	public static final int D_GUILD_CREATE = 2470;
	public static final int DC_GUILD_CREATE = 3470;

	public static final int D_GUILD_INFO = 2471;
	public static final int DC_GUILD_INFO = 3471;

	public static final int D_GUILD_JOIN_REQUEST = 2472;
	public static final int DC_GUILD_JOIN_REQUEST = 3472;

	// public static final int D_GUILD_LIST = 2473;
	// public static final int DC_GUILD_LIST = 3473;

	public static final int D_GUILD_JOIN_CONFIRM = 2474;
	public static final int DC_GUILD_JOIN_CONFIRM = 3474;

	public static final int D_GUILD_EXILE = 2475;
	public static final int DC_GUILD_EXILE = 3475;

	public static final int D_GUILD_MEMBER_INFO = 2476;
	public static final int DC_GUILD_MEMBER_INFO = 3476;

	public static final int D_GUILD_MAIL_SEND = 2477;
	public static final int DC_GUILD_MAIL_SEND = 3477;

	public static final int D_GUILD_DESTROY = 2478;
	public static final int DC_GUILD_DESTROY = 3478;

	public static final int D_GUILD_WRITE_NOTICE = 2479;
	public static final int DC_GUILD_WRITE_NOTICE = 3479;

	// ally
	public static final int D_ALLY_ADD = 2480;
	public static final int DC_ALLY_ADD = 3480;

	public static final int D_ALLY_LIST = 2481;
	public static final int DC_ALLY_LIST = 3481;

	public static final int D_ALLY_DELETE = 2482;
	public static final int DC_ALLY_DELETE = 3482;

	// public static final int D_ALLY_DAILY_SUPPORT = 2483;
	// public static final int DC_ALLY_DAILY_SUPPORT = 3483;

	// public static final int D_ALLY_DAILY_SUPPORT_COOL_TIME_OUT = 2484;
	// public static final int DC_ALLY_DAILY_SUPPORT_COOL_TIME_OUT = 3484;

	// public static final int D_ALLY_UNLOCK_SLOT = 2485;
	// public static final int DC_ALLY_UNLOCK_SLOT = 3485;

	public static final int D_ALLY_REQUEST = 2486;
	public static final int DC_ALLY_REQUEST = 3486;

	public static final int D_ALLY_CONFIRM = 2487;
	public static final int DC_ALLY_CONFIRM = 3487;

	public static final int D_NAME_TO_CSN = 2488;
	public static final int DC_NAME_TO_CSN = 3488;

	public static final int D_MAIL_SEND = 2490;
	public static final int DC_MAIL_SEND = 3490;

	public static final int D_MAIL_LIST = 2491;
	public static final int DC_MAIL_LIST = 3491;

	public static final int D_MAIL_READ = 2492;//
	public static final int DC_MAIL_READ = 3492;

	public static final int D_MAIL_DELETE = 2493;//
	public static final int DC_MAIL_DELETE = 3493;

	public static final int D_MAIL_ITEM_STORE = 2494;//
	public static final int DC_MAIL_ITEM_STORE = 3494;

	public static final int D_BUY_SHIP_REQ = 2503;//
	public static final int DC_BUY_SHIP_REQ = 3503;

	public static final int D_TRADE_REQ = 2505;//
	public static final int DC_TRADE_REQ = 3505;

	// enchant
	public static final int D_SHIP_ENCHANT = 2520;
	public static final int DC_SHIP_ENCHANT = 3520;

	// auction
	public static final int D_AUCTION_LIST = 2530;
	public static final int DC_AUCTION_LIST = 3530;
	public static final int D_AUCTION_REG_GOODS = 2531;
	public static final int DC_AUCTION_REG_GOODS = 3531;
	public static final int D_AUCTION_MY_GOODS = 2532;
	public static final int DC_AUCTION_MY_GOODS = 3532;
	public static final int D_AUCTION_BID = 2533;
	public static final int DC_AUCTION_BID = 3533;
	public static final int D_AUCTION_GET_GOODS_INFO = 2534;
	public static final int DC_AUCTION_GET_GOODS_INFO = 3534;
	public static final int D_AUCTION_MY_BID_LIST = 2535;
	public static final int DC_AUCTION_MY_BID_LIST = 3535;
	public static final int D_AUCTION_GAIN_ITEM_PAY = 2536;
	public static final int DC_AUCTION_GAIN_ITEM_PAY = 3536;

	// BATTLE
	public static final int D_BATTLE_END_REQ = 2601;//
	public static final int DC_BATTLE_END_REQ = 3601;
	public static final int D_GET_SHADOW_PVP_INFO = 2605;//
	public static final int DC_GET_SHADOW_PVP_INFO = 3605;

	public static final int D_GET_SHADOW_PVP_DATA = 2606;
	public static final int DC_GET_SHADOW_PVP_DATA = 3606;

	public static final int D_OBJECT_COOLTIME = 2701;//
	public static final int DC_OBJECT_COOLTIME = 3701;

	// ITEM
	public static final int D_ITEM_UPDATE = 2710;//
	public static final int DC_ITEM_UPDATE = 3710;

	public static final int D_ITEM_EQUIP = 2833;
	public static final int DC_ITEM_EQUIP = 3833;

	public static final int D_ITEM_RESET_STAT = 2837;
	public static final int DC_ITEM_RESET_STAT = 3837;

	public static final int D_CARE = 2840;
	public static final int DC_CARE = 3840;

	// npc
	public static final int D_GET_PUB_NPC_LIST_REQ = 2900;//
	public static final int DC_GET_PUB_NPC_LIST_REQ = 3900;

	public static final int D_STORE_PUB_NPC_LIST_REQ = 2901;//
	public static final int DC_STORE_PUB_NPC_LIST_REQ = 3901;

	public static final int D_HIRE_REQ = 2902;//
	public static final int DC_HIRE_REQ = 3902;

	public static final int D_FIRE_REQ = 2903;//
	public static final int DC_FIRE_REQ = 3903;

	public static final int D_UPDATE_SHIP_CAPTAIN = 2906;//
	public static final int DC_UPDATE_SHIP_CAPTAIN = 3906;

	public static final int D_BUY_LICENSE = 2907;//
	public static final int DC_BUY_LICENSE = 3907;

	public static final int D_ADMIN_CMD = 4300;// 작업대기
	public static final int DC_ADMIN_CMD = 5300;// 작업대기

	public static final int D_UPDATE_COMMANDER = 4301;//
	public static final int DC_UPDATE_COMMANDER = 5301;

	public static final int D_PTIME_UPDATE = 4302;//
	public static final int DC_PTIME_UPDATE = 5302;

	// skill
	public static final int D_USER_SKILL_UPDATE = 2510;//
	public static final int DC_USER_SKILL_UPDATE = 3510;

	public static final int D_USER_SKILL_REMOVE = 2511;//
	public static final int DC_USER_SKILL_REMOVE = 3511;

	// faction
	public static final int D_FACTION_UPDATE = 2320;//
	public static final int DC_FACTION_UPDATE = 3320;

	// building

	// public static final int D_BASE_DATAREQ = 2328;
	// public static final int DC_BASE_DATAREQ = 3328;

	public static final int D_WAREHOUSE_UPDATE = 2329;//
	public static final int DC_WAREHOUSE_UPDATE = 3329;

	public static final int D_BUILDING_UPDATE = 2330;//
	public static final int DC_BUILDING_UPDATE = 3330;

	public static final int D_PRODUCT_UPDATE = 2331;//
	public static final int DC_PRODUCT_UPDATE = 3331;

	public static final int D_DOCK_UPDATE = 2332;//
	public static final int DC_DOCK_UPDATE = 3332;

	public static final int D_UPDATE_RECIPE = 2334;//
	public static final int DC_UPDATE_RECIPE = 3334;

	public static final int D_BASEINFO_UPDATE = 2335;//
	public static final int DC_BASEINFO_UPDATE = 3335;

	public static final int D_BASEINFO_ADD = 2336;
	public static final int DC_BASEINFO_ADD = 3336;

	public static final int D_MOVE_ITEM = 2340; // 인벤 to 창고, 창고 to 인벤
	public static final int DC_MOVE_ITEM = 3340;

	// explore
	public static final int D_EXPLORE_ADD = 2341;//
	public static final int DC_EXPLORE_ADD = 3341;

	// base pvp command
	public static final int D_BASE_PVP_DATA_REQ = 2342;//
	public static final int DC_BASE_PVP_DATA_REQ = 3342;

	public static final int D_COLONY_PVP_START_REQ = 2343;//
	public static final int DC_COLONY_PVP_START_REQ = 3343;

	public static final int D_COLONY_PVP_END_REQ = 2344;//
	public static final int DC_COLONY_PVP_END_REQ = 3344;

	// user data update
	public static final int D_USERDATA_LEVEL_XP = 2350;//
	public static final int DC_USERDATA_LEVEL_XP = 3350;

	public static final int D_USERDATA_STAT = 2351;//
	public static final int DC_USERDATA_STAT = 3351;

	public static final int D_USERDATA_BONUSSTAT = 2352;//
	public static final int DC_USERDATA_BONUSSTAT = 3352;

	public static final int D_USERDATA_SHIPPCSLOT = 2353;//
	public static final int DC_USERDATA_SHIPPCSLOT = 3353;

	public static final int D_USERDATA_ATTACKRATING = 2354;//
	public static final int DC_USERDATA_ATTACKRATING = 3354;

	public static final int D_USERDATA_SKILLDATA = 2355;//
	public static final int DC_USERDATA_SKILLDATA = 3355;

	public static final int D_USERDATA_GOLD = 2356;//
	public static final int DC_USERDATA_GOLD = 3356;

	public static final int D_USERDATA_CASH = 2357;//
	public static final int DC_USERDATA_CASH = 3357;

	public static final int D_USERDATA_TUTORIAL = 2358;//
	public static final int DC_USERDATA_TUTORIAL = 3358;

	public static final int D_USERDATA_PVP_CATEGORY = 2359;
	public static final int DC_USERDATA_PVP_CATEGORY = 3359;
	// ---------------------------------------------------------

	// pc data update
	public static final int D_PCDATA_SKILLDATA = 2362;//
	public static final int DC_PCDATA_SKILLDATA = 3362;

	public static final int D_PCDATA_XP_LEVEL = 2363;//
	public static final int DC_PCDATA_XP_LEVEL = 3363;

	public static final int D_PCDATA_STAT = 2364;//
	public static final int DC_PCDATA_STAT = 3364;

	// //////////////
	public static final int D_COLONY_PVP_PLUNDER = 2365;
	public static final int DC_COLONY_PVP_PLUNDER = 3365;

	// / revenge
	public static final int D_REVENGE_UPDATE = 2366;
	public static final int DC_REVENGE_UPDATE = 3366;

	public static final int D_REVENGE_DELETE = 2367;
	public static final int DC_REVENGE_DELETE = 3367;

	public static final int D_COLONY_SCOUNT = 2369;
	public static final int DC_COLONY_SCOUNT = 3369;

	public static final int D_REVENGE_ADD = 2370;
	public static final int DC_REVENGE_ADD = 3370;

	public static final int D_REVENGE_DATA_REQ = 2371;
	public static final int DC_REVENGE_DATA_REQ = 3371;

	public static final int D_REVENGE_REQUEST_HELP = 2372;
	public static final int DC_REVENGE_REQUEST_HELP = 3372;

	public static final int D_REVENGE_ACCEPT_HELP = 2373;
	public static final int DC_REVENGE_ACCEPT_HELP = 3373;

	public static final int D_REVENGE_HELP_UPDATE = 2374;
	public static final int DC_REVENGE_HELP_UPDATE = 3374;

	// ----------------------------------------------------------
	public static final int D_BUILDING_ADD = 2380;
	public static final int DC_BUILDING_ADD = 3380;

	public static final int D_PRODUCT_ADD = 2381;
	public static final int DC_PRODUCT_ADD = 3381;

	// ---------------------------------------------------------
	public static final int D_ALLY_SUPPORT_TIME_UPDATE = 2382;
	public static final int DC_ALLY_SUPPORT_TIME_UPDATE = 3382;

	// HUD
	public static final int D_INTERCEPTION_ADD = 2383;
	public static final int DC_INTERCEPTION_ADD = 3383;

	public static final int D_INTERCEPTION_REMOVE = 2384;
	public static final int DC_INTERCEPTION_REMOVE = 3384;

	public static final int D_INTERCEPTION_LIST = 2385;
	public static final int DC_INTERCEPTION_LIST = 3385;

	public static final int D_HUD_HELPABLE_LIST = 2386;
	public static final int DC_HUD_HELPABLE_LIST = 3386;

	public static final int D_HUD_HELP_MODEL_ADD = 2387;
	public static final int DC_HUD_HELP_MODEL_ADD = 3387;

	public static final int D_HUD_HELP_MODEL_UPDATE = 2388;
	public static final int DC_HUD_HELP_MODEL_UPDATE = 3388;

	// -----------------------------------------------------------

	public static final int D_IS_EXISTING_USER_NAME = 2521;//
	public static final int DC_IS_EXISTING_USER_NAME = 3521;

	public static final int D_LOAD_DB_INDEX = 2522;//
	public static final int DC_LOAD_DB_INDEX = 3522;

	public static final int D_BLOCK_USER = 2523;//
	public static final int DC_BLOCK_USER = 3523;

	public static final int D_ADMIN_HONOR = 2524;
	public static final int DC_ADMIN_HONOR = 3524;

	// --------------------------------------------
	public static final int W_GS_PING = 8001;
	public static final int S_GS_PING = 9001;

	public static final int W_DC_PING = 8002;
	public static final int S_DC_PING = 9002;

	public static int commandConvertDBCachetoGameServer(int command) {
		int retv = command + 1000;
		return retv;
	}

}
