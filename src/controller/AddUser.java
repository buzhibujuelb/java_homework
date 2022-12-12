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

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AddUser extends JFrame implements ActionListener {
	/*
	 * 教务管理员添加用户，可以添加学生，教师，管理员
	 */
	JPanel contain;
	JLabel id, name, birthday, institute, major;
	JTextField idt, namet, birthdayt, institutet, majort;
	JRadioButton check1, check2;
	JButton submit;
	JComboBox chooice;

	String dir = System.getProperty("user.dir") + "/data/";
  String file ;
	// String file = "D://test//";

	public AddUser() {
		super("添加用户");
		setSize(300, 350);
		setLocation(600, 400);
		contain = new JPanel();
		contain.setLayout(null);
		id = new JLabel("帐号");
		name = new JLabel("姓名");
		check1 = new JRadioButton("男", true);
		check2 = new JRadioButton("女", false);
    ButtonGroup bg = new ButtonGroup();
    bg.add(check1);
    bg.add(check2);
		birthday = new JLabel("生日");
		institute = new JLabel("学院");
		major = new JLabel("专业");

    String [] items = {"学生","教师","系统管理员"};
		submit = new JButton("提交");
		chooice = new JComboBox<String>(items);

		idt = new JTextField();
		namet = new JTextField();

		birthdayt = new JTextField();
		institutet = new JTextField();
		majort = new JTextField();

		id.setBounds(42, 45, 75, 30);
		idt.setBounds(80, 45, 150, 30);
		// name.setBounds(40, 100, 75, 35);
		// namet.setBounds(80, 100, 150, 35);

		name.setBounds(42, 20, 75, 30);
		namet.setBounds(80, 20, 150, 30);

		check1.setBounds(80, 77, 80, 30);
		check2.setBounds(160, 77, 80, 30);
		birthday.setBounds(42, 110, 75, 30);
		birthdayt.setBounds(80, 110, 150, 30);
		institute.setBounds(40, 155, 75, 30);
		institutet.setBounds(80, 155, 150, 30);
		major.setBounds(40, 230, 75, 30);
		majort.setBounds(80, 230, 150, 30);

		chooice.setBounds(80, 190, 150, 30);
		submit.setBounds(102, 270, 70, 30);
		contain.add(id);
		contain.add(idt);
		contain.add(name);
		contain.add(namet);

		contain.add(birthday);
		contain.add(birthdayt);
		contain.add(institute);
		contain.add(institutet);
		contain.add(major);
		contain.add(majort);
		contain.add(check1);
		contain.add(check2);

		contain.add(chooice);
		contain.add(submit);
		submit.addActionListener(this);
		add(contain);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			if ((idt.getText().equals("")) || (namet.getText().equals("")) || (birthdayt.getText().equals(""))
					|| (institutet.getText().equals("")) || (majort.getText().equals(""))) {
				JOptionPane.showMessageDialog(null, "信息不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				String ch = (String) chooice.getSelectedItem();
				if (ch == "学生") {
					if ((new CheckInfo().isMember("student", idt.getText(), namet.getText())) == 2) {
						JOptionPane.showMessageDialog(null, "此学生已经存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
					} else {
						file = dir + "student.txt";

						ArrayList<String> modifiedContent = new ArrayList<String>();
						// StringBuilder result = new StringBuilder();

						try {
							BufferedReader br = new BufferedReader(new FileReader(file));
							String s = null;
							while ((s = br.readLine()) != null) { // 先将原来存在的信息存储起来
								String[] result = s.split(" ");

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

						String m;
						if (check1.isSelected()) {
							m = "male";
						} else {
							m = "female";
						}

						String user = idt.getText() + " " + "123456" + " " + namet.getText() + " " + m + " "
								+ birthdayt.getText() + " " + institutet.getText() + " " + majort.getText();

						modifiedContent.add(user);

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

						JOptionPane.showMessageDialog(null, "成功添加一个学生！", "提示", JOptionPane.INFORMATION_MESSAGE);
					}
				} else if (ch == "教师") {
					if ((new CheckInfo().isMember("teacher", idt.getText(), namet.getText())) == 2) {
						JOptionPane.showMessageDialog(null, "此教师已经存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
					} else {

						file = dir + "teacher.txt";

						ArrayList<String> modifiedContent = new ArrayList<String>();
						// StringBuilder result = new StringBuilder();
						try {
							BufferedReader br = new BufferedReader(new FileReader(file));
							String s = null;
							while ((s = br.readLine()) != null) { // 先将原来存在的信息存储起来
								String[] result = s.split(" ");

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

						String m;
						if (check1.isSelected()) {
							m = "male";
						} else {
							m = "female";
						}

						String user = idt.getText() + " " + "123456" + " " + namet.getText() + " " + m + " "
								+ birthdayt.getText() + " " + institutet.getText() + " " + majort.getText();

						modifiedContent.add(user);

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

						JOptionPane.showMessageDialog(null, "成功添加一个老师！", "提示", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					if ((new CheckInfo().isMember("administrator", idt.getText(), namet.getText())) == 2) {
						JOptionPane.showMessageDialog(null, "此系统管理员已经存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
					} else {

						file = file.concat("administrator.txt");

						ArrayList<String> modifiedContent = new ArrayList<String>();
						// StringBuilder result = new StringBuilder();
						try {
							BufferedReader br = new BufferedReader(new FileReader(file));
							String s = null;
							while ((s = br.readLine()) != null) { // 先将原来存在的信息存储起来
								String[] result = s.split(" ");

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

						String m;
						if (check1.isSelected()) {
							m = "male";
						} else {
							m = "female";
						}

						String user = idt.getText() + " " + "123456" + " " + namet.getText() + " " + m + " "
								+ birthdayt.getText() + " " + institutet.getText() + " " + majort.getText();

						modifiedContent.add(user);

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

						JOptionPane.showMessageDialog(null, "成功添加一个系统管理员！", "提示", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}
}
