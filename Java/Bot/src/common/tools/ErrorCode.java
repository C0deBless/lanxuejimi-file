package common.tools;

public class ErrorCode {
	public static final int RES_SUCCESS = 1;
	public static final int RES_FAILED = -1;
	public static final int RES_CREATE_AVATAR = 2;
	public static final int NO_ERROR = 0;

	// client
	public static final int NOT_FOUND_COMMAND = 1;
	public static final int NOT_FOUND_SIGNATURE = 100;

	public static final int FAILED_NULL = -1;

	public static final int CODE_NO_ERROR = 0;
	public static final int CODE_REQUEST_BASE_IS_NULL = 1;
	public static final int CODE_NOT_MATCH_INSTANCE_OF_CLASS = 2;
	public static final int CODE_INVALID_USER_ID = 3;
	public static final int CODE_RESULT_SET_IS_NULL = 4;
	public static final int CODE_RESULT_SET_EXCEPTION = 5;
	public static final int CODE_FLEET_DATA_IS_NULL = 6;
	public static final int CODE_COMMANDER_DATA_IS_NULL = 7;
	public static final int CODE_FAILED_MAKE_SHIP_DATA = 8;
	public static final int CODE_SHIPS_DATA_IS_NULL = 9;
	public static final int CODE_CASH_DATA_IS_NULL = 10;
	public static final int CODE_NPC_DATA_IS_NULL = 11;
	public static final int CODE_USER_DATA_FIELD_IS_NULL = 12;
	public static final int CODE_FAILED_UPDATE_SHIP_HP_ORDER_CAPTIAN = 13;
	public static final int CODE_FAILED_CREATE_AVATAR_DUPLICATE_NAME = 14;
	public static final int CODE_FAILED_CREATE_AVATAR_DUPLICATE_COMMANDER = 15;
	public static final int CODE_FAILED_UPDATE_LOGOUT_TIME = 16;
	public static final int CODE_FAILED_NOT_FOUND_CASH_DATA = 17;
	public static final int CODE_FAILED_DELETE_SHIP = 18;
	public static final int CODE_FAILED_MISSION_TASK = 19;
	public static final int CODE_FAILED_REMOVE_MISSION_TASK = 20;
	public static final int CODE_FAILED_REMOVE_MISSION = 21;
	public static final int CODE_FAILED_UPDATE_MISSION = 22;
	public static final int CODE_FAILED_UNKNOW_MISSION_TYPE = 23;
	public static final int CODE_FAILED_ADD_MISSION_TO_DB = 24;
	public static final int CODE_INVALID_REQUEST_DATA = 25;
	public static final int CODE_RESPONSE_BASE_IS_NULL = 26;
	public static final int FAILED_MISSION_UPDATE_TASK_DONE_WITH_CASH = 27;
	public static final int NOT_FOUND_USER_CACHE = 28;
	
	public static final int SQL_EXECUTE_ERROR = 29;
	public static final int USER_BASE_INFO_NOT_FOUND = 30;
	public static final int PACKET_DESERIALIZE_EXCEPTION = 31;

	public static final int BUY_LIST_IS_NULL_FROM_REQUES_PARAMETER = 200;
	public static final int NOT_FOUND_PORT_ID_FROM_USER_FLEET = 202;
	public static final int BUY_NOT_FOUND_PORT_PRODUCT_FROM_PORT_CACHE = 204;
	public static final int BUY_NOT_FOUND_PORT_PRODUCT_ITEM_FROM_PORT_CACHE = 205;
	public static final int BUY_NOT_CARGO_TYPE = 207;
	public static final int NOT_FOUND_MY_SHIP_FROM_USER_SHIP = 208;
	public static final int BUY_NOT_FOUND_ID_FROM_REQUEST_DATA = 210;
	public static final int BUY_SNAPSHOT_IS_NULL_FROM_USER_DATA = 211;
	public static final int BUY_SUPPLY_IS_GREATER_THAN_DEMAND = 231;
	public static final int SUPPLY_AND_DEMAND_ARE_EQUAL = 233;
	public static final int GOLD_IS_DIFFERENT_CALCULATION = 240;
	public static final int GOLD_IS_ZERO_CALCULATION = 241;
	public static final int BUY_DECK_SIZE_IS_FULL = 250;
	public static final int BUY_COUNT_IS_GREATER_THAN_DECK_SIZE = 251;

	public static final int FAILED_BUY_SHIP_DB_INSERT = 252;
	public static final int FAILED_BUY_SHIP_CREATE_NEW_SHIP = 253;
	public static final int FAILED_BUY_SHIP_USER_SHIPS_NULL = 254;
	public static final int FAILED_BUY_COUNT_ZERO = 255;
	public static final int FAILED_BUY_COUNT_OVER = 256;
	public static final int FAILED_BUY_SHIP_NOT_MATCH_GOLD = 257;

