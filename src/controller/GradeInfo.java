package controller;

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GradeInfo extends JFrame { 
	/**
	 * 学生根据学号查询所有成绩
	 */
	private static final long serialVersionUID = 1L;
	JPanel contain;
	JTextArea list;
	String id, courseid, coursename, teacherid, teachername, studentid, studentname, grade;

	public GradeInfo(String id) {
		super("课程");
		this.id = id;
		setSize(600, 400);
		contain = new JPanel();
		setLocation(600, 400);
		list = new JTextArea();
		list.setEditable(false);
		contain.add(list);

		list.append("课程号" + "\t");
		list.append("课程名" + "\t");
		list.append("教师工号" + "\t");
		list.append("教师姓名" + "\t");
		list.append("学号" + "\t");
		list.append("学生姓名" + "\t");
		list.append("成绩" + "\n");

		String path = System.getProperty("user.dir")+"/data/";
    ArrayList<String> courses = new CheckInfo().getAllInfo(path+"course.txt");
    for(int i=0;i<courses.size();i++){
      String[] cur = courses.get(i).split(" ");
      courseid = cur[0];
      coursename = cur[1];
      teacherid = cur[4];
      teachername = new CheckInfo().getByid(path+"teacher.txt", teacherid)[2];
      studentname =  new CheckInfo().getByid(path+"student.txt", id)[2];
      String[] check1 = new CheckInfo().getByid(path+"grade/"+coursename+".txt", id);
      if(!check1[0].equals(id))continue;
      grade = check1[1];

      list.append(courseid+"\t");
      list.append(coursename+"\t");
      list.append(teacherid+"\t");
      list.append(teachername+"\t");
      list.append(id+"\t");
      list.append(studentname+"\t");
      list.append(grade+"\n");
    }

		add(contain);
		setVisible(true);

	}
}
