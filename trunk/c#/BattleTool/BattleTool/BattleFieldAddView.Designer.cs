namespace BattleTool
{
    partial class BattleFieldAddView
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
            this.LB_BF_ID = new System.Windows.Forms.Label();
            this.checkBox1 = new System.Windows.Forms.CheckBox();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.TB_SEAID = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.comboBox1 = new System.Windows.Forms.ComboBox();
            this.comboBox2 = new System.Windows.Forms.ComboBox();
            this.label3 = new System.Windows.Forms.Label();
            this.BTN_ADD_BF = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // LB_BF_ID
            // 
            this.LB_BF_ID.AutoSize = true;
            this.LB_BF_ID.Font = new System.Drawing.Font("Gulim", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.LB_BF_ID.Location = new System.Drawing.Point(75, 58);
            this.LB_BF_ID.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.LB_BF_ID.Name = "LB_BF_ID";
            this.LB_BF_ID.Size = new System.Drawing.Size(58, 16);
            this.LB_BF_ID.TabIndex = 0;
            this.LB_BF_ID.Text = "전장ID:";
            // 
            // checkBox1
            // 
            this.checkBox1.AutoSize = true;
            this.checkBox1.Location = new System.Drawing.Point(140, 95);
            this.checkBox1.Name = "checkBox1";
            this.checkBox1.Size = new System.Drawing.Size(128, 20);
            this.checkBox1.TabIndex = 1;
            this.checkBox1.Text = "함대 자동배치";
            this.checkBox1.UseVisualStyleBackColor = true;
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(140, 55);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(128, 26);
            this.textBox1.TabIndex = 2;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Gulim", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.label1.Location = new System.Drawing.Point(38, 20);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(95, 16);
            this.label1.TabIndex = 3;
            this.label1.Text = "소속 해역ID:";
            // 
            // TB_SEAID
            // 
            this.TB_SEAID.Location = new System.Drawing.Point(140, 17);
            this.TB_SEAID.Name = "TB_SEAID";
            this.TB_SEAID.ReadOnly = true;
            this.TB_SEAID.Size = new System.Drawing.Size(128, 26);
            this.TB_SEAID.TabIndex = 4;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(15, 131);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(119, 16);
            this.label2.TabIndex = 5;
            this.label2.Text = "적함대 Fleet ID:";
            // 
            // comboBox1
            // 
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.Location = new System.Drawing.Point(140, 128);
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(128, 24);
            this.comboBox1.TabIndex = 6;
            // 
            // comboBox2
            // 
            this.comboBox2.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBox2.FormattingEnabled = true;
            this.comboBox2.Location = new System.Drawing.Point(140, 168);
            this.comboBox2.Name = "comboBox2";
            this.comboBox2.Size = new System.Drawing.Size(128, 24);
            this.comboBox2.TabIndex = 7;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(56, 171);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(77, 16);
            this.label3.TabIndex = 8;
            this.label3.Text = "저형선택:";
            // 
            // BTN_ADD_BF
            // 
            this.BTN_ADD_BF.Location = new System.Drawing.Point(140, 209);
            this.BTN_ADD_BF.Name = "BTN_ADD_BF";
            this.BTN_ADD_BF.Size = new System.Drawing.Size(128, 31);
            this.BTN_ADD_BF.TabIndex = 9;
            this.BTN_ADD_BF.Text = "추가";
            this.BTN_ADD_BF.UseVisualStyleBackColor = true;
            this.BTN_ADD_BF.Click += new System.EventHandler(this.BTN_ADD_BF_Click);
            // 
            // BattleFieldAddView
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(298, 270);
            this.Controls.Add(this.BTN_ADD_BF);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.comboBox2);
            this.Controls.Add(this.comboBox1);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.TB_SEAID);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.checkBox1);
            this.Controls.Add(this.LB_BF_ID);
            this.Font = new System.Drawing.Font("Gulim", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedToolWindow;
            this.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.Name = "BattleFieldAddView";
            this.Text = "전장추가하기";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label LB_BF_ID;
        private System.Windows.Forms.CheckBox checkBox1;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox TB_SEAID;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ComboBox comboBox1;
        private System.Windows.Forms.ComboBox comboBox2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button BTN_ADD_BF;
    }
}