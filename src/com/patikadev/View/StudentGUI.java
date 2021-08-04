package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Patika;
import com.patikadev.Model.User;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class StudentGUI extends JFrame{
    private JPanel wrapper;
    private JLabel fld_student_welcome;
    private JPanel pnl_patika;
    private JPanel pnl_courses;
    //private JTabbedPane pnl_content;
    private JTable tbl_patika_list;
    private JTable tbl_courses_list;
    private JTabbedPane pnl_tabbed;
    private JPanel pnl_content;
    private JTable tbl_content_list;
    private User user;
    private DefaultTableModel mdl_patika_list;
    private DefaultTableModel mdl_course_list;
    private DefaultTableModel mdl_content_list;
    private Object[] row_patika_list;
    private Object[] row_course_list;
    private Object[] row_content_list;
    private Patika selectedPatika;
    private Course selectedCourse;


    public StudentGUI(User user) {
        this.user = user;
        add(wrapper);
        setSize(800, 800);
        setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(true);
        setVisible(true);

        fld_student_welcome.setText("Welcome : " + user.getName());

        //Patika List
        mdl_patika_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column != -1) return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_patika_list = {"ID", "Patika Name"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];

        loadPatikaModel();

        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.setModel(mdl_patika_list);
        //JPopupMenu.setDefaultLightWeightPopupEnabled( false );
        //tbl_patika_list.setComponentPopupMenu(patikaMenu);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(50);
        // Patika List end
        //tbl_patika_list.setColumnSelectionAllowed(false);
        tbl_patika_list.setRowSelectionInterval(0,0);
        int firstPatikaId = Integer.parseInt(tbl_patika_list.getValueAt(0,tbl_patika_list.getSelectedRow()).toString());
        selectedPatika = Patika.getFetch(firstPatikaId);
        //tbl_patika_list.setSelectionMode(0);

        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println(e.getClickCount());
                if(e.getClickCount() >= 2) {
                    //int currentRow = tbl_patika_list.getSelectedRow();
                    int patikaId = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
                    System.out.println(patikaId);
                    selectedPatika = Patika.getFetch(patikaId);
                    loadSelectedPatikasCourses(selectedPatika);

                    pnl_tabbed.setSelectedIndex(1);

                    int firstCourseId = Integer.parseInt(tbl_courses_list.getValueAt(0,0).toString());
                    selectedCourse = Course.getFetch(firstCourseId);

                }

                super.mouseClicked(e);
            }
        });


        // Patika List end


        // Course List

        mdl_course_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column != -1) return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_course_list = {"ID", "User ID", "Patika ID", "Name", "Language"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];

        loadSelectedPatikasCourses(selectedPatika);

        tbl_courses_list.setModel(mdl_course_list);

        // Course List End


        // Content list

        Object[] col_content_list = {"Title", "Explanation", "Youtube Link", "Quiz Questions"};
        mdl_content_list = new DefaultTableModel();
        mdl_content_list.setColumnIdentifiers(col_content_list);
        tbl_content_list.setModel(mdl_content_list);
        row_content_list = new Object[col_content_list.length];


        tbl_courses_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    int selectedId = Integer.parseInt(tbl_courses_list.getValueAt(tbl_courses_list.getSelectedRow(),0).toString());
                    selectedCourse = Course.getFetch(selectedId);
                    loadSelectedCoursesContents(selectedCourse);

                    pnl_tabbed.setSelectedIndex(2);
                }
                super.mouseClicked(e);
            }
        });
    }

    public void loadSelectedCoursesContents(Course course) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Content content : Content.getContentsByCourse(course.getId())){
            i = 0;
            row_content_list[i++] = content.getTitle();
            row_content_list[i++] = content.getExplanation();
            row_content_list[i++] = content.getYoutubeLink();
            row_content_list[i] = content.getQuizQuestions();
            mdl_content_list.addRow(row_content_list);
        }
    }

    public void loadSelectedPatikasCourses(Patika patika) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_courses_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Course course : Course.getCoursesByPatikaId(patika.getId())){
            i = 0;
            row_course_list[i++] = course.getId();
            row_course_list[i++] = course.getUser_id();
            row_course_list[i++] = course.getPatika_id();
            row_course_list[i++] = course.getName();
            row_course_list[i] = course.getLang();
            mdl_course_list.addRow(row_course_list);
        }
    }

    public void loadPatikaModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_patika_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Patika obj : Patika.getList()) {
            i = 0;
            row_patika_list[i++] = obj.getId();
            row_patika_list[i] = obj.getName();
            mdl_patika_list.addRow(row_patika_list);

        }
    }


    public static void main(String[] args) {
        Helper.setLayout();
        User u = User.getFetch("salihogrenci");
        StudentGUI studentGUI = new StudentGUI(u);
    }

}
