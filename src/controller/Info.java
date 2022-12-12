package controller;

import java.awt.AWTEvent;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Student;
import model.Teacher;

public class Info extends JFrame {
	/**
	 * 用户查询个人信息
	 */
	private static final long serialVersionUID = 1L;
	JLabel idLabel, nameLabel, sexLabel, birLabel, insLabel, majorLabel;
  JTextArea idArea, nameArea, sexArea, birArea, insArea, majorArea;
  
	String id, name, pwd, sex, birthday, institute, major;
	JPanel stuInfoJPanel;

	Student stu;
	Teacher t;

	public Info(String id, int flag) {
		super("信息");
		this.id = id;
		setSize(300, 340);
		setLocation(600, 400);
		stuInfoJPanel = new JPanel();
		//stuInfoJPanel.setLayout(new GridLayout(20, 1));
		stuInfoJPanel.setLayout(null);
		String file = "";
		if (flag == 1) {
			// file = "D://test//student.txt";
			file = System.getProperty("user.dir") + "/data/student.txt";
		} else {
			// file = "D://test//teacher.txt";
			file = System.getProperty("user.dir") + "/data/teacher.txt";
		}

		// StringBuilder result = new StringBuilder();
				String[] result = new CheckInfo().getByid(file, id);
				if(!result[0].equals(id)){
          System.out.println("Id not found");
        }
					id = result[0];
					pwd = result[1];
					name = result[2];
					sex = result[3];
					birthday = result[4];
					institute = result[5];
					major = result[6];

          idLabel = new JLabel("账号:");
          idArea = new JTextArea(id);
          nameLabel = new JLabel("姓名:");
          nameArea = new JTextArea(name);
          sexLabel = new JLabel("性别:");
          sexArea = new JTextArea(sex);
          birLabel = new JLabel("生日:");
          birArea = new JTextArea(birthday);
          insLabel = new JLabel("学院:" );
          insArea = new JTextArea(institute);
          majorLabel = new JLabel("专业:");
          majorArea = new JTextArea(major);
          idArea.setEditable(false);
          nameArea.setEditable(false);
          sexArea.setEditable(false);
          birArea.setEditable(false);
          insArea.setEditable(false);
          majorArea.setEditable(false);




		idLabel.setBounds(42, 20, 75, 35);
		idArea.setBounds(80, 25, 150, 25);
		nameLabel.setBounds(42, 60, 75, 35);
		nameArea.setBounds(80, 65, 150, 25);
		sexLabel.setBounds(42, 100, 75, 35);
		sexArea.setBounds(80, 105, 150, 25);
		birLabel.setBounds(40, 145, 75, 35);
		birArea.setBounds(80, 150, 150, 25);
		insLabel.setBounds(40, 190, 75, 35);
		insArea.setBounds(80, 195, 150, 25);
		majorLabel.setBounds(36, 235, 75, 35);
		majorArea.setBounds(80, 240, 150, 25);


		stuInfoJPanel.add(idLabel);
		stuInfoJPanel.add(idArea);
		stuInfoJPanel.add(nameLabel);
		stuInfoJPanel.add(nameArea);
		stuInfoJPanel.add(sexLabel);
		stuInfoJPanel.add(sexArea);
		stuInfoJPanel.add(birLabel);
		stuInfoJPanel.add(birArea);
		stuInfoJPanel.add(insLabel);
		stuInfoJPanel.add(insArea);
		stuInfoJPanel.add(majorLabel);
		stuInfoJPanel.add(majorArea);
		add(stuInfoJPanel);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		setVisible(true);

	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}
}