	public static final int FAILED_SELL_COUNT = 300;
	public static final int FAILED_SELL_GOLD = 301;
	public static final int FAILED_SELL_SUM_GOLD = 302;
	public static final int FAILED_SELL_ALL_SUM_GOLD = 303;
	public static final int FAILED_SELL_ALL_PART_EMPTY = 304;
	public static final int FAILED_SELL_SUPPLY_EQUAL_DEMAND = 305;
	public static final int FAILED_SELL_SUPPLY_PLUS_COUNT_GREAT = 306;
	public static final int FAILED_SELL_NOT_FOUND_PORT_ID = 307;
	public static final int FAILED_SELL_LIST_NULL = 308;
	public static final int FAILED_SELL_NOT_FOUND_MY_SHIP = 309;
	public static final int FAILED_SELL_NOT_FOUND_PORT_PRODUCT_ITEM = 310;
	public static final int FAILED_SELL_COUNT_ZERO = 311;
	public static final int FAILED_SELL_COUNT_SHIP_PART_COUNT_GREAT = 312;
	public static final int FAILED_SELL_NOT_FOUND_PART_FROM_MYSHIP = 313;
	public static final int FAILED_SELL_AND_BUY_LIST_NULL = 314;
	public static final int FAILED_TRADE_SUM_GOLD = 320;
	public static final int FAILED_TRADE_RESULT_GOLD_MORE_WANT = 321;
	public static final int FAILED_TRADE_RESULT_COUNT = 322;
	public static final int FAILED_TRADE_NOT_FOUND_GUID = 323;
	public static final int FAILED_TRADE_NOT_MATCH_COUNT = 324;

	public static final int FAILED_NOT_FOUND_BUY_SELL_ITEM = 350;

