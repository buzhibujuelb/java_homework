package controller;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Course;

@SuppressWarnings("serial")
public class AddCourse extends JFrame implements ActionListener {
  /*
   * 教师增加课程
   */

  JPanel contain;
  JButton submit;
  JLabel id, name, gredit, classH, teacherId, teacherName;
  JTextField idt, namet, greditt, classHt, teacherIdt, teacherNamet;

  public AddCourse(String myid) {
    super("增加课程");
    setSize(300, 400);
    setLocation(600, 400);
    contain = new JPanel();
    contain.setLayout(null);
    id = new JLabel("课程号");
    name = new JLabel("课程名");
    gredit = new JLabel("学分");
    classH = new JLabel("学时");

    teacherId = new JLabel("教师号");
    teacherName = new JLabel("教师姓名");

    submit = new JButton("提交");
    idt = new JTextField();
    namet = new JTextField();
    greditt = new JTextField();
    classHt = new JTextField();
    teacherIdt = new JTextField();
    teacherNamet = new JTextField();

    id.setBounds(42, 35, 75, 35);
    idt.setBounds(80, 35, 150, 35);
    name.setBounds(40, 90, 75, 35);
    namet.setBounds(80, 90, 150, 35);
    gredit.setBounds(45, 145, 75, 35);
    greditt.setBounds(80, 145, 150, 35);
    classH.setBounds(45, 200, 75, 35);
    classHt.setBounds(80, 200, 150, 35);

    teacherId.setBounds(38, 245, 75, 35);
    teacherIdt.setBounds(80, 245, 150, 35);
    teacherName.setBounds(25, 290, 75, 35);
    teacherNamet.setBounds(80, 290, 150, 35);

    // 只能给自己开课

    String file = System.getProperty("user.dir") + "/data/teacher.txt";
    String[] result= new CheckInfo().getByid(file, myid);

    teacherIdt.setText(myid);
    teacherNamet.setText(result[2]);

    teacherIdt.setEditable(false);
    teacherNamet.setEditable(false);

    submit.setBounds(102, 330, 70, 25);

    contain.add(id);
    contain.add(idt);
    contain.add(name);
    contain.add(namet);
    contain.add(gredit);
    contain.add(greditt);
    contain.add(classH);
    contain.add(classHt);
    contain.add(teacherId);
    contain.add(teacherIdt);
    contain.add(teacherName);
    contain.add(teacherNamet);
    contain.add(submit);
    submit.addActionListener(this);
    add(contain);
    setVisible(true);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
  }

  public int hasCourse(String id) { // 教师开课前检查课程是否已经存在

    String file = System.getProperty("user.dir") + "/data/course.txt";
    try {
      BufferedReader br = new BufferedReader(new FileReader(file)); // 构造一个BufferedReader类来读取文件
      String s = null;
      while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
        String[] result = s.split(" ");
        if (result[0].equals(id)) {
          return 1;

        }
      }
      br.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return 0;
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == submit) {
      if ((idt.getText().equals("")) || (namet.getText().equals("")) || (greditt.getText().equals(""))
          || (classHt.getText().equals("")) || teacherIdt.getText().equals("")
          || teacherNamet.getText().equals("")) {
        JOptionPane.showMessageDialog(null, "信息不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
      } else {
        if ((hasCourse(idt.getText())) == 1) {
          JOptionPane.showMessageDialog(null, "此课程已经存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {

          String file = System.getProperty("user.dir") + "/data/course.txt";

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

          Course course = new Course(idt.getText(), namet.getText(), greditt.getText(), classHt.getText(),
              teacherIdt.getText(), teacherNamet.getText());

          modifiedContent.add(course.getCourseId() + " " + course.getCourseName() + " " + course.getCredit()
              + " " + course.getHour() + " " + course.getTeacherId() + " " + course.getTeacherName());

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

          // 除了添加对应课程文件外，还需要添加课程成绩文件以及课程学生文件
          File gradeFile = new File(System.getProperty("user.dir") + "/data/grade/" + course.getCourseName() + ".txt");
          File studentFile = new File(System.getProperty("user.dir") + "/data/course_student/" + course.getCourseName() + ".txt");

          try{
          if (gradeFile.createNewFile()) {
            System.out.println("课程文件新建成功");
          } 
          if (studentFile.createNewFile()) {
            System.out.println("课程学生文件新建成功");
          } 
          }catch(Exception e2){
          }

          JOptionPane.showMessageDialog(null, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
          this.dispose();
          setVisible(false);
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
