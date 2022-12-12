package view;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import controller.CheckInfo;


public class MainFrame extends JFrame implements ActionListener {
  /**
   * 登陆主界面
   */
  private static final long serialVersionUID = 1L;
  JTextField idTextField;
  JPasswordField passwdTextField;
  JLabel idLabel, passwdLabel;
  JComboBox chooice;
  JButton logon;
  JPanel contain;

  int count = 0;
  String [] items = { "学生", "教师", "系统管理员"};

  void mkifnex(String filename){
    File file = new File(filename);
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (Exception e) {
        e.printStackTrace();
      }
      System.out.println("文件 "+filename+" 已创建");
      if(filename.endsWith("administrator.txt")){
        JOptionPane.showMessageDialog(null, "检测到不存在管理员账户，已自动新建（账号：1，密码：123456）请尽快修改", "警告", JOptionPane.INFORMATION_MESSAGE);

        try {
          FileWriter fw = new FileWriter(file);
          BufferedWriter bw = new BufferedWriter(fw);
          bw.write("1 123456 root male 1 1 1");
          bw.newLine();
          bw.close();
          fw.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }

      }
    }
  }

  void mkdifnex(String dirname){
    File folder = new File(dirname);
    if (!folder.exists() && !folder.isDirectory()) {
      folder.mkdirs();
      System.out.println("创建文件夹 "+dirname);
    } 
  }

  void init(){
    String path = System.getProperty("user.dir");
    mkdifnex(path+"/data/");
    mkifnex(path+"/data/student.txt");
    mkifnex(path+"/data/course.txt");
    mkifnex(path+"/data/teacher.txt");
    mkifnex(path+"/data/administrator.txt");
    mkdifnex(path+"/data/course_student");
    mkdifnex(path+"/data/grade");

  }

  public MainFrame() {
    super("账号登陆");
    init();
    setLocation(300, 200);
    setSize(300, 340);
    contain = new JPanel();
    contain.setLayout(null);
    idLabel = new JLabel("账号");
    passwdLabel = new JLabel("密码");
    idTextField = new JTextField();
    passwdTextField = new JPasswordField();
    logon = new JButton("登陆");
    chooice = new JComboBox<String>(items);
    idLabel.setBounds(42, 45, 75, 35);
    idTextField.setBounds(80, 45, 150, 35);
    passwdLabel.setBounds(40, 100, 75, 35);
    passwdTextField.setBounds(80, 100, 150, 35);
    chooice.setBounds(80, 160, 150, 35);
    logon.setBounds(102, 220, 70, 30);
    contain.add(idLabel);
    contain.add(idTextField);
    contain.add(passwdLabel);
    contain.add(passwdTextField);
    contain.add(chooice);
    contain.add(logon);
    logon.addActionListener(this);
    add(contain);
    setVisible(true);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == logon) {
      String ch = (String) chooice.getSelectedItem();
      if (ch == "学生") {
        if ((new CheckInfo().isMember("student", idTextField.getText(),
                new String(passwdTextField.getPassword()))) == 1) {
          setVisible(false);
          new StudentsPanel(idTextField.getText());
        } else {
          count += 1;
          if (count <= 5) {
            JOptionPane.showMessageDialog(null, "无此用户，或者密码输入错误！",
                "错误", JOptionPane.INFORMATION_MESSAGE);
          }
          if (count > 5) {
            JOptionPane.showMessageDialog(null, "错误次数超过5次！",
                "错误", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            setVisible(false);
            System.exit(0);
          }
        }
      } else if (ch == "教师") {
        if ((new CheckInfo().isMember("teacher", idTextField.getText(),
                new String(passwdTextField.getPassword(), 0,
                  passwdTextField.getPassword().length))) == 1) {
          setVisible(false);
          new TeachersPanel(idTextField.getText());
        } else {
          count += 1;
          if (count <= 5) {
            JOptionPane.showMessageDialog(null, "无此用户，或者密码输入错误！",
                "错误", JOptionPane.INFORMATION_MESSAGE);
          }
          if (count > 5) {
            JOptionPane.showMessageDialog(null, "错误次数超过5次！",
                "错误", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            setVisible(false);
            System.exit(0);
          }
        }
      } else if (ch == "系统管理员") {
        if ((new CheckInfo().isMember("administrator", idTextField
                .getText(), new String(passwdTextField.getPassword(),
                  0, passwdTextField.getPassword().length))) == 1) {
          setVisible(false);
          new AdministratorPanel(idTextField.getText());
        } else {
          count += 1;
          if (count <= 5) {
            JOptionPane.showMessageDialog(null, "无此用户，或者密码输入错误！",
                "错误", JOptionPane.INFORMATION_MESSAGE);
          }
          if (count > 5) {
            JOptionPane.showMessageDialog(null, "错误次数超过5次！",
                "错误", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            setVisible(false);
            System.exit(0);
          }
        }
      }
    }

  }



  public void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      this.dispose();
      setVisible(false);
      System.exit(0);
    }
  }

  public static void main(String[] args) {
    new MainFrame();
  }
}