	public static final int FAILED_UPDATE_D_SELL_REQ = 400;
	public static final int MARKET_PRICE_IS_NULL = 499;
	public static final int REQUEST_DATA_IS_NULL = 500;
	public static final int USER_DATA_IS_NULL_FROM_USER_CACHE = 501;
	public static final int SHIP_ID_IS_NULL = 502;
	public static final int PORT_ID_IS_NULL = 503;
	public static final int ITEM_SNAPSHOT_IS_NULL = 504;
	public static final int CURRENT_SHIP_IS_NULL = 505;
	public static final int PORT_PRODUCTS_IS_NULL = 506;
	public static final int FLEET_IS_NULL = 507;
	public static final int FLEET_POS_IS_NULL = 508;
	public static final int EVENT_ID_IS_NULL_FROM_SESSION = 509;
	public static final int ENEMY_DATA_IS_NULL = 510;
	public static final int ENEMY_ALIGN_IS_NULL = 511;
	public static final int ENEMY_SHIP_LIST_IS_NULL = 512;
	public static final int USER_FLEET_POS_IS_NULL = 513;
	public static final int USER_FLEET_IS_NULL = 514;
	public static final int USER_SHIP_COUNT_FULL = 515;
	public static final int SHIP_PARTS_IS_NULL = 600;
	public static final int TRADE_SHIP_IS_NULL = 601;
	public static final int BATTLE_END_BATTLE_DATA_IS_NULL = 602;
	public static final int BATTLE_END_ENEMY_DATA_IS_NULL = 603;
	public static final int BATTLE_END_OBJECT_SHIP_LIST_IS_NULL = 604;
	public static final int BATTLE_END_SHIP_LIST_DATA_IS_NULL = 605;
	public static final int BATTLE_TOWARD_IS_NULL = 606;
	public static final int USER_SHIP_LIST_IS_NULL = 607;
	public static final int BATTLE_END_WIN_IS_NULL = 608;
	public static final int BATTLE_END_SHIP_LIST_IS_NULL = 609;
	public static final int BATTLE_END_PORT_DATA_IS_NULL = 610;
	public static final int ENEMY_SHIP_OBJECT_LIST_IS_NULL = 611;
	public static final int NOT_FOUND_PUB_OBJECT_ID = 612;
	public static final int PUB_HIRE_NOT_FOUND_ID = 613;
	public static final int NOT_CONTAINS_KIEY_PUB_NPC_CACHE = 614;
	public static final int EMPTY_PUB_NPC = 615;
	public static final int NOT_MATCH_USER_PORT_ID_PUB_NPC_PORT_ID = 616;
	public static final int NOT_MATCH_USER_ID_PUB_NPC_ID = 617;
	public static final int NOT_CONTAINS_PUB_NPC_ID = 618;
	public static final int NPC_XML_DATA_IS_NULL = 619;
	public static final int MORE_GOLD_IS_NEEDED = 620;
	public static final int FAILED_REQUEST_PC_ID = 621;
	public static final int FAILED_ADD_USER_PC = 622;
	public static final int MAX_HIRE_COUNT = 623;
	public static final int FIRE_NOT_FOUND_ID = 624;
	public static final int MATCH_PC_ID_CAPTAIN = 625;
	public static final int MATCH_PC_ID_BOSUN = 626;
	public static final int FAILED_REMOVE_USER_PC = 627;
	public static final int FAILED_REMOVE_USER_PC_FROM_DB = 628;
	public static final int FAILED_PUB_TALK_GOLD_UPDATE = 629;
	public static final int REQUEST_PARAMETER_IS_NULL = 630;
	public static final int COMMISSION_CAPTAIN = 631;
	public static final int COMMISSION_BOSUN = 632;
	public static final int SHIP_LIST_IS_NULL = 633;
	public static final int USER_SHIP_IS_NULL = 634;
	public static final int SHIP_COMMISSIONED_CAPTAIN = 635;
	public static final int SHIP_COMMISSIONED_CAPTAIN_USER = 636;
	public static final int SHIP_COMMISSIONED_BOSUN = 637;
	public static final int FAILED_SHIP_COMMISSION_UPDATE = 638;
	public static final int NOT_MATCH_CAPTIAIN_ID = 639;
	public static final int NOT_MATCH_BOSUN_ID = 640;
	public static final int NOT_USER_COMMISSION_BOSUN = 641;
	public static final int USER_AVATAR_CAN_NOT_DISMISS = 642;
	public static final int NOT_FIRE_USER_AVARTA = 643;
	public static final int NOT_FOUND_USER_PC_LIST_PC_ID = 644;
	public static final int NOT_FOUND_USER_SHIP = 645;
	public static final int NOT_FOUND_USER_OBJECT = 646;
	public static final int FAILED_CHANGE_SHIP_STATUS = 647;
	public static final int FIRE_IMPOSSILBE_PC = 648;
	public static final int USER_AVATAR_NAME_IS_NOT_VALID = 649;
	public static final int USER_PORTRAIT_ID_IS_NOT_VALID = 650;
	public static final int FAILED_REQUEST_DB_RETURN_IS_NULL = 651;
	public static final int DUPLICATE_NAME = 652;
	public static final int GOLD_IS_NULL = 653;
	public static final int MULTILOGINERROR = 654;
	public static final int ILLEGAL_MISSIONID = 655;
	public static final int ILLEGAL_FACTION_MEET_REQUEST = 656;
	public static final int ILLEGAL_PARAMETER = 657;
	public static final int DBCACHESERVER_TIMEOUT = 658;
	public static final int NAGETIVE_GOLD = 659;
	public static final int MAIL_INIT_MAIL_SEND_PARAMETER = 660;
	public static final int MAIL_MORE_MONEY_IS_NEEDED = 661;
	public static final int MAIL_ERROR_ITEM_UNKNOWN_GUID = 662;
	public static final int MAIL_ERROR_ITEM_NOT_TYPE_P = 663;
	public static final int MAIL_ERROR_NOT_FOUND_ITEM = 664;
	public static final int MAIL_ERROR_ITEM_COUNT_OVER= 665;
	public static final int MAIL_NOT_MATCH_RESPONSE_TYPE = 666;
	public static final int MAIL_DC_MAIL_SEND_PARAMETER = 667;
	public static final int MAIL_TO_CLIENT_NOT_FOUND_USER = 668;
	public static final int MAIL_INVALID_C_MAIL_DELETE = 669;
	public static final int MAIL_NOT_FOUND_MAIL = 670;
	public static final int MAIL_INVALID_C_MAIL_LIST = 671;
	public static final int MAIL_INVALID_C_MAIL_READ = 672;
	public static final int MAIL_INVALID_C_MAIL_ITEM_STORE = 673;
	public static final int INVEN_INVEN_SLOT_FULL = 674;
	public static final int GUILD_GUILD_LIST_PARAMETER = 675;
	public static final int GUILD_INVALID_C_GUILD_CREATE = 676;
	public static final int GUILD_INVALID_C_GUILD_INFO = 677;
	public static final int GUILD_NOT_INSTANCE_OF_RESGUILDINFO = 678;
	public static final int GUILD_NOT_FOUND_GUILD_DATA_FROM_RESGUILDINFO = 679;
	public static final int GUILD_INVALID_C_GUILD_JOIN_REQUEST = 680;
	public static final int GUILD_NOT_INSTANCE_OF_RESGUILDCREATE = 681;
	public static final int GUILD_NOT_INSTANCE_OF_RESGUILDJOINCONFIRM = 682;
	public static final int NOT_FOUND_GUILD = 683;
	public static final int ILLEGAL_CHATTING = 684;
	
	public static final int C_SHIP_ENCHANT_PACKET_DESERIALIZE_EXCEPTION = 685;
	public static final int C_SHIP_ENCHANT_REQCSHIPENCHANT_IS_NULL = 686;
	public static final int C_SHIP_ENCHANT_NOT_FOUND_TARGET_SHIP = 687;
	public static final int C_SHIP_ENCHANT_NOT_FOUND_MATERIAL_SHIP = 688;
	public static final int C_SHIP_ENCHANT_NOT_MATCH_SHIP_LV = 689;
	public static final int C_SHIP_ENCHANT_NOT_FOUND_MATERIAL_ITEM = 690;
	public static final int C_SHIP_ENCHANT_NOT_MATCH_MATERIAL_ITEM_TYPE = 691;
	public static final int C_SHIP_ENCHANT_NOT_FOUND_ENCHANT_DATA = 692;
	public static final int C_SHIP_ENCHANT_NEEDED_MORE_GOLD = 693;
	public static final int C_SHIP_ENCHANT_NOT_FOUND_MATERIAL_ITEM_COUNT_OVER = 694;
	public static final int C_SHIP_ENCHANT_ERROR_BASE_EXP = 695;
	public static final int C_SHIP_ENCHANT_NOT_FOUND_RECEIPTS_ITEM = 696;
	public static final int C_SHIP_ENCHANT_NOT_FOUND_RECEIPTS_SHIP = 697;
	public static final int C_SHIP_ENCHANT_NOT_FOUND_MATERIAL_SHIP_TYPE = 698;
	public static final int C_SHIP_ENCHANT_ERROR_TARGET_SHIP_FLAF_SHIP_INFO = 699;

