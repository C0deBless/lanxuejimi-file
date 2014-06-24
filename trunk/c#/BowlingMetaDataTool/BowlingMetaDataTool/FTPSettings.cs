using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Xml;

namespace BowlingMetaDataTool
{
	class FTPSettings
	{
		public static void LoadXML()
		{
			try
			{
				XmlDocument xml = new XmlDocument();
				xml.Load("FTPSettings.xml");
				XmlNodeList nodeList = xml.SelectNodes("/Servers/Server");
				foreach (XmlNode node in nodeList)
				{
					int id = Convert.ToInt32(node.Attributes["ID"].Value);
					string name = node.Attributes["Name"].Value.ToString();
					Debug.WriteLine("Server, ID:{0}, Name:{1}", id, name);
				}
			}
			catch(Exception e)
			{
				Debug.WriteLine(e.StackTrace);
			}
			
		}
	}
}
