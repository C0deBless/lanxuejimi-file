using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace ProcessView.Pages
{
	/// <summary>
	/// Interaction logic for Home.xaml
	/// </summary>
	public partial class Home : UserControl
	{
        private Thread _thread;
        private delegate void InvokeDelegete();
        private int _threadInvokeInterval = 1000;

		public Home()
		{
			InitializeComponent();
            Start();
		}

        private void Start()
        {
            _thread = new Thread(new ThreadStart(Run));
            _thread.Start();
        }

        private void Update()
        {
            //this.TB_1.Text = DateTime.Now.ToString();
            Process[] processes = Process.GetProcesses();
            foreach (Process process in processes)
            {
                foreach (ProcessThread thread in process.Threads)
                {
                    //thread.
                }
            }
        }

        private void Run()
        {
            while (ProgramStatus.IsRunning)
            {
                this.TC_MAIN.Dispatcher.Invoke(new InvokeDelegete(Update));
                Thread.Sleep(_threadInvokeInterval);
            }
        }
	}
}
