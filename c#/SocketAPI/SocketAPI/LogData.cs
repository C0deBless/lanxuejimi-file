using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace TigerNetwork
{
	public enum LogActionType
	{
		ClientLog = 5,
		
		Register1 = 6,
		Register2 = 7,

		RegisterGuest1 = 8,
		RegisterGuest2 = 9,

		Login = 10,
		LoginGuest = 11,

		GetUserInfo1 = 12, 
		GetUserInfo2 = 13,

		GameEnd = 14,
		Disconnect = 15,
	}

	public class LogData
	{
		public uint UserId;
		public int LogDate;
		public LogActionType ActionType;
		public string Var1 = "";
		public string Var2 = "";
		public readonly string[] Args = new string[6] { "", "", "", "", "", "" };

		public void Serialize(BinaryWriter writer)
		{
			writer.Write(this.UserId);
			writer.Write(this.LogDate);
			writer.Write((int)this.ActionType);
			writer.Write(this.Var1);
			writer.Write(this.Var2);

			foreach (string str in Args)
			{
				writer.Write(str);
			}
		}

		public static LogData Deserialize(BinaryReader reader)
		{
			uint userId = reader.ReadUInt32();
			int logDate = reader.ReadInt32();
			LogActionType actionType = (LogActionType)reader.ReadInt32();

			string var1 = reader.ReadString();
			string var2 = reader.ReadString();

			LogData logData = new LogData();
			for (short i = 0; i < logData.Args.Length; i++)
			{
				logData.Args[i] = reader.ReadString();
			}
			
			logData.UserId = userId;
			logData.LogDate = logDate;
			logData.ActionType = actionType;
			logData.Var1 = var1;
			logData.Var2 = var2;
			return logData;
		}
	}
}
