package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Patika;
import com.patikadev.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class EducatorGUI extends JFrame {
    private DefaultTableModel mdl_educator_course;
    private DefaultTableModel mdl_educator_content;
    private Object[] row_course_list;
    private Object[] row_content_list;
    private JPanel wrapper;
    private JTabbedPane educator_tabbed_pane;
    private JTable tbl_course_list;
    private JLabel lbl_educator_welcome;
    private JTable tbl_content_list;
    private JComboBox cmb_content_courses;
    private JTextField fld_educator_name;
    private User user;
    public EducatorGUI(User user){
            this.user = user;
            add(wrapper);
            setSize(1000, 700);
            setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setTitle(Config.PROJECT_TITLE);
            setResizable(false);
            setVisible(true);
            lbl_educator_welcome.setText("Welcome : " + user.getName());


            //Course
            mdl_educator_course = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    if (column == 0) return false;
                    return super.isCellEditable(row, column);
                }
            };
            //Object[] col_course_list = {"ID", "Educator Name ", "Patika ID", "Name", "Language"};
            Object[] col_course_list = {"ID", "Patika ID", "Name", "Language"};
            mdl_educator_course.setColumnIdentifiers(col_course_list);

            row_course_list = new Object[col_course_list.length];
    
            loadCourseModel();

            tbl_course_list.setModel(mdl_educator_course);

            tbl_course_list.getTableHeader().setReorderingAllowed(false);

            //Course End


            //Content
            mdl_educator_content = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    if (column == 0) return false;
                    return super.isCellEditable(row, column);
                }
            };
            Object[] col_content_list = {"ID", "Course ID", "Title", "Explanation", "Youtube Link", "Quiz Questions"};
            mdl_educator_content.setColumnIdentifiers(col_content_list);

            row_content_list = new Object[col_content_list.length];

            loadContentModel();

            tbl_content_list.setModel(mdl_educator_content);

            tbl_content_list.getTableHeader().setReorderingAllowed(false);



            //Content End
            loadContentCoursesCmb();



    }

    public void loadContentModel(){

        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Content content : Content.getContentsByUserId(this.user)) {
            //System.out.println(content.getId());
            i = 0;
            row_content_list[i++] = content.getId();
            row_content_list[i++] = content.getCourseId();
            row_content_list[i++] = content.getTitle();
            row_content_list[i++] = content.getExplanation();
            row_content_list[i++] = content.getYoutubeLink();
            row_content_list[i] = content.getQuizQuestions();
            mdl_educator_content.addRow(row_content_list);
        }
    }
    public void loadCourseModel() {

        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Course course : Course.getEducatorCourses(this.user)) {
            i = 0;
            row_course_list[i++] = course.getId();
            //row_course_list[i++] = course.getUser_id();
            row_course_list[i++] = course.getPatika_id();
            row_course_list[i++] = course.getName();
            row_course_list[i] = course.getLang();

            mdl_educator_course.addRow(row_course_list);
        }
    }

    public void loadContentCoursesCmb() {
        cmb_content_courses.removeAllItems();
        ArrayList<Course> courses = Content.getDistinctCourses(this.user);
        cmb_content_courses.addItem(new Item(0,"All"));
        for (Course course : courses) {
            cmb_content_courses.addItem(new Item(course.getId(), course.getName()));
        }
    }


    public static void main(String[] args) {
        System.out.println("asdsad");
        Helper.setLayout();
        User educator = User.getFetch("merveyildiz");
        EducatorGUI educatorGUI = new EducatorGUI(educator);
    }
}