	public static final int C_ITEM_CACHA_USE_PACKET_DESERIALIZE_EXCEPTION = 700;
	public static final int C_ITEM_CACHA_USE_REQCITEMCACHAUSE_IS_NULL = 701;
	public static final int C_ITEM_CACHA_USE_NOT_FOUND_ITEM = 702;
	public static final int C_ITEM_CACHA_USE_NOT_MATCH_ITEM_POP_TYPE= 703;
	public static final int C_ITEM_CACHA_USE_NOT_FOUND_DROP_TABLE = 704;
	public static final int C_ITEM_CACHA_USE_DROP_REWARD_IS_NULL = 705;
	public static final int C_ITEM_CACHA_USE_DROP_REWARD_REWARD_UPDATE_FAILED = 706;
	public static final int C_ITEM_CACHA_USE_NOT_FOUND_CACHA_ITEM_ID = 707;
	
	public static final int C_ITEM_ADD_PACKET_DESERIALIZE_EXCEPTION = 708;
	public static final int C_ITEM_ADD_REQCITEMADD_IS_NULL = 709;
	public static final int C_ITEM_ADD_REQCITEMADD_INVALID = 710;
	public static final int C_ITEM_ADD_NOT_FOUND_ITEM = 711;
	
	public static final int C_CARE_EXCEPTION = 712;
	
	public static final int C_AUCTION_LIST_PACKET_DESERIALIZE_EXCEPTION = 713;
	public static final int C_AUCTION_LIST_REQCAUCTIONLIST_IS_NULL = 714;
	public static final int C_AUCTION_LIST_INVALID_REQCAUCTIONLIST = 715;	
	public static final int C_AUCTION_REG_GOODS_PACKET_DESERIALIZE_EXCEPTION = 716;
	public static final int C_AUCTION_REG_GOODS_REQCAUCTIONLIST_IS_NULL = 717;
	public static final int C_AUCTION_REG_GOODS_INVALID_REQCAUCTIONLIST = 718;
	public static final int C_AUCTION_REG_GOODS_NOT_FOUND_ITEM = 719;
	public static final int C_AUCTION_REG_GOODS_NOT_MATCH_ITEM_TYPE = 720;
	public static final int C_AUCTION_REG_GOODS_NEED_MORE_CASH = 721;
	public static final int C_AUCTION_REG_GOODS_NEED_MORE_GOLD = 722;
	public static final int C_AUCTION_REG_GOODS_UNKNOWN_PAY_TYPE = 723;
	public static final int C_AUCTION_REG_GOODS_NEED_MORE_GOLD_CASH = 724;
	public static final int C_AUCTION_REG_GOODS_NEED_MORE_ITEM_COUNT = 725;
	public static final int C_AUCTION_MY_GOODS_PACKET_DESERIALIZE_EXCEPTION = 726;
	public static final int C_AUCTION_MY_GOODS_REQCAUCTIONMYGOODS_IS_NULL = 727;
	public static final int DC_AUCTION_REG_GOODS_FAILED_PACKET_DESERIALIZE = 728;
	public static final int DC_AUCTION_REG_GOODS_NOT_INSTANCE_OF_RESAUCTIONMYGOODS = 729;
	public static final int C_AUCTION_BID_PACKET_DESERIALIZE_EXCEPTION = 730;
	public static final int C_AUCTION_BID_REQCAUCTIONBID_IS_NULL = 731;
	public static final int C_AUCTION_BID_INVALID_REQCAUCTIONBID = 732;
	public static final int C_AUCTION_REG_GOODS_REQCAUCTIONREGGOODS_IS_NULL = 733;
	public static final int C_AUCTION_REG_GOODS_INVALID_REQCAUCTIONREGGOODS = 734;
	public static final int C_AUCTION_BID_NOT_FOUND_GOODS = 735;
	public static final int C_AUCTION_BID_NEED_MORE_PAY = 736;
	public static final int C_AUCTION_MY_BID_LIST_PACKET_DESERIALIZE_EXCEPTION = 737;
	public static final int C_AUCTION_MY_BID_LIST_REQCAUCTIONLIST_IS_NULL = 738;
	public static final int C_AUCTION_MY_BID_LIST_INVALID_REQCMYBIDLIST = 739;
	public static final int C_AUCTION_GAIN_ITEM_PAY_PACKET_DESERIALIZE_EXCEPTION = 740;
	public static final int C_AUCTION_GAIN_ITEM_PAY_REQCAUCTIONGETITEMPAY_IS_NULL = 741;
	public static final int C_AUCTION_GAIN_ITEM_PAY_INVALID_REQCAUCTIONGETITEMPAY = 742;
	public static final int C_AUCTION_GAIN_ITEM_PAY_NOT_FOUND_BID_INFO = 743;
	public static final int C_AUCTION_GAIN_ITEM_PAY_PREVIOUS_GET_ITEM = 744;
	public static final int C_AUCTION_GAIN_ITEM_PAY_NOT_MATCH_TOP_BID_CSN = 745;
	public static final int C_AUCTION_GAIN_ITEM_PAY_NOT_FOUND_MY_REG_GOODS = 746;
	public static final int C_AUCTION_GAIN_ITEM_PAY_UNKNOWN_PAY_TYPE = 747;
	public static final int C_AUCTION_GAIN_ITEM_PAY_MATCH_TOP_BID_CSN = 748;
	public static final int C_AUCTION_GAIN_ITEM_PAY_TIME_ERROR = 749;
	public static final int C_AUCTION_REG_GOODS_SLOT_ERROR = 750;
	public static final int C_AUCTION_BID_NOT_FOUND_GOODS_SRL = 751;
	public static final int C_AUCTION_BID_OVER_MAX_GOLDL = 752;
	public static final int C_AUCTION_BID_PRICE_OVER = 753;
	public static final int C_AUCTION_GAIN_ITEM_PAY_OVER_MAX_GOLD = 754;
	public static final int C_AUCTION_GAIN_ITEM_PAY_OVER_MAX_CASH = 755;
	
	
	
	
	
	
	
