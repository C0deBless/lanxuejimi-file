namespace BattleTool
{
    partial class BattleFieldView
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
            this.TV_BATTLEFIELD_LIST = new System.Windows.Forms.TreeView();
            this.CMX_ADD_BATTLEFIELD = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.전장추가ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.CMX_ADD_BATTLEFIELD.SuspendLayout();
            this.SuspendLayout();
            // 
            // TV_BATTLEFIELD_LIST
            // 
            this.TV_BATTLEFIELD_LIST.Font = new System.Drawing.Font("Gulim", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.TV_BATTLEFIELD_LIST.ItemHeight = 20;
            this.TV_BATTLEFIELD_LIST.Location = new System.Drawing.Point(12, 12);
            this.TV_BATTLEFIELD_LIST.Name = "TV_BATTLEFIELD_LIST";
            this.TV_BATTLEFIELD_LIST.Size = new System.Drawing.Size(276, 513);
            this.TV_BATTLEFIELD_LIST.TabIndex = 0;
            // 
            // CMX_ADD_BATTLEFIELD
            // 
            this.CMX_ADD_BATTLEFIELD.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.전장추가ToolStripMenuItem});
            this.CMX_ADD_BATTLEFIELD.Name = "CMX_ADD_BATTLEFIELD";
            this.CMX_ADD_BATTLEFIELD.Size = new System.Drawing.Size(123, 26);
            this.CMX_ADD_BATTLEFIELD.ItemClicked += new System.Windows.Forms.ToolStripItemClickedEventHandler(this.CMX_ADD_BATTLEFIELD_ItemClicked);
            // 
            // 전장추가ToolStripMenuItem
            // 
            this.전장추가ToolStripMenuItem.Name = "전장추가ToolStripMenuItem";
            this.전장추가ToolStripMenuItem.Size = new System.Drawing.Size(122, 22);
            this.전장추가ToolStripMenuItem.Text = "전장추가";
            // 
            // BattleFieldView
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(300, 537);
            this.Controls.Add(this.TV_BATTLEFIELD_LIST);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedToolWindow;
            this.Name = "BattleFieldView";
            this.Text = "BattleFieldView";
            this.CMX_ADD_BATTLEFIELD.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TreeView TV_BATTLEFIELD_LIST;
        private System.Windows.Forms.ContextMenuStrip CMX_ADD_BATTLEFIELD;
        private System.Windows.Forms.ToolStripMenuItem 전장추가ToolStripMenuItem;
    }
}