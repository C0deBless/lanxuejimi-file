namespace BattleTool
{
    partial class MainWindow
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.fileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.openToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.viewToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.TSMI_ViewBattlefield = new System.Windows.Forms.ToolStripMenuItem();
            this.triggerToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.maintab = new System.Windows.Forms.TabControl();
            this.contextMenuStrip1 = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.button1ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.button2ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.button3ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.statusStrip1 = new System.Windows.Forms.StatusStrip();
            this.newPageToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.menuStrip1.SuspendLayout();
            this.contextMenuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuStrip1
            // 
            this.menuStrip1.BackColor = System.Drawing.SystemColors.ControlLight;
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.fileToolStripMenuItem,
            this.viewToolStripMenuItem,
            this.triggerToolStripMenuItem,
            this.toolsToolStripMenuItem,
            this.newPageToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(1512, 24);
            this.menuStrip1.TabIndex = 0;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // fileToolStripMenuItem
            // 
            this.fileToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.openToolStripMenuItem,
            this.saveToolStripMenuItem});
            this.fileToolStripMenuItem.Name = "fileToolStripMenuItem";
            this.fileToolStripMenuItem.Size = new System.Drawing.Size(37, 20);
            this.fileToolStripMenuItem.Text = "File";
            // 
            // openToolStripMenuItem
            // 
            this.openToolStripMenuItem.Name = "openToolStripMenuItem";
            this.openToolStripMenuItem.Size = new System.Drawing.Size(103, 22);
            this.openToolStripMenuItem.Text = "Open";
            // 
            // saveToolStripMenuItem
            // 
            this.saveToolStripMenuItem.Name = "saveToolStripMenuItem";
            this.saveToolStripMenuItem.Size = new System.Drawing.Size(103, 22);
            this.saveToolStripMenuItem.Text = "Save";
            // 
            // viewToolStripMenuItem
            // 
            this.viewToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.TSMI_ViewBattlefield});
            this.viewToolStripMenuItem.Name = "viewToolStripMenuItem";
            this.viewToolStripMenuItem.Size = new System.Drawing.Size(45, 20);
            this.viewToolStripMenuItem.Text = "View";
            // 
            // TSMI_ViewBattlefield
            // 
            this.TSMI_ViewBattlefield.Name = "TSMI_ViewBattlefield";
            this.TSMI_ViewBattlefield.Size = new System.Drawing.Size(122, 22);
            this.TSMI_ViewBattlefield.Text = "전장보기";
            this.TSMI_ViewBattlefield.Click += new System.EventHandler(this.TSMI_ViewBattlefield_Click);
            // 
            // triggerToolStripMenuItem
            // 
            this.triggerToolStripMenuItem.Name = "triggerToolStripMenuItem";
            this.triggerToolStripMenuItem.Size = new System.Drawing.Size(56, 20);
            this.triggerToolStripMenuItem.Text = "Trigger";
            // 
            // toolsToolStripMenuItem
            // 
            this.toolsToolStripMenuItem.Name = "toolsToolStripMenuItem";
            this.toolsToolStripMenuItem.Size = new System.Drawing.Size(47, 20);
            this.toolsToolStripMenuItem.Text = "Tools";
            // 
            // maintab
            // 
            this.maintab.AllowDrop = true;
            this.maintab.DrawMode = System.Windows.Forms.TabDrawMode.OwnerDrawFixed;
            this.maintab.ItemSize = new System.Drawing.Size(60, 18);
            this.maintab.Location = new System.Drawing.Point(0, 27);
            this.maintab.Name = "maintab";
            this.maintab.SelectedIndex = 0;
            this.maintab.Size = new System.Drawing.Size(1512, 882);
            this.maintab.TabIndex = 1;
            this.maintab.DrawItem += new System.Windows.Forms.DrawItemEventHandler(this.maintab_DrawItem);
            this.maintab.SelectedIndexChanged += new System.EventHandler(this.maintab_SelectedIndexChanged);
            this.maintab.MouseDown += new System.Windows.Forms.MouseEventHandler(this.maintab_MouseDown);
            this.maintab.MouseMove += new System.Windows.Forms.MouseEventHandler(this.maintab_MouseMove);
            // 
            // contextMenuStrip1
            // 
            this.contextMenuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.button1ToolStripMenuItem,
            this.button2ToolStripMenuItem,
            this.button3ToolStripMenuItem});
            this.contextMenuStrip1.Name = "contextMenuStrip1";
            this.contextMenuStrip1.Size = new System.Drawing.Size(118, 70);
            // 
            // button1ToolStripMenuItem
            // 
            this.button1ToolStripMenuItem.Name = "button1ToolStripMenuItem";
            this.button1ToolStripMenuItem.Size = new System.Drawing.Size(117, 22);
            this.button1ToolStripMenuItem.Text = "button1";
            // 
            // button2ToolStripMenuItem
            // 
            this.button2ToolStripMenuItem.Name = "button2ToolStripMenuItem";
            this.button2ToolStripMenuItem.Size = new System.Drawing.Size(117, 22);
            this.button2ToolStripMenuItem.Text = "button2";
            // 
            // button3ToolStripMenuItem
            // 
            this.button3ToolStripMenuItem.Name = "button3ToolStripMenuItem";
            this.button3ToolStripMenuItem.Size = new System.Drawing.Size(117, 22);
            this.button3ToolStripMenuItem.Text = "button3";
            // 
            // statusStrip1
            // 
            this.statusStrip1.Location = new System.Drawing.Point(0, 912);
            this.statusStrip1.Name = "statusStrip1";
            this.statusStrip1.Size = new System.Drawing.Size(1512, 22);
            this.statusStrip1.TabIndex = 2;
            this.statusStrip1.Text = "statusStrip1";
            // 
            // newPageToolStripMenuItem
            // 
            this.newPageToolStripMenuItem.Name = "newPageToolStripMenuItem";
            this.newPageToolStripMenuItem.Size = new System.Drawing.Size(69, 20);
            this.newPageToolStripMenuItem.Text = "NewPage";
            this.newPageToolStripMenuItem.Click += new System.EventHandler(this.newPageToolStripMenuItem_Click);
            // 
            // MainWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlLight;
            this.ClientSize = new System.Drawing.Size(1512, 934);
            this.Controls.Add(this.maintab);
            this.Controls.Add(this.menuStrip1);
            this.Controls.Add(this.statusStrip1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedToolWindow;
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "MainWindow";
            this.Text = "전장툴";
            this.KeyUp += new System.Windows.Forms.KeyEventHandler(this.MainWindow_KeyUp);
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.contextMenuStrip1.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem fileToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem openToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem viewToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem triggerToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem toolsToolStripMenuItem;
        private System.Windows.Forms.TabControl maintab;
        private System.Windows.Forms.ContextMenuStrip contextMenuStrip1;
        private System.Windows.Forms.ToolStripMenuItem button1ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem button2ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem button3ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem TSMI_ViewBattlefield;
        private System.Windows.Forms.StatusStrip statusStrip1;
        private System.Windows.Forms.ToolStripMenuItem newPageToolStripMenuItem;
    }
}