	// DBC
	public static final int OFFSET = 10000;
	public static final int DB_NOT_FOUND_COMMAND = OFFSET + 1;
	public static final int DB_NOT_FOUND_SIGNATURE = OFFSET + 100;
	public static final int NOT_FOUND_USER_ID = OFFSET + 101;
	public static final int NULL_RESULT_SET = OFFSET + 102;
	public static final int USER_ID_IS_NULL = OFFSET + 103;
	public static final int DB_PORT_ID_IS_NULL = OFFSET + 104;
	public static final int PC_ID_IS_NULL = OFFSET + 105;
	public static final int BOUNS_PC_SLOT_IS_NULL = OFFSET + 106;
	public static final int CARGOS_IS_NULL = OFFSET + 107;
	public static final int NOT_FOUND_COMMANDER = OFFSET + 108;
	public static final int NO_AVATAR = OFFSET + 109;

	public static final int FAILED_UPDATE_D_BUY_NULL = OFFSET + 200;
	public static final int FAILED_UPDATE_D_BUY_REQ = OFFSET + 201;
	public static final int FAILED_UPDATE_D_BUY_NOT_FOUND_PORT_ID = OFFSET + 202;
	public static final int FAILED_UPDATE_D_BUY_NOT_FOUND_PORT = OFFSET + 203;
	public static final int FAILED_UPDATE_D_BUY_NOT_FOUND_PORT_PRODUCT = OFFSET + 204;
	public static final int FAILED_UPDATE_D_BUY_SUPPLY = OFFSET + 205;
	public static final int FAILED_UPDATE_D_BUY_SUPPLY_LIST_NULL = OFFSET + 206;

	public static final int FAILED_INSERT_SHIP = OFFSET + 1000;
	public static final int FAILED_INSERT_MY_SHIP = OFFSET + 1001;
	public static final int FAILED_INSERT_COMMANDER = OFFSET + 1003;

	public static final int DB_FAILED_UPDATE_D_SELL_REQ = OFFSET + 2000;
	public static final int FAILED_UPDATE_D_SELL_ALL_GOLD_REQ = OFFSET + 2001;
	public static final int FAILED_UPDATE_D_SELL_LIST_IS_NULL = OFFSET + 2002;

	public static final int FAILED_UPDATE_D_TRADE_ITEM_IS_NULL = OFFSET + 2003;

