using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace GameServerManager
{
    public class Logger
    {
        TextBox textbox;
        private Logger(TextBox textbox)
        {
            this.textbox = textbox;
        }

        public static Logger getLogger(TextBox textbox)
        {
            return null;
        }
        public void Info(string message)
        {
            textbox.Text += "\n "+message;
        }
    }
}
