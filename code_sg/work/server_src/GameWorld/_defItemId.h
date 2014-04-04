//--xx2009_2_4--
//#include "_defItemId.h"

#ifndef DEF_ItemId_HPP_
#define DEF_ItemId_HPP_
class __DEF_ItemId_HPP {};

//--Time No Balance
enum ETimeNoBalanceType
{
	TNB_NULL	= 0,
	//--新手保护期
	TNB_NEWBIE	= 0,//--HideItem/no item

	//--
	TNB_Start,//	= 1,
	//--免战 (保留) 5天
	TNB_NOWAR	= TNB_Start,

	//--风调雨顺	农业值+10%	1天
	TNB_FOOD110,	//--HideItem/only one item
	//--丰衣足食	商业值+10%	1天
	TNB_SILVER110,	//--HideItem/only one item
	//--礼乐文化	文化值+10%	1天
	TNB_CULTURE110,	//--HideItem/only one item
	//--人丁兴旺	快乐增长×2	8小时
	TNB_HAPPY200,	//--HideItem/only one item
	//--休养生息	免战	8小时
	TNB_REST_NOWAR,	//--HideItem/only one item

	//--农业加成（2天）	农业值提高15%	未激活	10金币
	TNB_FOOD115,
	//--商业加成（2天）	商业值提高15%	2:00:00	10金币
	TNB_SILVER115,
	//--文化加成（2天）	文化值提高5%	未激活	10金币
	TNB_CULTURE105,

	//--能工巧匠（3天）	额外获得1个建筑队列	未激活	30金币
	TNB_BUILDING1,

	//--低级仁君（2天）	1.5倍君主经验值	未激活	40金币
	TNB_EXP150,
	//--中级仁君（2天）	2倍君主经验值	未激活	60金币
	TNB_EXP200,
	//--高级仁君（2天）	3倍君主经验值	未激活	80金币
	TNB_EXP300,

	//--兵贵神速（2天）	行军速度提高10%	未激活	50金币
	TNB_SPEED110,
	//--固若金汤（2天）	防御能力提高10%	未激活	40金币
	TNB_STRENGTH110,
	//--金戈铁马（2天）	进攻能力提高10%	未激活	50金币
	TNB_ATTACK110,

	//--低级能臣（1天）	功勋值获得提高1.5倍	未激活	20金币
	TNB_CREDITEXP150,
	//--中级能臣（1天）	功勋值获得提高2倍	未激活	30金币
	TNB_CREDITEXP200,
	//--高级能臣（1天）	功勋值获得提高3倍	未激活	40金币
	TNB_CREDITEXP300,

	TNB_Type_MAX,//--
	TNB_End	= TNB_Type_MAX - 1,//--
	//--
	TNB_RESERVED_MAX	= 100,//--保留(最大)增值功能
};
typedef enum ETimeNoBalanceType	TNB_Type;
const long TNB_Time_Table[] =
{
//--	TNB_NULL	= 0,
//--	TNB_NEWBIE	= 0,//--新手保护期
	7*TM_SS_DAY,
//--	//--
//--	TNB_Start,//	= 1,
//--	//--免战 (保留) 5天
//--	TNB_NOWAR	= TNB_Start,
	5*TM_SS_DAY,
//--
//--	//--风调雨顺	农业值+10%	1天
//--	TNB_FOOD110,
	1*TM_SS_DAY,
//--	//--丰衣足食	商业值+10%	1天
//--	TNB_SILVER110,
	1*TM_SS_DAY,
//--	//--礼乐文化	文化值+10%	1天
//--	TNB_CULTURE110,
	1*TM_SS_DAY,
//--	//--人丁兴旺	快乐增长×2	8小时
//--	TNB_HAPPY200,
	8*TM_SS_HOUR,
//--	//--休养生息	免战	8小时
//--	TNB_REST_NOWAR,
	8*TM_SS_HOUR,
//--
//--	//--农业加成（2天）	农业值提高15%	未激活	10金币
//--	TNB_FOOD115,
	2*TM_SS_DAY,
//--	//--商业加成（2天）	商业值提高15%	2:00:00	10金币
//--	TNB_SILVER115,
	2*TM_SS_DAY,
//--	//--文化加成（2天）	文化值提高5%	未激活	10金币
//--	TNB_CULTURE105,
	2*TM_SS_DAY,
//--
//--	//--能工巧匠（3天）	额外获得1个建筑队列	未激活	30金币
//--	TNB_BUILDING1,
	3*TM_SS_DAY,
//--
//--	//--低级仁君（2天）	1.5倍君主经验值	未激活	40金币
//--	TNB_EXP150,
	2*TM_SS_DAY,
//--	//--中级仁君（2天）	2倍君主经验值	未激活	60金币
//--	TNB_EXP200,
	2*TM_SS_DAY,
//--	//--高级仁君（2天）	3倍君主经验值	未激活	80金币
//--	TNB_EXP300,
	2*TM_SS_DAY,
//--
//--	//--兵贵神速（2天）	行军速度提高10%	未激活	50金币
//--	TNB_SPEED110,
	2*TM_SS_DAY,
//--	//--固若金汤（2天）	防御能力提高10%	未激活	40金币
//--	TNB_STRENGTH110,
	2*TM_SS_DAY,
//--	//--金戈铁马（2天）	进攻能力提高10%	未激活	50金币
//--	TNB_ATTACK110,
	2*TM_SS_DAY,
//--
//--	//--低级能臣（1天）	功勋值获得提高1.5倍	未激活	20金币
//--	TNB_CREDITEXP150,
	1*TM_SS_DAY,
//--	//--中级能臣（1天）	功勋值获得提高2倍	未激活	30金币
//--	TNB_CREDITEXP200,
	1*TM_SS_DAY,
//--	//--高级能臣（1天）	功勋值获得提高3倍	未激活	40金币
//--	TNB_CREDITEXP300,
	1*TM_SS_DAY,
};