	public static final int FAILED_TRADE = OFFSET + 499;
	public static final int NULL_IS_VALUE = OFFSET + 500;
	public static final int FAILED_UPDATE_GOLD = OFFSET + 501;
	public static final int FAILED_DELETE_ITEM = OFFSET + 502;
	public static final int FAILED_UPDATE_CARGO = OFFSET + 503;
	public static final int FAILED_INSERT_CARGO = OFFSET + 504;
	public static final int FAILED_UPDATE_XP = OFFSET + 505;
	public static final int DB_REQUEST_DATA_IS_NULL = OFFSET + 506;
	public static final int FAILED_UPDATE_FLEET_POS = OFFSET + 507;
	public static final int FAILED_UPDATE_ENERGY = OFFSET + 508;
	public static final int FAILED_UPDATE_SHIP_HP = OFFSET + 509;
	public static final int PUB_NPC_LIST_IS_NULL = OFFSET + 510;
	public static final int FAILED_INSERT_PUB_NPC_LIST = OFFSET + 511;
	public static final int DB_GOLD_IS_NULL = OFFSET + 512;
	public static final int FAILED_INSERT_PC = OFFSET + 513;
	public static final int FAILED_DELETE_PC = OFFSET + 514;
	public static final int FAILED_UPDATE_SHIP_BOSUN = OFFSET + 515;
	public static final int DB_SHIP_ID_IS_NULL = OFFSET + 516;
	public static final long captain_IS_NULL = OFFSET + 517;
	public static final int FALG_IS_NULL = OFFSET + 518;
	public static final int FAILED_UPDATE_COMMISSION_SHIP_PC = OFFSET + 519;
	public static final int FAILED_UPDATE_DISSMISSAL_SHIP_PC = OFFSET + 520;
	public static final int FAILED_UPDATE_MYSHIP_COMMISSION_OR_DISSMISSAL = OFFSET + 521;
	public static final int FAILED_UPDATE_SHIP_COMMISSION_OR_DISSMISSAL = OFFSET + 522;
	public static final int NOT_FOUND_SHIP_REQUEST_PARAMERTER = OFFSET + 523;
	public static final int FAILED_UPDATE_INIT_SHIP = OFFSET + 524;
	public static final int FAILED_UPDATE_PC_SLOT = OFFSET + 525;
	public static final int NAME_IS_NULL = OFFSET + 526;
	public static final int PORTRAIT_ID_IS_NULL = OFFSET + 527;
	public static final int DB_DUPLICATE_NAME = OFFSET + 528;
	public static final int FAILED_CREATE_AVATAR = OFFSET + 529;
	public static final int FAILED_CREATE_PC = OFFSET + 530; 

	
	public static final int DB_ERROR_MAIL_SEND_FAILED_NOT_FOUND_GUILD_NAME= OFFSET + 3465;
	public static final int DB_ERROR_MAIL_SEND_FAILED_INSERT_GUILD_INVITE = OFFSET + 3466;
	public static final int DB_ERROR_MAIL_SEND_FAILED_INSERT = OFFSET + 3467;
	public static final int DB_ERROR_MAIL_SEND_FAILED_UPDATE_SEND_USER = OFFSET + 3468;
	public static final int DB_ERROR_UNKNOW_ALLOW_MODE_VALUE = OFFSET + 3469;
	public static final int DB_ERROR_UNKNOW_ALLOW_VALUE = OFFSET + 3470;
	public static final int DB_ERROR_ALLY_FAILED_CONFIRM_DENY_MAIL_DELETE = OFFSET + 3471;
	public static final int DB_ERROR_ALLY_FAILED_INSERT_CONFIRM_MAIL = OFFSET + 3472;
	public static final int DB_ERROR_ALLY_CONFIRM_MAKE_ALLY_DATA = OFFSET + 3473;
	public static final int DB_ERROR_ALLY_CONFIRM_INSERT_FAILED = OFFSET + 3474;
	public static final int DB_ERROR_ALLY_CONFIRM_NOT_FOUND_USER_INFO = OFFSET + 3475;
	public static final int DB_ERROR_MAIL_SEND_INVALID_TO_USER_NAME = OFFSET + 3476;
	public static final int DB_ERROR_ALLY_FAILED_INSERT_REQUEST_MAIL = OFFSET + 3477;
	public static final int DB_ERROR_ALLY_FAILED_NOT_FOUND_REQUEST_USER = OFFSET + 3478;
	public static final int DB_ERROR_ALLY_FAILED_NOT_FOUND_TARGET_USER = OFFSET + 3479;
	public static final int DB_ERROR_ALLY_FAILED_REQUEST_INVALID_NAME = OFFSET + 3480;
	public static final int DB_ERROR_ALLY_FAILED_UPDATE_CASH = OFFSET + 3481;
	public static final int DB_ERROR_ALLY_FAILED_UPDATE_DAILY_SUPPORT = OFFSET + 3483;
	public static final int DB_ERROR_ALLY_FAILED_DELETE = OFFSET + 3483;
	public static final int DB_ERROR_ALLY_NOT_FOUND_DEFAULT_ALLY_DATA = OFFSET + 3484;
	public static final int DB_ERROR_ALLY_NOT_FOUND_ALLY_DATA = OFFSET + 3485;
	public static final int DB_ERROR_ALLY_FAILED_INSERT = OFFSET + 3486;
	public static final int DB_ERROR_ALLY_NOT_FOUND_ALLY_NAME_TO_USER_ID = OFFSET + 3487;
	public static final int DB_MAIL_NOT_FOUND_TO_USER = OFFSET + 3490;
	public static final int DB_ETC = OFFSET + 3490;

	public static final int FAILED_UPADTE_ALLY_BATTLE_TIME = OFFSET + 3491;
	public static final int FAILED_UPADTE_TRADE_COUNT = OFFSET + 3492;

	public static final int DB_ERROR_GUILD_CREATE_DUPLICATE_NAME = OFFSET + 3493;
	public static final int DB_ERROR_GUILD_CREATE_RESULT_PREV_GUILD_JOIN = OFFSET + 3494;

