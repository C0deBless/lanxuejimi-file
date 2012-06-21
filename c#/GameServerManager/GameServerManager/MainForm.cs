using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using NetMXHelper;

namespace GameServerManager
{
    public partial class MainForm : Form
    {
        Logger logger;
        private JMXClient jmxClient;

        public MainForm()
        {
            InitializeComponent();
            logger = Logger.getLogger(this.textbox);
        }

        private void btn_jmxclient_Click(object sender, EventArgs e)
        {
            jmxClient = new JMXClient(logger);
            jmxClient.run();
            
        }

        private void btn_send_Click(object sender, EventArgs e)
        {

        }
    }
}
