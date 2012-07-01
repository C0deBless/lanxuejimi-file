using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace BattleTool
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent(); 
            this.maintab.Padding = new System.Drawing.Point(CLOSE_SIZE, CLOSE_SIZE);
        }

        const int CLOSE_SIZE = 15;
        Bitmap closeHover = new Bitmap("close_hover.png");
        Bitmap closeLeave = new Bitmap("close_leave.png");

        private void maintab_DrawItem(object sender, DrawItemEventArgs e)
        {
            try
            {
                Rectangle myTabRect = this.maintab.GetTabRect(e.Index);
                //myTabRect.Width = 100;
                //myTabRect.Height = 40;
               
                //this.maintab.Update();
                //先添加TabPage属性      
                //this.maintab.TabPages[e.Index] = 100;
                e.Graphics.DrawString(this.maintab.TabPages[e.Index].Text
                , this.Font, SystemBrushes.ControlText, myTabRect.X + 2, myTabRect.Y + 2);

                //再画一个矩形框   
                /* using (Pen p = new Pen(Color.White))
                 {
                     myTabRect.Offset(myTabRect.Width - (CLOSE_SIZE + 3), 2);
                     myTabRect.Width = CLOSE_SIZE;
                     myTabRect.Height = CLOSE_SIZE;
                     e.Graphics.DrawRectangle(p, myTabRect);

                 }
                  //填充矩形框   
                 Color recColor = e.State == DrawItemState.Selected ? Color.White : Color.White;
                 using (Brush b = new SolidBrush(recColor))
                 {
                     e.Graphics.FillRectangle(b, myTabRect);
                 }
                 */



                //画关闭符号   
             /*   using (Pen objpen = new Pen(Color.Black))
                {
                    //"\"线   
                    //Point p1 = new Point(myTabRect.X + 3, myTabRect.Y + 3);
                  //  Point p2 = new Point(myTabRect.X + myTabRect.Width - 3, myTabRect.Y + myTabRect.Height - 3);
                    //e.Graphics.DrawLine(objpen, p1, p2);

                    //"/"线   
                  //  Point p3 = new Point(myTabRect.X + 3, myTabRect.Y + myTabRect.Height - 3);
                 //  Point p4 = new Point(myTabRect.X + myTabRect.Width - 3, myTabRect.Y + 3);
                  //  e.Graphics.DrawLine(objpen, p3, p4);
                    ////=============================================   
                    
                    //e.Graphics.DrawString(this.MainTabControl.TabPages[e.Index].Text, this.Font, objpen.Brush, p5);   
                }*/
                Bitmap bt = new Bitmap(closeLeave);
                Point p5 = new Point(myTabRect.X + 60, 4);
                e.Graphics.DrawImage(bt, p5);

                //绘制小图标                 
                //==============================================================================   
                //Bitmap bt = new Bitmap("E:\\1\\2.jpg");   
                //Point p5 = new Point(4, 4);   
                ////e.Graphics.DrawImage(bt, e.Bounds);   
                //e.Graphics.DrawImage(bt, p5);   
                //Pen pt = new Pen(Color.Red);   
                ////e.Graphics.DrawString(this.MainTabControl.TabPages[e.Index].Text, this.Font, pt.Brush, e.Bounds);   
                //e.Graphics.DrawString(this.MainTabControl.TabPages[e.Index].Text, this.Font, pt.Brush, p5);   

                e.Graphics.Dispose();
            }
            catch
            {

            }   
        }


        private void maintab_MouseMove(object sender, MouseEventArgs e)
        {
            int x = e.X, y = e.Y;

            //计算关闭区域      
            Rectangle myTabRect = this.maintab.GetTabRect(this.maintab.SelectedIndex);
            
            myTabRect.Offset(myTabRect.Width - (CLOSE_SIZE + 3), 2);
            myTabRect.Width = CLOSE_SIZE;
            myTabRect.Height = CLOSE_SIZE;

            //如果鼠标在区域内就关闭选项卡      
            bool isClose = x > myTabRect.X && x < myTabRect.Right
             && y > myTabRect.Y && y < myTabRect.Bottom;

            if (isClose == true)
            {
                Bitmap bt = new Bitmap(closeLeave);
                Point p5 = new Point(myTabRect.X + 60, 4);
                //this.maintabGraphics.DrawImage(bt, p5);
            }
            else
            {
                Bitmap bt = new Bitmap(closeLeave);
                Point p5 = new Point(myTabRect.X + 60, 4);
                //this.maintabGraphics.DrawImage(bt, p5);
            }
        }

        private void maintab_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                int x = e.X, y = e.Y;

                //计算关闭区域      
                Rectangle myTabRect = this.maintab.GetTabRect(this.maintab.SelectedIndex);

                myTabRect.Offset(myTabRect.Width - (CLOSE_SIZE + 3), 2);
                myTabRect.Width = CLOSE_SIZE;
                myTabRect.Height = CLOSE_SIZE;

                //如果鼠标在区域内就关闭选项卡      
                bool isClose = x > myTabRect.X && x < myTabRect.Right
                 && y > myTabRect.Y && y < myTabRect.Bottom;

                if (isClose == true)
                {
                    this.maintab.TabPages.Remove(this.maintab.SelectedTab);
                }
            }   
        }


    }
}
