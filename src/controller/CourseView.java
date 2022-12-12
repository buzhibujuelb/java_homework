package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class CourseView extends JFrame {
  /*
   * 学生查询课程，教师查询所教授课程
   */

  JPanel contain;
  JTextArea list;

  public CourseView(String id, int flag) {
    super("课程");
    setSize(500, 400);
    contain = new JPanel();
    setLocation(600, 400);
    list = new JTextArea();
    list.setEditable(false);
    contain.add(list);
    list.append("课程编号\t课程名\t学分\t学时\t教师号\t教师名\n");

    String courseid, coursename,credit ,classhour, teacherid, teachername;

    if(flag == 0){   // 学生查询课程

      String courseFile = System.getProperty("user.dir")+"/data/course.txt";
      ArrayList<String> courses = new CheckInfo().getAllInfo(courseFile);

      for(int i=0;i<courses.size();i++){
        String cur_course = courses.get(i);
        String[] course=cur_course.split(" ");
        String curFile = System.getProperty("user.dir")+"/data/course_student/"+course[1]+".txt";
        String[] check = new CheckInfo().getByid(curFile, id);
        if(!check[0].equals(id))continue;
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

    }else if(flag == 1){      // 教师查询自己教授课程
      String courseFile = System.getProperty("user.dir")+"/data/course.txt";
      String s = null;

      try {
        BufferedReader br = new BufferedReader(new FileReader(courseFile));
        while((s = br.readLine())!=null){   //使用readLine方法，一次读一行
          String[] course = s.split(" ");
          if(!course[4].equals(id))continue;

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
        br.close();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    add(contain);
    setVisible(true);
  }
}
