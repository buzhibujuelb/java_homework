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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class EditInfo extends JFrame implements ActionListener {
  /*
   * 用户修改信息
   */
  String id;
  JPanel contain;
  JButton submit;
  JLabel name, inst, birth, pass1, pass2, major;
  JTextField namet, instt, birtht, pass1t, pass2t, majort;
  JRadioButton check1, check2;
  int flag;

  public EditInfo(String id, int flag) {
    super("修改信息");
    setSize(300, 420);
    setLocation(600, 400);
    this.id = id;
    this.flag = flag; // flag：0 学生；1教师；3：系统管理员
    contain = new JPanel();
    contain.setLayout(null);
    name = new JLabel("姓名");
    birth = new JLabel("生日");
    inst = new JLabel("学院");
    major = new JLabel("专业");
    pass1 = new JLabel("新密码\n（留空不修改）");
    pass2 = new JLabel("确认密码");
    submit = new JButton("提交");
    check1 = new JRadioButton("男", true);
    check2 = new JRadioButton("女", false);
    ButtonGroup bg = new ButtonGroup();
    bg.add(check1);
    bg.add(check2);

    instt = new JTextField();
    namet = new JTextField();
    birtht = new JTextField();
    majort = new JTextField();
    pass1t = new JPasswordField();
    pass2t = new JPasswordField();

    String file = "";

    //System.out.printf("flag=%d\n",flag);
    if (flag == 0) {
      file = System.getProperty("user.dir") + "/data/student.txt";
    } else if(flag == 1){
      file = System.getProperty("user.dir") + "/data/teacher.txt";
    }else if(flag == 3){
      file = System.getProperty("user.dir") + "/data/administrator.txt";
    }

    String[] result = new CheckInfo().getByid(file, id);
    if(result[0].equals(id)){
      namet.setText(result[2]);

      if(result[3].equals("male")){
        check1.setSelected(true);
        check2.setSelected(false);
      }else{
        check1.setSelected(false);
        check2.setSelected(true);
      }

      birtht.setText(result[4]);
      instt.setText(result[5]);
      majort.setText(result[6]);
    }

    name.setBounds(42, 20, 75, 35);
    namet.setBounds(80, 20, 150, 35);
    check1.setBounds(80, 60, 80, 40);
    check2.setBounds(160, 60, 80, 40);
    birth.setBounds(42, 100, 75, 35);
    birtht.setBounds(80, 100, 150, 35);
    inst.setBounds(40, 145, 75, 35);
    instt.setBounds(80, 145, 150, 35);
    major.setBounds(40, 190, 75, 35);
    majort.setBounds(80, 190, 150, 35);
    pass1.setBounds(36, 235, 75, 35);
    pass1t.setBounds(80, 235, 150, 35);
    pass2.setBounds(28, 280, 75, 35);
    pass2t.setBounds(80, 280, 150, 35);
    submit.setBounds(102, 325, 70, 30);
    contain.add(name);
    contain.add(namet);
    contain.add(check1);
    contain.add(check2);
    contain.add(birth);
    contain.add(birtht);
    contain.add(inst);
    contain.add(instt);
    contain.add(major);
    contain.add(majort);
    contain.add(pass1);
    contain.add(pass1t);
    contain.add(pass2);
    contain.add(pass2t);
    contain.add(submit);
    submit.addActionListener(this);
    add(contain);
    setVisible(true);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == submit) {
      if ((inst.getText().equals("")) || (birtht.getText().equals(""))
          || (namet.getText().equals(""))
          //|| (pass1t.getText().equals("")) || (pass2t.getText().equals(""))
         ) {
        JOptionPane.showMessageDialog(null, "信息不能为空！", "提示",
            JOptionPane.INFORMATION_MESSAGE);
      } else if (!(pass1t.getText().equals(pass2t.getText()))) {
        JOptionPane.showMessageDialog(null, "新密码与确认密码不同！", "提示",
            JOptionPane.INFORMATION_MESSAGE);
      } else if (!pass1t.getText().equals("") &&pass1t.getText().length() < 6) {
        JOptionPane.showMessageDialog(null, "密码长度至少为6位！", "提示",
            JOptionPane.INFORMATION_MESSAGE);
      } else {
        String m;
        if (check1.isSelected()) {
          m = "male";
        } else {
          m = "female";
        }


        ArrayList<String> modifiedContent = new ArrayList<String>();
        String file = new String();

        if (flag == 0) {
          file = System.getProperty("user.dir") + "/data/student.txt";
        } else if(flag == 1){
          file = System.getProperty("user.dir") + "/data/teacher.txt";
        }else if(flag == 3){
          file = System.getProperty("user.dir") + "/data/administrator.txt";
        }
        try {
          BufferedReader br = new BufferedReader(
              new FileReader(file));

          String s = null;
          while ((s = br.readLine()) != null) {
            String[] result = s.split(" ");
            if (result[0].equals(id)) {
              result[0] = result[0]; // 号码不能更改

              if(!pass2t.getText().equals(""))
                result[1] = result[1].replace(result[1],
                    pass2t.getText());
              result[2] = result[2].replace(result[2],
                  namet.getText());
              result[3] = result[3].replace(result[3], m);
              result[4] = result[4].replace(result[4],
                  birtht.getText());
              result[5] = result[5].replace(result[5],
                  instt.getText());
              result[6] = result[6].replace(result[6],
                  majort.getText());

            }
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


        JOptionPane.showMessageDialog(null, "修改成功！", "提示",
            JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
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
