package com.patikadev.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static int screenCenter(String axis, Dimension size) {

        int point = 0;
        switch (axis) {
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
        }
        return point;
    }

    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());

                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static boolean isFieldEmpty(JTextField textField) {
        return textField.getText().trim().isEmpty();
    }

    public static void showMsg(String str) {
        //optionPageTR();
        String msg;
        String title;
        switch (str) {
            case "fill":
                msg = "Fill all texts";
                title = "Empty text";
                break;
            case "done":
                msg = "Transaction successful!";
                title = "Success";
                break;
            case "error":
                msg = "Transaction unsuccessful!";
                title = "Error";
                break;
            case "delete":
                msg = "Delete successful!";
                title = "User Deleted";
                break;
            case "update":
                msg = "Update successful!";
                title = "User Updated";
                break;
            case "Invalid Type":
                msg = "Please change the membership type! (Available types : student, educator, operator)";
                title = "Invalid Type";
                break;
            default:
                title = "Message";
                msg = str;
                break;

        }
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);

    }

    public static boolean confirm(String str){
        String msg;
        switch (str){
            case "sure":
                msg = "Are you sure you want to perform this operation?";
                break;
            default:
                msg = str;
        }
        return  JOptionPane.showConfirmDialog(null,msg,"Are you sure?", JOptionPane.YES_NO_OPTION) == 0;
    }

    @Deprecated
    public static void optionPageTR() {
        UIManager.put("OptionPane.okButtonText", "Ok");
    }

}
