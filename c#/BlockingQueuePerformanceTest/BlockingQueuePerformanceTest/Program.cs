using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace BlockingQueuePerformanceTest
{
	class ActionModel
	{

	}
	class Program
	{
		static readonly BlockingCollection<ActionModel> queue 
			= new BlockingCollection<ActionModel>(new ConcurrentQueue<ActionModel>());
		static int executionCount = 0;
		static long startTime = 0;
		static long endTime = 0;

		static void Main(string[] args)
		{
			Thread consumer = new Thread(new ThreadStart(Consumer));
			Thread producer = new Thread(new ThreadStart(Producer));
			consumer.Start();
			producer.Start();
			Thread.CurrentThread.Join();
			Console.Read();
		}

		static void Consumer()
		{
			while (true)
			{
				ActionModel model = queue.Take();
				executionCount++;
				if (executionCount >= 10000000)
				{
					// print
					endTime = DateTime.Now.Ticks;
					Console.WriteLine("time: {0} ms", (endTime - startTime) / 10000.0);
					break;
				}
			}
		}

		static void Producer()
		{
			startTime = DateTime.Now.Ticks;
			for (int i = 0; i < 10000000; i++)
			{
				queue.Add(new ActionModel());
			}
		}
	}
}