	public static final int DB_ERROR_GUILD_INVITE_CONFIRM_RESULT_ERROR = OFFSET + 3495; // 가입초대
																						// 거절
																						// 정상
																						// 처리
	public static final int DB_ERROR_GUILD_INVITE_CONFIRM_RESULT_DENY = OFFSET + 3496; // 가입초대
																						// 거절
																						// 정상
																						// 처리
	public static final int DB_ERROR_GUILD_INVITE_CONFIRM_RESULT_ALLOW = OFFSET + 3497; // 가입초대
																						// 수락
																						// 정상
																						// 처이
	public static final int DB_ERROR_GUILD_INVITE_CONFIRM_RESULT_MEMBER_FULL = OFFSET + 3498; // 해당
																								// 길드는
																								// 더이상
																								// 길드원을
																								// 받을수
																								// 없음
	public static final int DB_ERROR_GUILD_INVITE_CONFIRM_RESULT_JOIN_A_GUILD = OFFSET + 3499; // 이미
																								// 다른
																								// 길드에
																								// 가입된
																								// 상태
	public static final int DB_ERROR_GUILD_INVITE_CONFIRM_RESULT_NOT_FOUND_GUILD = OFFSET + 3500; // 존재하지
																									// 않는
																									// 길드
	public static final int DB_ERROR_GUILD_INVITE_CONFIRM_RESULT_HAVE_JOINED_THE_GUILD = OFFSET + 3501; // 이미
																										// 길드원으로
																										// 등록되어
																										// 있음
	public static final int DB_ERROR_GUILD_INVITE_CONFIRM_RESULT_FAILED_INSERT = OFFSET + 3502; // DB
																								// 멤버
																								// 추가
																								// 실패
	public static final int DB_ERROR_GUILD_INVITE_CONFIRM_RESULT_FAILED_UPDATE_COMMANDER = OFFSET + 3502; // DB
																											// commander
																											// guildSRL
																											// 업데이트
																											// 실패

	public static final int DB_ERROR_ALLY_DUPLICATE_ALLY = OFFSET + 3503;

	// 길드를 찾을수 없음
	public static final int DB_ERROR_GUILD_JOIN_NOT_FOUND_GUILD = OFFSET + 3504; 
	
	// 같은 길드에 중복 가입 신청
	public static final int DB_ERROR_GUILD_JOIN_DUPLICATE_JOIN_REQUEST = OFFSET + 3505; 
	
	// 다른 길드에 가입되어 있음
	public static final int DB_ERROR_GUILD_JOIN_OTHER_GUILD_MEMEBR = OFFSET + 3506; 
	
	// 이미 해당 길드에 가입되어 있음
	public static final int DB_ERROR_GUILD_JOIN_GUILD_MEMEMBER = OFFSET + 3507; 
	
	// 길드 멤버 FULL
	public static final int DB_ERROR_GUILD_JOIN_GUILD_MEMEMBER_FULL = OFFSET + 3508; 
	
	// 길드	가입 멤버아님
	public static final int DB_ERROR_GUILD_JOIN_NOT_APPLY = OFFSET + 3509;
	
	// 길드 가입수락 실패
	public static final int DB_ERROR_GUILD_JOIN_ALLOW_INSERT_FAILED = OFFSET + 3510; 
	
	// 가입 유저 업데이트 실패
	public static final int DB_ERROR_GUILD_JOIN_UPDATE_TARGET_USER_FAILED = OFFSET + 3511; 
	
	// 길드 멤버 데이타 생설 실패
	public static final int DB_ERROR_GUILD_JOIN_MAKE_MEMBER_FAILED = OFFSET + 3512; 

	// 길드초대 결과 NULL
	public static final int DB_ERROR_GUILD_INVITE_CONFIRM_RESULT_IS_NULL = OFFSET + 3513;
	public static final int DB_ERROR_GUILD_UPDATE_POINT_NOT_FOUND_GUILD = OFFSET + 3514;
	public static final int DB_ERROR_GUILD_UPDATE_POINT_FAILED = OFFSET + 3515;
	
	// 길드를 찾을수 없음
	public static final int DB_ERROR_GUILD_NOT_FOUND_GUILD = OFFSET + 3516; 
	public static final int DB_ERROR_GUILD_FAILED_UPDATE_PERMISSION = OFFSET + 3517;
	public static final int DB_ERROR_GUILD_CREATE_INVALID_NAME = OFFSET + 3518;
	public static final int DB_ERROR_GUILD_FAILED_EXILE = OFFSET + 3519;
	public static final int DB_ERROR_GUILD_NOT_FOUND_MEMBER = OFFSET + 3520;
	public static final int DB_ERROR_GUILD_FAILED_SEND_MAIL = OFFSET + 3521;
	public static final int DB_ERROR_GUILD_LOAD_GUILDS = OFFSET + 3522;
	public static final int DB_ERROR_GUILD_PERMISSION = OFFSET + 3523;
	public static final int DB_ERROR_GUILD_FAILED_DESTROY = OFFSET + 3524;
	public static final int DB_ERROR_GUILD_NOTICE_URLENCODING = OFFSET + 3525;
	public static final int DB_ERROR_GUILD_FAILED_UPDATE_NOTICE = OFFSET + 3526;

