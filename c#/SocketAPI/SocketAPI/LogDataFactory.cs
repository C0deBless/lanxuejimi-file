using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.IO;

namespace TigerNetwork
{
	public class LogDataFactory
	{
		private UdpClient udpClient = new UdpClient();
		public LogDataFactory(string address, int port)
		{
			udpClient.Connect(new IPEndPoint(IPAddress.Parse(address), port));
		}

		public LogData MakeLogData(uint userId, LogActionType actionType, string[] args)
		{
			LogData data = new LogData();
			data.UserId = userId;
			data.ActionType = actionType;
			data.LogDate = (int)(this.ConvertDateTimeToUnixMilliTime(DateTime.Now.ToUniversalTime())/1000);
			for (int i = 0; i < data.Args.Length; i++)
			{
				if (i >= args.Length)
				{
					break;
				}
				data.Args[i] = args[i];
			}
			return data;
		}
		public void SendLogData(LogData logData)
		{
			using (MemoryStream stream = new MemoryStream())
			using (BinaryWriter writer = new BinaryWriter(stream))
			{
				logData.Serialize(writer);
				int dataSize = (int)stream.Position;
				byte[] buffer = new byte[dataSize];
				stream.Position = 0;
				stream.Read(buffer, 0, dataSize);
				udpClient.Send(buffer, buffer.Length);
			}
		}

		private long ConvertDateTimeToUnixMilliTime(DateTime time)
		{
			DateTime date = new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc);
			TimeSpan span = new TimeSpan(time.Ticks - date.Ticks);
			return (long)span.TotalMilliseconds;
		}
	}
}
