using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Tail
{
	class Program
	{
		public static string Path;// = "Z:\\STServer-1\\ProjectBowling-server.log";
		public static string FilterString;

		public static ConsoleColor DefaultColor = Console.ForegroundColor;
		public static ConsoleColor ErrorColor = ConsoleColor.Red;

		static void Main(string[] args)
		{
			for (int index = 0; index < args.Length; index++)
			{
				string arg = args[index];
				if (arg == "-p")
				{
					if (index == args.Length - 1)
					{
						Console.Error.WriteLine("should specify path");
						return;
					}
					else
					{
						Path = args[index + 1];
					}
				}

				if (arg == "grep")
				{
					if (index == args.Length - 1)
					{
						Console.Error.WriteLine("should specify grep value");
						return;
					}
					else
					{
						FilterString = args[index + 1];
					}
				}
			}

			Run();
		}
		private static void Run()
		{
			FileStream stream = new FileStream(Path, FileMode.Open, FileAccess.Read, FileShare.ReadWrite);
			StreamReader reader = new StreamReader(stream, Encoding.UTF8);

			bool foundEnd = false;
			while (true)
			{
				if (!reader.EndOfStream)
				{
					string line = reader.ReadLine();
					if (foundEnd)
					{
						if (FilterString != null && !line.Contains(FilterString))
						{
							// do nothing
						}
						else
						{
							if (line.Contains("ERROR"))
							{
								Console.ForegroundColor = ErrorColor;
							}
							else
							{
								Console.ForegroundColor = DefaultColor;
							}
							if (line.Length > Console.WindowWidth)
							{
								Console.WindowWidth = Math.Min(200, line.Length);
							}

							Console.WriteLine(line);
						}
						
						Thread.Sleep(10);
					}
				}
				else
				{
					if (!foundEnd)
					{
						foundEnd = true;
					}
				}
			}
		}
	}
}
