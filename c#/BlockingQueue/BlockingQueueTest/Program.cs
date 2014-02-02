using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BlockingQueue;
using System.Threading;
using System.Collections.Concurrent;

namespace BlockingQueueTest
{
    class Program
    {
        static BlockingQueue<int> TestQueue =new BlockingQueue<int>();
		static BlockingCollection<int> TestQueue2 = new BlockingCollection<int>(new ConcurrentQueue<int>());

		static long QueueTime = 0;

        static void Main(string[] args)
        {
            Thread productThread = new Thread(new ThreadStart(Product));
            Thread consumeThread1 = new Thread(new ThreadStart(Consume));
            Thread consumeThread2 = new Thread(new ThreadStart(Consume));
            consumeThread1.Name = "consume1";
            consumeThread2.Name = "consume2";
            productThread.Start();
            consumeThread1.Start();
            consumeThread2.Start();

            Console.ReadLine();
        }
        static void Product()
        {
            while (true)
            {
				QueueTime = DateTime.Now.Ticks;
				//TestQueue2.Add(2);
				//TestQueue2.Add(3);
                TestQueue.Push(2);
                TestQueue.Push(3);
                Thread.Sleep(5000);
            }
        }
        static void Consume()
        {
            while (true)
            {
				//int value = TestQueue2.Take();
                int value = TestQueue.Pull();
				Console.WriteLine("pull queue data :{0},{1},{2} * 100 ns", value, Thread.CurrentThread.Name, (DateTime.Now.Ticks - QueueTime)); ;
            }
        }
    }
}
