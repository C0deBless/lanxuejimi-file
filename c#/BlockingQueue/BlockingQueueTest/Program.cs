using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BlockingQueue;
using System.Threading;

namespace BlockingQueueTest
{
    class Program
    {
        static BlockingQueue<int> TestQueue =new BlockingQueue<int>();
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
                TestQueue.Push(2);
                TestQueue.Push(3);
                Thread.Sleep(5000);
            }
        }
        static void Consume()
        {
            while (true)
            {
                int value = TestQueue.Pull();
                Console.WriteLine("pull queue data :{0},{1}", value, Thread.CurrentThread.Name);
            }
        }
    }
}
