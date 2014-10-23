using StackExchange.Redis;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace RedisStressTest
{
	class Program
	{
		static ConnectionMultiplexer conn;
		static ISubscriber subscriber;
		static IDatabase DB;

		static int TotalCount = 200000;
		static int CurrentCount = 1;
		static Random _random = new Random();

		static long totalTicks = 0;

		static void Main(string[] args)
		{
			ConfigurationOptions config = new ConfigurationOptions
			{
				EndPoints =
					{
						{ "192.168.0.192", 6379 },
					},
				KeepAlive = 180,
				Password = "wr2dis!"
			};
			conn = ConnectionMultiplexer.Connect(config);
			subscriber = conn.GetSubscriber();
			DB = conn.GetDatabase(0);

			//Task.Run(() =>
			//{
			//	for (; CurrentCount <= TotalCount; CurrentCount++)
			//	{
			//		int score = _random.Next(10, 5000);
			//		DB.SortedSetAdd("TestSet", CurrentCount, score);
			//	}
			//});

			//Task.Run(() =>
			//{
			//	Stopwatch watch = new Stopwatch();
			//	for (; CurrentCount <= TotalCount; CurrentCount++)
			//	{
			//		int userId = _random.Next(1, 2000000);
			//		int score = _random.Next(10, 5000);
			//		watch.Start();
			//		DB.SortedSetAdd("TestSet", userId, score);
			//		long? ranking = DB.SortedSetRank("TestSet", userId, Order.Descending);
			//		watch.Stop();
			//		totalTicks += watch.ElapsedTicks;
			//		watch.Reset();
			//	}
			//});

			//while (CurrentCount < TotalCount)
			//{
			//	Console.Clear();
			//	Console.WriteLine("CurrentCount -> {0}", CurrentCount);
			//	Thread.Sleep(1000);
			//}

			Console.WriteLine(DB.HashGet("LoginSet", 10000));
			Console.WriteLine(DB.HashGet("LoginSet", 10000).IsNull);

			//Console.WriteLine("TotalSeconds: " + (totalTicks / 10000.0 / 1000));
			//Console.WriteLine("Result, TotalCount:{0}, TotalTicks:{1}, ActionPerSecond:{2}", TotalCount, totalTicks, TotalCount / (totalTicks / 10000.0 / 1000));
			Console.Read();
		}
	}
}