//--TNB_Type功能道具(假设存在)ID和以上定义相对应
//--这里只定义更多的其他功能道具
enum EItemID
{
	ITEM_NULL	= TNB_RESERVED_MAX,//--from

	ITEM_Start,// = 101,
	//--(保留)
	ITEM_NOWAR	= ITEM_Start,//	= 101,//--免战卡

	//--1级内政卡	减少2个小时建筑升级时间	使用立即有效	1	1
	//--2级内政卡	减少4个小时建筑升级时间	使用立即有效	0	2
	//--3级内政卡	随机减少10-20个小时建筑升级时间	使用立即有效	0	5
	//--1级军事卡	减少2个小时军队征召时间	使用立即有效	0	1
	//--2级军事卡	减少4个小时军队征召时间	使用立即有效	0	2
	//--3级军事卡	随机减少10-20个小时军队征召时间	使用立即有效	0	5
	//--1级科技卡	减少2个小时科技研究时间	使用立即有效	0	1
	//--2级科技卡	减少4个小时科技研究时间	使用立即有效	0	2
	//--3级科技卡	随机减少10-20个小时科技研究时间	使用立即有效	0	5
	//--1级资源卡	获得1小时城池资源产量	使用立即有效	0	1
	//--2级资源卡	获得2小时城池资源产量	使用立即有效	0	2
	//--3级资源卡	获得3小时城池资源产量	使用立即有效	0	5

	//--仁德卡	提高城池快乐值10点	使用立即有效	0	5金币
	ITEM_IHAPPY10,

	//--飞城卡	使用可将城池迁移到指定坐标	使用立即有效	0	30金币
	ITEM_IFLYCITY,

	//--华佗再世	复活当前城池死亡将领	使用立即有效	0	5金币

	//--酒肉之乐	提高5点，所有将领忠诚度	使用立即有效	0	2金币

	//--传音符	用在发送世界聊天，每条一个传音符。	使用立即有效	0	1
	ITEM_ISPEAK,

	//--奇迹建造卡	用于建造联盟奇迹	使用立即有效	0	50金币

	ITEM_ID_MAX,//--
	ITEM_End	= ITEM_ID_MAX - 1,//--

	//--宝箱
	IBOX_Start	= 251,//--

	//--青铜宝箱
	IBOX_COPPER,
	//--白银宝箱
	IBOX_SILVER,
	//--黄金宝箱
	IBOX_GOLD,
	//--麒麟宝箱
	IBOX_KIRIN,

	IBOX_MAX,//--
	IBOX_End	= IBOX_MAX - 1,//--

	//--
	ITEM_RESERVED_MAX	= 300,//--(保留)最多300种道具
};
typedef enum EItemID	IIEM_ID;
#define	ITEM_LEVEL_DEFAULT	0

#endif
