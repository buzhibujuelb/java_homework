package view;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.AddUser;
import controller.DeleteUser;
import controller.EditInfo;
import controller.ShowInfo;

@SuppressWarnings("serial")
public class AdministratorPanel extends JFrame implements ActionListener {
	/*
	 * 管理员登陆后操作主界面
	 */
	JButton deleteUser, addUser, selfInfo, relogInButton, showInfo;
	JPanel contain;
	String idd;

	public AdministratorPanel(String idd) {
		super("系统管理员");
		this.idd = idd;
		setLocation(300, 200);
		setSize(300, 340);
		contain = new JPanel();
		contain.setLayout(null);
		add(contain);
		selfInfo = new JButton("修改信息");
		showInfo = new JButton("查看所有用户信息");
		addUser = new JButton("增加用户");
		deleteUser = new JButton("删除用户");
		relogInButton = new JButton("重新登陆");
		selfInfo.setBounds(70, 30, 140, 30);
		showInfo.setBounds(70, 70, 140, 30);
		addUser.setBounds(70, 110, 140, 30);
		deleteUser.setBounds(70, 150, 140, 30);
		relogInButton.setBounds(70, 190, 140, 30);
		contain.add(selfInfo);
		contain.add(showInfo);
		contain.add(addUser);
		contain.add(deleteUser);
		contain.add(relogInButton);
		selfInfo.addActionListener(this);
		showInfo.addActionListener(this);
		addUser.addActionListener(this);
		deleteUser.addActionListener(this);
		relogInButton.addActionListener(this);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addUser) {
			new AddUser(); // 用户密码初始化统一为123456
		} else if (e.getSource() == deleteUser) {
			new DeleteUser();
		} else if (e.getSource() == selfInfo) {
			new EditInfo(idd, 3);
		} else if (e.getSource() == showInfo){
      new ShowInfo();
    }else if (e.getSource() == relogInButton) {
			this.dispose();
			setVisible(false);
			new MainFrame();
		}
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
			System.exit(0);
		}
	}
}
