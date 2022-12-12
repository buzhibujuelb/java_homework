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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class ShowInfo extends JFrame implements ActionListener {
  /*
   * 查看所有用户信息
   */
  String id;
  JPanel contain;
  JButton submit;
  JRadioButton check1, check2, check3;
  JTable table;
  String[] title = {"账号", "姓名","性别", "生日", "学院", "专业"};
  JScrollPane jsp;

  public ShowInfo() {
    super("查看所有用户信息");
    setSize(600, 420);
    setLocation(600, 400);
    contain = new JPanel();
    contain.setLayout(null);
    submit = new JButton("查看");
    ButtonGroup bg = new ButtonGroup();
    check1 = new JRadioButton("学生", true);
    check2 = new JRadioButton("教师", false);
    check3 = new JRadioButton("系统管理员", false);
    bg.add(check1);
    bg.add(check2);
    bg.add(check3);
    check1.setBounds(80, 20, 80, 40);
    check2.setBounds(160, 20, 80, 40);
    check3.setBounds(240, 20, 100, 40);

    submit.setBounds(340, 20, 80, 30);

    contain.add(check1);
    contain.add(check2);
    contain.add(check3);
    contain.add(submit);
    submit.addActionListener(this);
    add(contain);
    setVisible(true);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
  }

  public void actionPerformed(ActionEvent e) {
    String file = System.getProperty("user.dir");

    if (e.getSource() == submit) {
      if(check1.isSelected()){
        file = file + "/data/student.txt";
      }else if(check2.isSelected()){
        file = file + "/data/teacher.txt";
      }else if(check3.isSelected()){
        file = file + "/data/administrator.txt";
      }
    ArrayList<String> cur = new CheckInfo().getAllInfo(file);

    int cnt = cur.size();
    for(int i=0;i<cur.size();i++){
      if(cur.get(i).split(" ").length!=7){
        //System.out.println("Invalid data");
        cnt--;
      }
    }

    Object[][] data = new Object[cnt][6];

    for(int i=0;i<cur.size();i++){
      String s = cur.get(i);
      String[] tmp = s.split(" ");
      if(tmp.length!=7){
        continue;
      }
      data[i][0]=tmp[0];
      data[i][1]=tmp[2];
      data[i][2]=tmp[3];
      data[i][3]=tmp[4];
      data[i][4]=tmp[5];
      data[i][5]=tmp[6];
    }
    table = new JTable(data, title){public boolean isCellEditable(int row, int column){return false;}};

    table.setBounds(20, 70, 550, 300);

    if(jsp != null){
      jsp.removeAll();
      remove(jsp);
    }

    jsp = new JScrollPane(table);
    add(jsp);
    jsp.setBounds(20,70,550,300);
    jsp.setVisible(true);


    }
  }

  public void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      setVisible(false);
      this.dispose();
    }
  }
}
