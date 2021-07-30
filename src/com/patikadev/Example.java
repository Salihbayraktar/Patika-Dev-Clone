package com.patikadev;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Deprecated
public class Example extends JFrame{
    private JPanel wrapper;
    private JPanel wTop;
    private JPanel wBottom;
    private JTextField fld_userName;
    private JPasswordField fld_password;
    private JButton btn_login;

    public Example(){
/*
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
           //System.out.println(info.getName() + "  => " + info.getClassName());
            if("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        setContentPane(wrapper);
        setSize(400,300);
        setTitle(Config.PROJECT_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        int x = Helper.screenCenter("x",getSize());
        int y = Helper.screenCenter("y",getSize());
        setLocation(x,y);
        setVisible(true);
        btn_login.addActionListener(e ->{
            System.out.println("Butona tıklandı");

            if(fld_userName.getText().length() == 0 || fld_password.getPassword().length == 0){
                JOptionPane.showMessageDialog(null,"Tüm alanları doldurun !", "Hata", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Tüm alanları doldurun");
            }
        });
    }

}
