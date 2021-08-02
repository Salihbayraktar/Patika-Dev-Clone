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
import java.awt.event.*;
import java.util.ArrayList;

public class EducatorGUI extends JFrame {
    private final DefaultTableModel mdl_educator_course;
    private final DefaultTableModel mdl_educator_content;
    private final Object[] row_course_list;
    private final Object[] row_content_list;
    private Object[] row_content_list2;
    private final User user;
    private DefaultTableModel mdl_content_list;
    private JPopupMenu contentMenu;
    private JPanel wrapper;
    private JTable tbl_course_list;
    private JLabel lbl_educator_welcome;
    private JTable tbl_content_list;
    private JComboBox cmb_content_courses;
    private JComboBox cmb_content_title;
    private JButton btn_content_filter;
    private JButton btn_content_add;
    private JComboBox cmb_educator_courses;
    private JComboBox cmb_educator_contents;
    private JTextField fld_content_title;
    private JTextField fld_content_explanation;
    private JTextField fld_content_youtube_link;
    private JTextField fld_content_quiz_questions;
    private JTextField fld_educator_name;

    public EducatorGUI(User user) {
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

        //--------------------------------------------------------------------------
        // PatikaList
        contentMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Update");
        JMenuItem deleteMenu = new JMenuItem("Delete");
        contentMenu.add(updateMenu);
        contentMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            System.out.println("Update Worked");
            int selectId = Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString());
            int courseId = Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),1).toString());
            //int educatorId = Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),2).toString());
            System.out.println("Select id : " + selectId);
            System.out.println("Course id : " + courseId);
            UpdateContentGUI updateGUI = new UpdateContentGUI(selectId,courseId);
            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    System.out.println("Güncelleme ekrani kapandı");
                    loadContentModel(user);
                    loadContentTitleCmb(user, cmb_content_courses.getSelectedItem().toString());
                    loadEducatorContent(user, cmb_educator_courses.getItemAt(0).toString());
                    //loadContentCoursesCmb();
                    //loadEducatorCourses();

                }
            });
        });

        deleteMenu.addActionListener(e -> {
            System.out.println("Delete Worked");
            if (Helper.confirm("sure")) {
                int selectId = Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString());
                System.out.println("Secili content silindi");
                Content.delete(selectId);
                loadContentModel(user);
                loadContentTitleCmb(user, cmb_content_courses.getSelectedItem().toString());
                loadEducatorContent(user, cmb_educator_courses.getItemAt(0).toString());
                //System.out.println("select id" + select_id);
                //Course.deleteAllPatikasInCourses(select_id);
                //Patika.delete(Patika.getFetch(select_id).getId());
                //loadPatikaModel();
                //loadCoursePatikaCmb();
                // loadCourseModel();
            }
        });

        mdl_content_list = new DefaultTableModel();
        Object[] col_content_list = {"ID", "Course ID", "Title", "Explanation", "Youtube Link", "Quiz Questions"};
        mdl_content_list.setColumnIdentifiers(col_content_list);
        row_content_list2 = new Object[col_content_list.length];
        //loadPatikaModel();
        tbl_content_list.getTableHeader().setReorderingAllowed(false);
        tbl_content_list.setModel(mdl_content_list);
        //JPopupMenu.setDefaultLightWeightPopupEnabled( false );*/
        tbl_content_list.setComponentPopupMenu(contentMenu);
        tbl_content_list.getColumnModel().getColumn(0).setMaxWidth(50);

        tbl_content_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_content_list.rowAtPoint(point);
                tbl_content_list.setRowSelectionInterval(selected_row, selected_row);
                super.mousePressed(e);
            }
        });
        // ## PatikaList
        //--------------------------------------------------------------------------





        mdl_educator_content = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) return false;
                return super.isCellEditable(row, column);
            }
        };
        //Object[] col_content_list = {"ID", "Course ID", "Title", "Explanation", "Youtube Link", "Quiz Questions"};
        mdl_educator_content.setColumnIdentifiers(col_content_list);

        row_content_list = new Object[col_content_list.length];

        loadContentModel(user);

        tbl_content_list.setModel(mdl_educator_content);

        tbl_content_list.getTableHeader().setReorderingAllowed(false);


        //Content End
        loadContentCoursesCmb();
        loadContentTitleCmb(this.user, cmb_content_courses.getSelectedItem().toString());
        loadEducatorCourses();
        loadEducatorContent(user, cmb_educator_courses.getItemAt(0).toString());


        cmb_content_title.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //loadContentTitleCmb(user, cmb_content_courses.getSelectedItem().toString());
                super.mouseClicked(e);
            }
        });

        btn_content_filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(cmb_content_courses.getSelectedItem().toString());
                //System.out.println(cmb_content_courses.getSelectedIndex());
                //System.out.println(cmb_content_courses.toString());
                loadContentModel(user, cmb_content_courses.getSelectedItem().toString(), cmb_content_title.getSelectedItem().toString());
                // Filtrelenmiş listeyi getir
            }
        });
        cmb_educator_contents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println(cmb_educator_courses.getSelectedItem().toString());
                //loadEducatorContent(user, cmb_educator_courses.getSelectedItem().toString());
                super.mouseClicked(e);
            }
        });
        // cmb_content_courses.addMouseListener(new MouseAdapter() {
        // });
        cmb_content_courses.addActionListener(e -> {
            //System.out.println("Action performed cmb_content_courses");
            loadContentTitleCmb(user, cmb_content_courses.getSelectedItem().toString());
        });
        cmb_educator_courses.addActionListener(e -> {
            //System.out.println("Action performed cmb_educator_courses");
            loadEducatorContent(user, cmb_educator_courses.getSelectedItem().toString());
        });
        cmb_educator_contents.addActionListener(e -> {
            fld_content_title.setEnabled(cmb_educator_contents.getSelectedIndex() == 0);
            fld_content_title.setText("");
        });
        btn_content_add.addActionListener(e -> {
            //System.out.println("Buton clicked");
            String title = "";
            if (cmb_educator_contents.getSelectedIndex() == 0) {
                title = fld_content_title.getText();
            } else {
                title = cmb_educator_contents.getSelectedItem().toString();
            }
            if (Content.addContent(cmb_educator_courses.getSelectedItem().toString(), user, title, fld_content_explanation.getText(), fld_content_youtube_link.getText(), fld_content_quiz_questions.getText())) {
                loadContentModel(user);
                loadContentTitleCmb(user, cmb_content_courses.getSelectedItem().toString());
                loadEducatorContent(user, cmb_educator_courses.getItemAt(0).toString());
            }
        });




    }

    public static void main(String[] args) {
        //System.out.println("Start");
        Helper.setLayout();
        //User educator = User.getFetch("merveyildiz");
        User educator = User.getFetch("yucelkara");
        EducatorGUI educatorGUI = new EducatorGUI(educator);
    }

    public void loadEducatorContent(User user, String courseName) {
        cmb_educator_contents.removeAllItems();
        ArrayList<String> titles = Content.getDistinctTitles(user, courseName);
        cmb_educator_contents.addItem(new Item(0, "Add New Title"));
        int i = 1;
        for (String title : titles) {
            cmb_educator_contents.addItem(new Item(i++, title));
        }
    }

    public void loadContentModel(User user) {

        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Content content : Content.getContentsByUserId(user)) {
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

    public void loadContentModel(User user, String courseName, String contentTitle) {

        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Content content : Content.getFilteredContents(user, courseName, contentTitle)) {
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
        cmb_content_courses.addItem(new Item(0, "All"));
        for (Course course : courses) {
            cmb_content_courses.addItem(new Item(course.getId(), course.getName()));
        }
    }

    public void loadEducatorCourses() {
        cmb_educator_courses.removeAllItems();
        ArrayList<Course> courses = Content.getDistinctCourses(this.user);
        for (Course course : courses) {
            cmb_educator_courses.addItem(new Item(course.getId(), course.getName()));
        }
    }

    public void loadContentTitleCmb(User user, String courseName) {
        cmb_content_title.removeAllItems();
        ArrayList<String> titles = Content.getDistinctTitles(user, courseName);
        cmb_content_title.addItem(new Item(0, "All"));
        int i = 1;
        for (String title : titles) {
            cmb_content_title.addItem(new Item(i++, title));
        }
    }
}



