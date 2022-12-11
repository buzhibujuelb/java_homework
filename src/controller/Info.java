package controller;

import java.awt.AWTEvent;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Student;
import model.Teacher;

public class Info extends JFrame {
	/**
	 * 用户查询个人信息
	 */
	private static final long serialVersionUID = 1L;
	JLabel idLabel, nameLabel, genderLabel, birLabel, insLabel, majorLabel;
  JLabel idArea, nameArea, genderArea, birArea, insArea, majorArea;
  
	String id, name, pwd, gender, birthday, institute, major;
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
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
			String s = null;

			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行

				String[] result = s.split(" ");
				if (result[0].equals(id)) {
					id = result[0];
					pwd = result[1];
					name = result[2];
					gender = result[3];
					birthday = result[4];
					institute = result[5];
					major = result[6];

					if (flag == 1) {
						stu = new Student(id, pwd, name, gender, birthday, institute, major);
						idLabel = new JLabel("账号:");
            idArea = new JLabel(stu.getId());
						nameLabel = new JLabel("姓名:");
            nameArea = new JLabel(stu.getName());
						genderLabel = new JLabel("性别:");
            genderArea = new JLabel(stu.getSex());
						birLabel = new JLabel("生日:");
            birArea = new JLabel(stu.getBirthday());
						insLabel = new JLabel("学院:" );
            insArea = new JLabel(stu.getInstitute());
						majorLabel = new JLabel("系别:");
            majorArea = new JLabel(stu.getMajor());
					} else {
						t = new Teacher(id, pwd, name, gender, birthday, institute, major);
						idLabel = new JLabel("账号:" );
            idArea = new JLabel(t.getId());
						nameLabel = new JLabel("姓名:" );
            nameArea = new JLabel( t.getName());
						genderLabel = new JLabel("性别:" );
            genderArea = new JLabel(t.getSex());
						birLabel = new JLabel("生日:" );
            birArea = new JLabel(t.getBirthday());
						insLabel = new JLabel("学院:" );
            insArea = new JLabel(t.getInstitute());
						majorLabel = new JLabel("系别:" );
            majorArea = new JLabel(t.getMajor());
					}

				}

			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


		idLabel.setBounds(42, 20, 75, 35);
		idArea.setBounds(80, 20, 150, 35);
		nameLabel.setBounds(42, 60, 75, 35);
		nameArea.setBounds(80, 60, 150, 35);
		genderLabel.setBounds(42, 100, 75, 35);
		genderArea.setBounds(80, 100, 150, 35);
		birLabel.setBounds(40, 145, 75, 35);
		birArea.setBounds(80, 145, 150, 35);
		insLabel.setBounds(40, 190, 75, 35);
		insArea.setBounds(80, 190, 150, 35);
		majorLabel.setBounds(36, 235, 75, 35);
		majorArea.setBounds(80, 235, 150, 35);


		stuInfoJPanel.add(idLabel);
		stuInfoJPanel.add(idArea);
		stuInfoJPanel.add(nameLabel);
		stuInfoJPanel.add(nameArea);
		stuInfoJPanel.add(genderLabel);
		stuInfoJPanel.add(genderArea);
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
