package controller;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DeleteUser extends JFrame implements ActionListener {
	/**
	 * 管理员删除用户
	 */
	private static final long serialVersionUID = 1L;
	JPanel contain;
	JLabel id;
	JTextField idt;
	JComboBox chooice;
	JButton submit;

	String file = System.getProperty("user.dir") + "/data/";
	// String file = "D://test//";

  String [] items = {"学生","教师","系统管理员","一键清空所有学生","一键清空所有教师","一键清空所有人员"};
	public DeleteUser() {
		super("删除用户");
		setSize(300, 340);
		setLocation(600, 400);
		contain = new JPanel();
		contain.setLayout(null);
		chooice = new JComboBox<String>(items);
		id = new JLabel("帐号");
		submit = new JButton("提交");
		idt = new JTextField();
		id.setBounds(42, 45, 75, 35);
		idt.setBounds(80, 45, 150, 35);
		chooice.setBounds(80, 100, 150, 35);
		submit.setBounds(102, 150, 70, 30);
		contain.add(id);
		contain.add(idt);
		contain.add(chooice);
		contain.add(submit);
		submit.addActionListener(this);
		add(contain);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			String ch = (String) chooice.getSelectedItem();
			if (ch == "学生") {
				if ((new CheckInfo().isMember("student", idt.getText(), "000") == 2)) {

					String file1 = file + "student.txt";
					deleteInfo(file1);

					JOptionPane.showMessageDialog(null, "删除学生成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "此学生不存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (ch == "教师") {
				if ((new CheckInfo().isMember("teacher", idt.getText(), "000") == 2)) {

					String file2 = file + "teacher.txt";
					deleteInfo(file2);

					JOptionPane.showMessageDialog(null, "删除教师成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "此教师不存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (ch == "教务员") {
				if ((new CheckInfo().isMember("administrator", idt.getText(), "000") == 2)) {

					String file3 = file + "administrator.txt";
					deleteInfo(file3);

					JOptionPane.showMessageDialog(null, "删除教务员成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "此系统管理员不存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
				}

			} else if (ch == "一键清空所有学生") {
				String file1 = file + "student.txt";
				deleteAll(file1);
				JOptionPane.showMessageDialog(null, "删除所有学生成功", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else if (ch == "一键清空所有教师") {
				String file2 = file + "teacher.txt";
				deleteAll(file2);
				JOptionPane.showMessageDialog(null, "删除所有教师成功", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else if (ch == "一键清空所有人员") {
				String file1 = file + "student.txt";
				String file2 = file + "teacher.txt";
				String file3 = file + "administrator.txt";
				deleteAll(file1);
				deleteAll(file2);
				deleteAll(file3);
				JOptionPane.showMessageDialog(null, "重置成功", "提示", JOptionPane.INFORMATION_MESSAGE);

			}
		}
	}

	public void deleteInfo(String file) {
		ArrayList<String> modifiedContent = new ArrayList<String>();
		// StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = br.readLine()) != null) { // 先将原来存在的信息存储起来
				String[] result = s.split(" ");
				if (result[0].equals(idt.getText())) {
					continue;
				}

				String s1 = "";
				for (int i = 0; i < result.length - 1; i++) {
					s1 = s1 + result[i];
					s1 = s1 + " ";
				}
				s1 = s1 + result[result.length - 1];
				// System.out.println(s1);
				modifiedContent.add(s1);
			}
			br.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);

			for (int i = 0; i < modifiedContent.size(); i++) {
				bw.write(modifiedContent.get(i));
				bw.newLine();
			}

			bw.close();
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void deleteAll(String file) {
		ArrayList<String> modifiedContent = new ArrayList<String>();
		// StringBuilder result = new StringBuilder();

		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("");
			bw.newLine();
			bw.close();
			fw.close();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}
}
