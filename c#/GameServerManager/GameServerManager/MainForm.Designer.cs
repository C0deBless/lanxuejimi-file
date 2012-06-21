namespace GameServerManager
{
    partial class MainForm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            this.btn_jmxclient = new System.Windows.Forms.Button();
            this.textbox = new System.Windows.Forms.TextBox();
            this.tb_operation = new System.Windows.Forms.TextBox();
            this.tb_param1 = new System.Windows.Forms.TextBox();
            this.lb_operation = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.tb_param2 = new System.Windows.Forms.TextBox();
            this.btn_send = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // btn_jmxclient
            // 
            this.btn_jmxclient.Location = new System.Drawing.Point(12, 42);
            this.btn_jmxclient.Name = "btn_jmxclient";
            this.btn_jmxclient.Size = new System.Drawing.Size(207, 40);
            this.btn_jmxclient.TabIndex = 0;
            this.btn_jmxclient.Text = "start jmx client";
            this.btn_jmxclient.UseVisualStyleBackColor = true;
            this.btn_jmxclient.Click += new System.EventHandler(this.btn_jmxclient_Click);
            // 
            // textbox
            // 
            this.textbox.Location = new System.Drawing.Point(12, 100);
            this.textbox.Multiline = true;
            this.textbox.Name = "textbox";
            this.textbox.Size = new System.Drawing.Size(1047, 536);
            this.textbox.TabIndex = 1;
            // 
            // tb_operation
            // 
            this.tb_operation.Location = new System.Drawing.Point(294, 54);
            this.tb_operation.Name = "tb_operation";
            this.tb_operation.Size = new System.Drawing.Size(100, 21);
            this.tb_operation.TabIndex = 2;
            // 
            // tb_param1
            // 
            this.tb_param1.Location = new System.Drawing.Point(458, 54);
            this.tb_param1.Name = "tb_param1";
            this.tb_param1.Size = new System.Drawing.Size(100, 21);
            this.tb_param1.TabIndex = 3;
            // 
            // lb_operation
            // 
            this.lb_operation.AutoSize = true;
            this.lb_operation.Location = new System.Drawing.Point(225, 62);
            this.lb_operation.Name = "lb_operation";
            this.lb_operation.Size = new System.Drawing.Size(63, 12);
            this.lb_operation.TabIndex = 4;
            this.lb_operation.Text = "Operation:";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(400, 62);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(52, 12);
            this.label1.TabIndex = 5;
            this.label1.Text = "Param1:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(564, 63);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(52, 12);
            this.label2.TabIndex = 6;
            this.label2.Text = "Param2:";
            // 
            // tb_param2
            // 
            this.tb_param2.Location = new System.Drawing.Point(622, 53);
            this.tb_param2.Name = "tb_param2";
            this.tb_param2.Size = new System.Drawing.Size(100, 21);
            this.tb_param2.TabIndex = 7;
            // 
            // btn_send
            // 
            this.btn_send.Location = new System.Drawing.Point(744, 35);
            this.btn_send.Name = "btn_send";
            this.btn_send.Size = new System.Drawing.Size(75, 41);
            this.btn_send.TabIndex = 8;
            this.btn_send.Text = "Send";
            this.btn_send.UseVisualStyleBackColor = true;
            this.btn_send.Click += new System.EventHandler(this.btn_send_Click);
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1071, 648);
            this.Controls.Add(this.btn_send);
            this.Controls.Add(this.tb_param2);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.lb_operation);
            this.Controls.Add(this.tb_param1);
            this.Controls.Add(this.tb_operation);
            this.Controls.Add(this.textbox);
            this.Controls.Add(this.btn_jmxclient);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "MainForm";
            this.Text = "Game Server Manager";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btn_jmxclient;
        private System.Windows.Forms.TextBox textbox;
        private System.Windows.Forms.TextBox tb_operation;
        private System.Windows.Forms.TextBox tb_param1;
        private System.Windows.Forms.Label lb_operation;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox tb_param2;
        private System.Windows.Forms.Button btn_send;
    }
}

