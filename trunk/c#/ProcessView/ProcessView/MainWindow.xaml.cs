using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace ProcessView
{
	/// <summary>
	/// Interaction logic for MainWindow.xaml
	/// </summary>
	public partial class MainWindow : Window
	{
		public MainWindow()
		{
			InitializeComponent();
			Process[] processes = Process.GetProcesses();
			foreach (Process pros in processes)
			{
				Console.WriteLine(String.Format("Process id:{0}, name:{1}", pros.Id, pros.ProcessName));
				foreach (ProcessThread thread in pros.Threads)
				{
					Console.WriteLine(String.Format("------Thread id:{0}, status:{1}", thread.Id, thread.ThreadState));
				}
			}
		}
	}
}
