using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;

namespace WindowsFormsApplication1
{
    public partial class Form1 : Form
    {
        int[,] data_array = new int[100, 2];
        public void Form1_GotFocus()
        {
            textBox1.Focus();
        }
        public Form1()
        {
            InitializeComponent();
            fileloading();
            textBox1.Tag = false;
            textBox1.GotFocus += new EventHandler(textBox1_GotFocus);
            textBox1.MouseUp += new MouseEventHandler(textBox1_MouseUp);
        }
        void textBox1_MouseUp(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left && (bool)textBox1.Tag == true)
            {
                textBox1.SelectAll();
            }
            textBox1.Tag = false;
        }
        public void textBox1_GotFocus(object sender, EventArgs e)
        {
            textBox1.Tag = true;
            textBox1.SelectAll();
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            string info_pipe = textBox1.Text;
            txtbini();
            //textBox_D.Text = "";
            //textBox_L.Text = "0";
            info_pipe = info_pipe.ToUpper();
            int PL_int = 0;
            int flag = 0;
            for (int i = 0; i < info_pipe.Length; i++)
            {
                if ((info_pipe.Substring(i, 1) == "D")&i!=0)
                {
                    //flag: 1=找到“D”。2=判断为OD。 3=判断带“L”。 
                    //寻找管径部分
                    //寻找“D”位置
                    if (flag == 0)
                    {
                        flag = 1;
                        if (info_pipe.Substring(i - 1, 1) == "I")
                        {
                            MessageBox.Show("本软件暂时不支持内径管，请自行计算( ˉ □ ˉ )");
                            textBox1.Text = "OD###X# L=##M";
                        }
                    }
                    //寻找“O”位置
                    if (info_pipe.Substring(i - 1, 1) == "O")
                    {
                        for (int j = i; j < info_pipe.Length; j++)
                        {
                            if (info_pipe.Substring(j, 1) == "X")
                            {
                                textBox_D.Text = info_pipe.Substring(i + 1, j - i - 1);
                                flag = 2;
                            }
                        }
                    }
                }
                //寻找管长部分
                if (info_pipe.Substring(i, 1) == "=")
                {
                    for (int j = i; j < info_pipe.Length; j++)
                    {
                        if (info_pipe.Substring(j, 1) == "M")
                        {
                            textBox_L.Text = info_pipe.Substring(i + 1, j - i - 1);
                            flag = 3;
                        }
                    }
                }
                //判断全数字部分
                if (isNumberic(info_pipe.Substring(i, 1), out PL_int))
                {
                    if (isNumberic(info_pipe, out PL_int))
                    {
                        textBox_D.Text = info_pipe;
                    }
                    else
                    {
                        if (flag == 0)
                        {
                            textBox_D.Text = "无法识别";
                        }
                    }
                }
            }
        }

        public bool isNumberic(string message, out int result)
        {
            result = -1;
            try
            {
                result = int.Parse(message);
                return true;
            }
            catch
            {
                return false;
            }
        }
        public void calculation()
        {
            int flag = 1;
            int PL_int, WN_int, WR_int, NF_int, FIX_int;
            double L_dbl;
            if (isNumberic(textBox_L.Text, out PL_int)) { }
            else { flag = 0; }
            if (isNumberic(textBox_WN.Text, out WN_int)) { }
            else { flag = 0; }
            if (isNumberic(textBox_WR.Text, out WR_int)) { }
            else { flag = 0; }
            if (isNumberic(textBox_NF.Text, out NF_int)) { }
            else { flag = 0; }
            if (isNumberic(textBox_FIX.Text, out FIX_int)) { }
            else { flag = 0; }
            if (flag == 1)
            {
                L_dbl = (PL_int * 1000 * 1f + WN_int * 2 * WR_int * 1f + NF_int * 550 * 1f + FIX_int * 1f) / 1000;
                textBox7.Text = Convert.ToString(L_dbl);
                Clipboard.SetText(textBox7.Text);
            }
        }
        public void txtbini()
        {
            textBox_D.Text = "";
            textBox_L.Text = "0";
            textBox_WN.Text = "0";
            textBox_NF.Text = "0";
            textBox_FIX.Text = "0";
            textBox_WR.Text = "0";
            return;
        }
        public void fileloading()
        {
            FileStream fs = new FileStream("C:\\BWYQ\\pipe_data.txt", FileMode.Open);
            StreamReader m_streamReader = new StreamReader(fs);
            m_streamReader.BaseStream.Seek(0, SeekOrigin.Begin);
            int i = 0;
            string strLine = m_streamReader.ReadLine();
            do
            {
                string[] split = strLine.Split('/');
                string a = split[0];
                string b = split[1];
                int a_int,b_int;
                if(isNumberic(a,out a_int)&isNumberic(b,out b_int))
                {
                    data_array[i,0]=a_int;
                    data_array[i,1]=b_int;
                    i++;
                }   
                strLine = m_streamReader.ReadLine();
            } while (strLine != null && strLine != "");
            m_streamReader.Close();
            m_streamReader.Dispose();
            fs.Close();
            fs.Dispose();
            Console.Write(data_array);
            Console.ReadLine();
        }

        private void textBox_D_TextChanged(object sender, EventArgs e)
        {
            if (textBox_D.Text != "")
            {
                for (int i = 0; i < data_array.GetLength(0); i++)
                {
                    if (Convert.ToInt32(textBox_D.Text) == data_array[i, 0])
                    {
                        textBox_WR.Text = Convert.ToString(data_array[i, 1]);
                    }
                }
            }
        }

        private void textBox_WN_TextChanged(object sender, EventArgs e)
        {
            calculation();
        }

        private void textBox_NF_TextChanged(object sender, EventArgs e)
        {
            calculation();
        }

        private void textBox_FIX_TextChanged(object sender, EventArgs e)
        {
            calculation();
        }

        private void textBox_L_TextChanged(object sender, EventArgs e)
        {
            calculation();
        }

        private void textBox_WR_TextChanged(object sender, EventArgs e)
        {
            calculation();
        }

        public void textBoxD_GotFocus(object sender, EventArgs e)
        {
            textBox_D.SelectAll();
        }

        public void textBoxWN_GotFocus(object sender, EventArgs e)
        {
            textBox_WN.SelectAll();
        }

        public void textBoxNF_GotFocus(object sender, EventArgs e)
        {
            textBox_NF.SelectAll();
        }

        public void textBoxFIX_GotFocus(object sender, EventArgs e)
        {
            textBox_FIX.SelectAll();
        }

        public void textBoxL_GotFocus(object sender, EventArgs e)
        {
            textBox_L.SelectAll();
        }

        public void textBoxWR_GotFocus(object sender, EventArgs e)
        {
            textBox_WR.SelectAll();
        }

    }
}
