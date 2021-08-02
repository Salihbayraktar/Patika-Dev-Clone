package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Patika;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateContentGUI extends JFrame {
    private JTextField fld_content_title;
    private JPanel pnl_update;
    private JTextField fld_content_explanation;
    private JTextField fld_content_youtube_link;
    private JTextField fld_content_quiz_questions;
    private JButton btn_content_update;
    private JTextField fld_content_id;
    private JTextField fld_content_course_id;
    private JTextField fld_content_course_name;


    public UpdateContentGUI(int contentId, int courseId) {
        //this.patika = patika;
        add(pnl_update);
        setSize(300,500);
        setLocation(Helper.screenCenter("x", getSize()),Helper.screenCenter("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        fld_content_id.setText(String.valueOf(contentId));
        fld_content_course_id.setText(String.valueOf(courseId));
        fld_content_course_name.setText(Course.getFetch(courseId).getName());
        /*
        fld_patika_name.setText(patika.getName());
        btn_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_patika_name)){
                Helper.showMsg("fill");
            } else {
                if(Patika.update(patika.getId(),fld_patika_name.getText())){
                    dispose();

                }
            }

        }); */
        btn_content_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_title) || Helper.isFieldEmpty(fld_content_explanation)){
                Helper.showMsg("fill");
            } else {
                if(Content.update(contentId,fld_content_title.getText(),fld_content_explanation.getText(),fld_content_youtube_link.getText(),fld_content_quiz_questions.getText())){
                    dispose();

                }
            }
        });
    }
}
