using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace BattleTool
{
    class DataManager
    {
        public string DataRoot;
        public string BattleDataRoot;
        public Dictionary<int, string> SeaList = new Dictionary<int, string>();

        public DataManager()
        {
            DataRoot = @"D:\trunk\design\data\";
            BattleDataRoot = DataRoot + @"BattleField\";

            DirectoryInfo di1 = new DirectoryInfo(DataRoot);
            if (!di1.Exists)
            {
                di1.Create();
            }

            DirectoryInfo di2 = new DirectoryInfo(BattleDataRoot);
            if (!di2.Exists)
            {
                di2.Create();
            }
        }

        public void loadSeaData()
        {
            string path= DataRoot + "sea.xml";
            string[] idList=XmlHelper.Read(path, "/sea_list/sea", "id");
            string[] nameList = XmlHelper.Read(path, "/sea_list/sea", "name");
            if (idList.Length == nameList.Length)
            {
                SeaList.Clear();
                for (int i = 0; i < idList.Length; i++)
                {
                    int key=Convert.ToInt32(idList[i]);
                    SeaList.Add(key, nameList[i]);
                }
            }
            else
            {

            }
        }

        public void loadBattleFieldData()
        {

        }
    }
}
