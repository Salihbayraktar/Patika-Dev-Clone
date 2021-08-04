package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class NewStudentGUI extends JFrame{


    private JTextField fld_student_name;
    private JTextField fld_student_user_name;
    private JPasswordField fld_student_password;
    private JPasswordField fld_student_password_again;
    private JButton btn_student_register;
    private JPanel wrapper;

    public NewStudentGUI() {
        add(wrapper);
        setSize(330,300);
        setLocation(Helper.screenCenter("x", getSize()),Helper.screenCenter("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.STUDENT_REGISTER_TITLE);
        setVisible(true);

        btn_student_register.addActionListener(e -> {
            String password = new String(fld_student_password.getPassword());
            String passwordAgain = new String(fld_student_password_again.getPassword());
            if(Helper.isFieldEmpty(fld_student_name) || Helper.isFieldEmpty(fld_student_user_name) || Helper.isFieldEmpty(fld_student_password) || Helper.isFieldEmpty(fld_student_password_again)){
                Helper.showMsg("fill");
            } else if (User.getFetch(fld_student_user_name.getText()) != null){
                Helper.showMsg("Username has already been added.");
            } else if (!password.equals(passwordAgain)) {
                Helper.showMsg("Password fields do not match.");
            } else {
                //kayıt et
                User.add(fld_student_name.getText(), fld_student_user_name.getText(), password, "student");
                //Helper.showMsg("Successfully Registered");
                dispose();
            }
        });


    }
}
