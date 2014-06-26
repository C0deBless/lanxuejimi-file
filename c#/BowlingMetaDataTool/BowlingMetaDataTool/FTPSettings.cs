using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Xml;

namespace BowlingMetaDataTool
{
	class WebFTPConfig
	{
		public string Host;
		public string UserName;
		public string Password;
	}

	class ServerModel
	{
		public int ID;
		public string Name;
		public WebFTPConfig WebFTP;
	}

	class ServerModelDataContext
	{
		public int ID { get; set; }
		public string Name { get; set; }
		public string WebFTPHost { get; set; }
		public string WebFTPUserName { get; set; }
		public string WebFTPPassword { get; set; }
	}

	class FTPSettings
	{
		private FTPSettings()
		{

		}

		private static readonly FTPSettings instance = new FTPSettings();
		public static FTPSettings Instance
		{
			get
			{
				return instance;
			}
		}

		public readonly Dictionary<int, ServerModel> SettingPool = new Dictionary<int, ServerModel>();
		public void LoadXML()
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
					ServerModel server = new ServerModel();
					server.ID = id;
					server.Name = name;

					XmlNode webFTPNode = node.SelectSingleNode("WebFTP");

					WebFTPConfig webFTP = ParseWebFTPConfig(webFTPNode);
					server.WebFTP = webFTP;

					SettingPool.Add(id, server);
				}
			}
			catch(Exception e)
			{
				Debug.WriteLine(e.StackTrace);
			}
			
		}

		public ObservableCollection<ServerModelDataContext> DataContext
		{
			get
			{
				ObservableCollection<ServerModelDataContext> collection = new ObservableCollection<ServerModelDataContext>();
				foreach (ServerModel server in SettingPool.Values)
				{
					collection.Add(new ServerModelDataContext()
					{
						ID = server.ID,
						Name = server.Name,
						WebFTPHost = server.WebFTP.Host,
						WebFTPPassword = server.WebFTP.Password,
						WebFTPUserName = server.WebFTP.UserName,
					});
				}

				return collection;
			}
		}

		private WebFTPConfig ParseWebFTPConfig(XmlNode node)
		{
			string host = node.Attributes["Host"].Value;
			string userName = node.Attributes["UserName"].Value;
			string password = node.Attributes["Password"].Value;
			WebFTPConfig config = new WebFTPConfig();
			config.Host = host;
			config.UserName = userName;
			config.Password = password;
			return config;
		}
	}
}
