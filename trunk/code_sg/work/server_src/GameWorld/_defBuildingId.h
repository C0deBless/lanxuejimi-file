//--xx2009_2_4--
//#include "_defBuildingId.h"

#ifndef DEF_BuildingId_HPP_
#define DEF_BuildingId_HPP_
class __DEF_BuildingId_HPP {};

class _eBuildingType {};
//--Building Id/建筑物代码定义
enum eBuildingType
{
	BID_NULL	= 0,//--0/(保留)
	BID_Start	= 1,//--0/(保留)
	//--
	BID_Palace	= 1,//--1：宫殿Palace
	BID_Temple	= 2,//--2：宗庙Temple
	BID_Square	= 3,//--3：广场Square
	BID_Bill	= 4,//--4：告示所Bill
	BID_Prison	= 5,//--5：监狱Prison
	BID_College	= 6,//--6：太学院College
	BID_Workman	= 7,//--7：工匠铺Workman
	BID_Drill	= 8,//--8：演练场Drill
	BID_Charity	= 9,//--9：养济院Charity
	BID_Constellations	= 10,//--10：观星台Constellations
	BID_Music	= 11,//--11：乐府Music
	BID_Market	= 12,//--12：市场Market
	BID_Ally	= 13,//--13：会所Ally
	BID_Army	= 14,//--14：军营Army
	BID_Bar		= 15,//--15：酒馆Bar
	BID_Martial	= 16,//--16：武馆Martial
	BID_Taoist	= 17,//--17：道场Taoist
	BID_Hospital= 18,//--18：医院Hospital
	BID_Cellar	= 19,//--19：地窖Cellar
	BID_Barn	= 20,//--20：仓库Barn
	BID_Walls	= 21,//--21：城墙Walls
	//--
	BID_End		= 21,
	BID_Type_MAX,//--22
};
#define MAX_Buildings	BID_Type_MAX
typedef enum eBuildingType BtType;
//--xx2009_2_17--typedef enum eBuildingType EBuildingType;
//--
inline const char* BuildingIdName(int id)
{
	if (id < BID_Start || id > BID_End)
		return "Unknown_Building";
	static char* s[MAX_Buildings]= 
	{
		"保留",

		"宫殿",
		"宗庙",
		"广场",
		"告示所",
		"监狱",
		"太学院",
		"工匠铺",
		"演练场",
		"养济院",
		"观星台",
		"乐府",
		"市场",
		"会所",
		"军营",
		"酒馆",
		"武馆",
		"道场",
		"医院",
		"地窖",
		"仓库",
		"城墙",
	};
	return s[id];
}

#endif
