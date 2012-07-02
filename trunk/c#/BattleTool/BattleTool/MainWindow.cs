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
    public partial class MainWindow : Form
    {
        public MainWindow()
        {
            InitializeComponent(); 
            this.maintab.Padding = new System.Drawing.Point(CLOSE_SIZE, CLOSE_SIZE);
            
        }

        const int CLOSE_SIZE = 15;
        Bitmap closeHover = new Bitmap("res/close_hover.png");
        Bitmap closeLeave = new Bitmap("res/close_leave.png");

        private void maintab_DrawItem(object sender, DrawItemEventArgs e)
        {
            try
            {
                Rectangle myTabRect = this.maintab.GetTabRect(e.Index);
                
                e.Graphics.DrawString(this.maintab.TabPages[e.Index].Text
                , this.Font, SystemBrushes.ControlText, myTabRect.X + 2, myTabRect.Y + 2);
             
                Bitmap bt = new Bitmap(closeLeave);
                Point p5 = new Point(myTabRect.X + 60, 4);
                e.Graphics.DrawImage(bt, p5);
                e.Graphics.Dispose();
            }
            catch
            {

            }
            
        }


        private void maintab_MouseMove(object sender, MouseEventArgs e)
        {
        
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

        private void TSMI_ViewBattlefield_Click(object sender, EventArgs e)
        {
            BattleFieldView view=new BattleFieldView();
            view.Show();
        }

        private void maintab_TabIndexChanged(object sender, EventArgs e)
        {
            
        }

        private void maintab_SelectedIndexChanged(object sender, EventArgs e)
        {
           
        }

        private void MainWindow_KeyUp(object sender, KeyEventArgs e)
        {
            MessageBox.Show(e.KeyCode+","+Keys.Escape);
            if (e.KeyCode == Keys.Escape)
            {
                Application.Exit();
            }
        }

        private void CreateNewPage(int id)
        {
            TabPage page = new TabPage();
            page.Text="test page";
            page.Size = this.maintab.Size;
            this.maintab.Controls.Add(page);
            page.Select();
            page.Update();
            page.Paint += new PaintEventHandler(MainWindow_Paint);
            
            ScenePage scene=SceneFactory.newPage(page,id);
            scene.Draw();
        }


        #region
        Point oriPoint;//保存原有位置
        Control sourceObj;
        private void DragDown(object sender, MouseEventArgs e)
        {
            //记住原来的位置，这里是鼠标相对于button1的位置
            oriPoint = e.Location;
            sourceObj = (Control)sender;
        }

        private void DragUp(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                //这个地方记录的是button1对于窗口的位置
                Point newPos = sourceObj.Location;
                //适当调整button1的位置
                newPos.Offset(e.Location.X - oriPoint.X, e.Location.Y - oriPoint.Y);
                //保证拖动后控件还在当前窗体的可见范围内
                if (new Rectangle(new Point(0, 0), this.Size).Contains(newPos))
                {
                    sourceObj.Location = newPos;
                }
                else
                {
                    MessageBox.Show("你太狠了，出界了");
                }
            }
        }

        private void DragMove(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                //这个地方记录的是button1对于窗口的位置
                Point newPos = sourceObj.Location;
                //适当调整button1的位置
                newPos.Offset(e.Location.X - oriPoint.X, e.Location.Y - oriPoint.Y);
                //保证拖动后控件还在当前窗体的可见范围内
            //    if (new Rectangle(0, 0, this.Width - sourceObj.Width, this.Height - sourceObj.Height * 5 / 2).Contains(newPos))
               // {
                    sourceObj.Location = newPos;
                //}
                //else
               // {
                 //   MessageBox.Show("你太狠了，出界了");
                //}
            }
        }
        #endregion

        private void MainWindow_Paint(object sender, PaintEventArgs e)
        {
            ScenePage scene = SceneFactory.newPage((Panel)sender, 0);
            scene.Draw();
        }

        private void newPageToolStripMenuItem_Click(object sender, EventArgs e)
        {
            CreateNewPage(0);
        }

    }
}
