package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class ApplyCourse extends JFrame implements ActionListener{
  /*
   * 学生报名课程
   */

  String id;
  JPanel contain;
  JTextArea list;
  JButton submit;
  JTextField inputField;
  JLabel info;

  String courseid, coursename,credit ,classhour, teacherid, teachername;

  public ApplyCourse(String id) {
    super("报名课程");
    this.id=id;
    setSize(500, 400);
    contain = new JPanel();
    contain.setLayout(null);
    setLocation(600, 400);
    list = new JTextArea();
    list.setEditable(false);
    submit = new JButton("确认");
    inputField = new JTextField();
    info = new JLabel("请输入想报名的课程");


    list.setBounds(20, 20, 460, 300);
    info.setBounds(40, 330, 120, 30);
    inputField.setBounds(180, 330, 80,30);
    submit.setBounds(280, 330, 80,30);

    contain.add(list);
    contain.add(info);
    contain.add(submit);
    contain.add(inputField);
    list.append("课程编号\t课程名\t学分\t学时\t教师号\t教师名\n");


    String courseFile = System.getProperty("user.dir")+"/data/course.txt";
    ArrayList<String> courses = new CheckInfo().getAllInfo(courseFile);

    for(int i=0;i<courses.size();i++){
      String cur_course = courses.get(i);
      String[] course=cur_course.split(" ");
      String curFile = System.getProperty("user.dir")+"/data/course_student/"+course[1]+".txt";
      String[] check = new CheckInfo().getByid(curFile, id);
      if(check[0].equals(id))continue;
      //System.out.printf("course[%s] has stu %s\n",course[1], id);

      courseid = course[0];
      coursename = course[1];
      credit = course[2];
      classhour = course[3];
      teacherid = course[4];
      teachername = course[5];

      list.append(courseid + "\t");
      list.append(coursename + "\t");
      list.append(credit + "\t");
      list.append(classhour + "\t");
      list.append(teacherid + "\t");
      list.append(teachername + "\n");
    }

    submit.addActionListener(this);
    add(contain);
    setVisible(true);
  }
  public void actionPerformed(ActionEvent e) {
    if(e.getSource()==submit){
      String apply_id = inputField.getText();
      String file = System.getProperty("user.dir")+"/data/course.txt";
      String[] course = new CheckInfo().getByid(file, apply_id);
      if(!course[0].equals(apply_id)){
        JOptionPane.showMessageDialog(null, "未找到该课程", "提示",
            JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      courseid = course[0];
      coursename = course[1];
      credit = course[2];
      classhour = course[3];
      teacherid = course[4];
      teachername = course[5];

      String curFile = System.getProperty("user.dir")+"/data/course_student/"+course[1]+".txt";
      String[] check = new CheckInfo().getByid(curFile, id);
      if(check[0].equals(id)){
        JOptionPane.showMessageDialog(null, "已报名过该课程", "提示",
            JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      int opt = JOptionPane.showConfirmDialog(null,
          "请确认是否报名课程：["+coursename+"]?", "确认信息",
          JOptionPane.YES_NO_OPTION);
      if (opt == JOptionPane.YES_OPTION) {
        try{
          FileWriter fw = new FileWriter(curFile, true);
          BufferedWriter bw = new BufferedWriter(fw);
          bw.write(id);
          bw.newLine();
          bw.close();
          fw.close();
        }catch(IOException e1){
          e1.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "报名成功!", "提示",
            JOptionPane.INFORMATION_MESSAGE);

        this.dispose();
        setVisible(false);
      }

    }
  }
}
