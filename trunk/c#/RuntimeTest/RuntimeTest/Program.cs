using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace RuntimeTest
{
	class Program
	{
		static void Main(string[] args)
		{
			ProcessThreadCollection col = Process.GetCurrentProcess().Threads;
			Console.WriteLine(Process.GetCurrentProcess().TotalProcessorTime);
			for (int i = 0; i < 100000000; i++)
			{

			}
			Thread.Sleep(1000);
			Console.WriteLine(Process.GetCurrentProcess().TotalProcessorTime);
			Console.ReadLine();
		}
	}
}
