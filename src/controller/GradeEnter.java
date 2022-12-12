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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GradeEnter extends JFrame implements ActionListener {
	/*
	 * 教师登陆课程信息
	 */
	String idd;  // 教师号
	JPanel contain;
	JLabel id;
	JTextField idt, stuIdt, stuGradet;
	
	String targetFile;
	
	JButton submit, bn;
	ArrayList<String> modifiedContent = new ArrayList<String>();

	public GradeEnter(String idd) {
		super("查看");
		this.idd = idd;
		setSize(300, 340);
		setLocation(600, 400);
		contain = new JPanel();
		contain.setLayout(null);
		add(contain);
		id = new JLabel("课程号");
		idt = new JTextField();
		submit = new JButton("提交");
		id.setBounds(38, 50, 75, 35);
		idt.setBounds(80, 50, 150, 35);
		submit.setBounds(102, 125, 70, 30);
		contain.add(id);
		contain.add(idt); 
		contain.add(submit);
		submit.addActionListener(this);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			if (hasThisCourse(idt.getText()) ) {
				enter();   // 进入成绩输入界面
				
			} else {
				JOptionPane.showMessageDialog(null, "您未开设此课程！", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == bn) {
			
			if (hasThisStu(idt.getText(),stuIdt.getText()) ) {   // 登陆成绩

        String stuid = stuIdt.getText();
        double score = Double.valueOf(stuGradet.getText());
        if(score<0 || score>100){
				  JOptionPane.showMessageDialog(null, "成绩超出范围", "提示", JOptionPane.INFORMATION_MESSAGE);
          return;
        }

				String path = System.getProperty("user.dir")+"/data/course.txt";
        String[] check = new CheckInfo().getByid(path, idd);

        String curFile =  System.getProperty("user.dir")+"/data/grade/"+check[1]+".txt";
        String[] pre_score = new CheckInfo().getByid(curFile, stuid);
        if(pre_score[0].equals(stuid)){
				  JOptionPane.showMessageDialog(null, "该学生已有本科目成绩！", "提示", JOptionPane.INFORMATION_MESSAGE);
          return;
        }

        try{
          FileWriter fw = new FileWriter(curFile, true);
          BufferedWriter bw = new BufferedWriter(fw);
          bw.write(stuid+" "+ score);
          bw.newLine();
          bw.close();
          fw.close();
        }catch(IOException e1){
          e1.printStackTrace();
        }

				JOptionPane.showMessageDialog(null, "成绩登录成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
			
			} else {
				JOptionPane.showMessageDialog(null, "课程号为" + idt.getText() + "无此学生", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}


	void enter() {
		JFrame fm = new JFrame("登录成绩");
		fm.setSize(300, 340);
		JPanel contain = new JPanel();
		fm.setLocation(600, 400);
		contain.setLayout(null);
		bn = new JButton("提交");
		JLabel stuId = new JLabel("学号");
		JLabel stuGrade = new JLabel("成绩");
		
		stuIdt = new JTextField();
		stuGradet = new JTextField();
		
		stuId.setBounds(38, 50, 75, 35);
		stuIdt.setBounds(80, 50, 150, 35);
		
		stuGrade.setBounds(38, 110, 75, 35);
		stuGradet.setBounds(80, 110, 150, 35);
		
		
		bn.setBounds(170, 220, 70, 30);
		contain.add(stuId);
		contain.add(stuIdt);
		contain.add(stuGrade);
		contain.add(stuGradet);
		contain.add(bn);
		fm.add(contain);
		bn.addActionListener(this);
		
	
		fm.setVisible(true);
		
	}

  boolean hasThisStu(String courseid, String stuid){
    String file = System.getProperty("user.dir") + "/data/course.txt";
    String[] course = new CheckInfo().getByid(file,courseid);
    ArrayList<String> students = new CheckInfo().getAllInfo(System.getProperty("user.dir") + "/data/course_student/"+course[1]+".txt");
    for(int i=0;i<students.size();i++){
      if(students.get(i).equals(stuid))
        return true;
    }
    return false;
  }

	boolean hasThisCourse(String idd) {
		
		String file = System.getProperty("user.dir")+"/data/course.txt";
		try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	String[] result = s.split(" ");
            	if(result[4].equals(idd)){
            		return true;
            	}
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
		return false;
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}
}