	public static final int DB_ERROR_UPDATE_LICENSE_COUNT = OFFSET + 3527;

	public static final int NOT_FOUND_MAIL_ITEM = OFFSET + 3528;
	public static final int MAIL_ITEM_STORE_ERROR = OFFSET + 3529;
	public static final int MAIL_READ_ERROR = OFFSET + 3530;
	public static final int MAIL_LIST_READ_ERROR = OFFSET + 3530;
	
	public static final int DB_ERROR_FAILED_MOVE_ITEM_PORT_TO_INVEN_UPDATE_INVEN = OFFSET + 3531;
	public static final int DB_ERROR_FAILED_MOVE_ITEM_INVEN_TO_PORT_UPDATE_INVEN = OFFSET + 3532;
	
	public static final int DB_ERROR_FAILED_UPDATE_SHIP_PART = OFFSET + 3533;
	public static final int DB_ERROR_FAILED_UPDATE_SHIP_PART_TO_INVENTORY = OFFSET + 3534;
	
	public static final int DB_ERROR_FAILED_UPDATE_SET_GOLD_OR_CASH = OFFSET + 3535;
	public static final int DB_ERROR_FAILED_UPDATE_INVENTORY_SLOT = OFFSET + 3536;
	
	public static final int DB_ERROR_FAILED_RESET_STAT_UPDATE_STAT = OFFSET + 3537;
	public static final int DB_ERROR_FAILED_RESET_STAT_DELETE_SKILL = OFFSET + 3538;
	
	public static final int DB_ERROR_FAILED_NAME_TO_CSN = OFFSET + 3539;
	
	
	public static final int DB_ERROR_FAILED_D_SHIP_ENCHANT_UPDATE_SHIP_ENCHANT = OFFSET + 3540;
	public static final int DB_ERROR_FAILED_D_SHIP_ENCHANT_DELETE_MATERIAL_SHIP = OFFSET + 3541;
	public static final int DB_ERROR_FAILED_D_SHIP_ENCHANT_UPDATE_ITEM = OFFSET + 3542;
	public static final int DB_ERROR_FAILED_D_SHIP_ENCHANT_UPDATE_EXCEPTION = OFFSET + 3543;
	
	public static final int DB_ERROR_FAILED_D_AUCTION_GET_GOODS_NOT_FOUND_GOODS = OFFSET + 3544;
	public static final int DB_ERROR_FAILED_D_AUCTION_BID_GOODS_NOT_FOUND_GOODS = OFFSET + 3545;
	public static final int DB_ERROR_FAILED_D_AUCTION_BID_UNKNOWN_STATUS = OFFSET + 3546;
	public static final int DB_ERROR_FAILED_D_AUCTION_BID_SOLD_OUT = OFFSET + 3547;
	public static final int DB_ERROR_FAILED_D_AUCTION_FAILED = OFFSET + 3548;
	public static final int DB_ERROR_FAILED_D_AUCTION_FAILED_UPDATE_PAY = OFFSET + 3549;
	public static final int DB_ERROR_FAILED_D_AUCTION_FAILED_UPDATE_BID = OFFSET + 3550;
	public static final int DB_ERROR_FAILED_D_AUCTION_FAILED_UPDATE_GOODS_STATUS = OFFSET + 3551;
	public static final int DB_ERROR_FAILED_D_AUCTION_MY_BID_LIST_IS_NULL = OFFSET + 3552;
	
	public static final int DB_ERROR_FAILED_D_AUCTION_GAIN_ITEM_PAY_UPDATE_ITEM = OFFSET + 3553;
	public static final int DB_ERROR_FAILED_D_AUCTION_GAIN_ITEM_PAY_UPDATE_BID_STATUS = OFFSET + 3554;
	public static final int DB_ERROR_FAILED_D_AUCTION_GAIN_ITEM_PAY_UNKNOWN_GAIN_TYPE = OFFSET + 3555;
	public static final int DB_ERROR_FAILED_D_AUCTION_GAIN_ITEM_PAY_UPDATE_GOLD = OFFSET + 3556;
	public static final int DB_ERROR_FAILED_D_AUCTION_GAIN_ITEM_PAY_UPDATE_CASH = OFFSET + 3557;
	public static final int DB_ERROR_FAILED_D_AUCTION_GAIN_ITEM_PAY_UNKNOWN_PAY_TYPE = OFFSET + 3558;
	public static final int DB_ERROR_FAILED_D_AUCTION_GAIN_ITEM_PAY_UPDATE_GAIN_TYPE_REG_GOODS_PAY = OFFSET + 3559;
	public static final int DB_ERROR_FAILED_D_AUCTION_GAIN_ITEM_GOODS_BIDING = OFFSET + 3560;
	public static final int DB_ERROR_FAILED_D_AUCTION_REG_GOODS_REREG = OFFSET + 3561;
	public static final int DB_ERROR_FAILED_D_AUCTION_REG_GOODS_REG = OFFSET + 3562;
	
	
}




