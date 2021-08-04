package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JPanel wrapper_top;
    private JPanel wrapper_bottom;
    private JTextField fld_user_uname;
    private JTextField fld_user_pass;
    private JButton btn_login;
    private JButton btn_create_student;

    public LoginGUI() {
        add(wrapper);
        setSize(250, 350);
        setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_uname) || Helper.isFieldEmpty(fld_user_pass)) {
                Helper.showMsg("fill");
            } else {
                User u = User.getFetch(fld_user_uname.getText(),fld_user_pass.getText());
                if (u != null){
                    Helper.showMsg("Welcome " + u.getName());
                    switch (u.getType()){
                        case "operator":
                            OperatorGUI opGUI = new OperatorGUI(u);
                            break;
                        case "educator":
                            EducatorGUI eduGUI = new EducatorGUI(u);
                            break;
                        case "student":
                            StudentGUI stuGUI = new StudentGUI(u);
                            break;
                    }
                    dispose();
                } else {
                    Helper.showMsg("User not found!");
                }
            }
        });
        btn_create_student.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewStudentGUI newStudentGUI = new NewStudentGUI();
            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI login = new LoginGUI();
    }
}
