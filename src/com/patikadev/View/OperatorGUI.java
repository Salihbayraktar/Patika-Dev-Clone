package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Course;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Patika;
import com.patikadev.Model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class OperatorGUI extends JFrame {
    private final User user;
    private final DefaultTableModel mdl_user_list;
    private final Object[] row_user_list;
    private final DefaultTableModel mdl_patika_list;
    private final Object[] row_patika_list;
    private final JPopupMenu patikaMenu;
    private final DefaultTableModel mdl_course_list;
    private final Object[] row_course_list;
    private JPanel wrapper;
    private JTabbedPane pnl_patika_list;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JPanel pnl_top;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JTextField fld_user_password;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JButton btn_user_delete;
    private JTextField fld_search_user_name;
    private JTextField fld_search_user_uname;
    private JComboBox cmb_search_user_type;
    private JButton btn_user_search;
    private JScrollPane scrl_patika_list;
    private JTable tbl_patika_list;
    private JPanel pnl_patika_add;
    private JTextField fld_patika_name;
    private JButton btn_patika_add;
    private JPanel pnl_course_list;
    private JPanel pnl_patika;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_add;
    private JTextField fld_course_name;
    private JTextField fld_course_lang;
    private JComboBox<Item> cmb_course_patika;
    private JComboBox<Item> cmb_course_educator;
    private JButton btn_course_add;
    private JPanel pnl_search_form;
    private User selectedUser;

    public OperatorGUI(User user) {
        this.user = user;


        //System.out.println(UIManager.getLookAndFeel().getName());
        add(wrapper);
        setSize(1000, 500);
        int x = Helper.screenCenter("x", getSize());
        int y = Helper.screenCenter("y", getSize());
        setLocation(x, y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Welcome : " + user.getName());


        // ModelUserList
        mdl_user_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list = {"ID", "Name Surname ", "User Name", "Password", "Membership Type"};
        mdl_user_list.setColumnIdentifiers(col_user_list);

        row_user_list = new Object[col_user_list.length];


        loadUserModel();

        tbl_user_list.setModel(mdl_user_list);

        tbl_user_list.getTableHeader().setReorderingAllowed(false);


        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                updateSelectedUser();
                if (User.update(this.selectedUser)){
                    loadUserModel();
                    loadCourseEducatorCmb();
                    loadCourseModel();
                    Helper.showMsg("done");
                } else {
                    loadUserModel();
                   // Helper.showMsg("error");
                }

            }
        });

        // PatikaList
        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Update");
        JMenuItem deleteMenu = new JMenuItem("Delete");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
            UpdatePatikaGUI updateGUI = new UpdatePatikaGUI(Patika.getFetch(select_id));
            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadCoursePatikaCmb();
                    loadCourseModel();

                }
            });
        });

        deleteMenu.addActionListener(e -> {

            if (Helper.confirm("sure")) {
                int select_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
                //System.out.println("select id" + select_id);
                Course.deleteAllPatikasInCourses(select_id);
                Patika.delete(Patika.getFetch(select_id).getId());
                loadPatikaModel();
                loadCoursePatikaCmb();
                loadCourseModel();
            }
        });

        mdl_patika_list = new DefaultTableModel();
        Object[] col_patika_list = {"ID", "Patika Name"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];
        loadPatikaModel();
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.setModel(mdl_patika_list);
        //JPopupMenu.setDefaultLightWeightPopupEnabled( false );
        tbl_patika_list.setComponentPopupMenu(patikaMenu);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(50);

        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_patika_list.rowAtPoint(point);
                tbl_patika_list.setRowSelectionInterval(selected_row, selected_row);
                super.mousePressed(e);
            }
        });
        // ## PatikaList

        // CourseList
        mdl_course_list = new DefaultTableModel();
        Object[] col_courseList = {"ID", "Course Name", "Programming Language", "Patika", "Educator"};
        mdl_course_list.setColumnIdentifiers(col_courseList);
        row_course_list = new Object[col_courseList.length];
        loadCourseModel();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(50);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);

        loadCoursePatikaCmb();
        loadCourseEducatorCmb();
        // ## CourseList


        btn_user_add.addActionListener(e -> {
            if ("".equals(fld_user_name.getText()) || "".equals(fld_user_uname.getText()) || "".equals(fld_user_password.getText())) {
                Helper.showMsg("fill");

            } else {

                if (User.add(fld_user_name.getText(), fld_user_uname.getText(), fld_user_password.getText(), Objects.requireNonNull(cmb_user_type.getSelectedItem()).toString())) {
                    loadUserModel();
                    loadCourseEducatorCmb();
                    fld_user_name.setText(null);
                    fld_user_uname.setText(null);
                    fld_user_password.setText(null);
                    cmb_user_type.setSelectedIndex(0);
                }
            }
        });

        tbl_user_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateSelectedUser();
                super.mouseClicked(e);
            }
        });
        btn_user_delete.addActionListener(e -> {

            if (this.selectedUser != null) {
                if (Helper.confirm("sure")) {
                    User.delete(this.selectedUser.getId());
                    Course.deleteAllEducatorCourses(this.selectedUser.getId());
                    this.selectedUser = null;
                    loadUserModel();
                    loadCourseEducatorCmb();
                    loadCourseModel();

                    Helper.showMsg("done");

                }
            } else {
                Helper.showMsg("error");
            }
        });

        btn_user_search.addActionListener(e -> {
            ArrayList<User> searchedList = User.searchUserList(fld_search_user_name.getText(), fld_search_user_uname.getText(), Objects.requireNonNull(cmb_search_user_type.getSelectedItem()).toString());
            loadUserModel(searchedList);
        });

        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI = new LoginGUI();
        });
        btn_patika_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_patika_name)) {
                Helper.showMsg("fill");
            } else {
                Patika patika = new Patika();
                patika.setName(fld_patika_name.getText());
                Patika.Add(patika);
                loadPatikaModel();
                loadCoursePatikaCmb();
                fld_patika_name.setText(null);
            }
        });

        btn_course_add.addActionListener(e -> {
            Item patikaItem = (Item) cmb_course_patika.getSelectedItem();
            Item userItem = (Item) cmb_course_educator.getSelectedItem();
            if (Helper.isFieldEmpty(fld_course_name) || Helper.isFieldEmpty(fld_course_lang)){
                Helper.showMsg("fill");
            } else {
                Course.add(userItem.getKey(), patikaItem.getKey(), fld_course_name.getText(), fld_course_lang.getText());
                loadCourseModel();
                fld_course_name.setText(null);
                fld_course_lang.setText(null);
            }
        });

    }

    public static void main(String[] args) {
        Helper.setLayout();
        Operator op = new Operator();
        op.setId(1);
        op.setName("Salih BAYRAKTAR");
        op.setPassword("1234");
        op.setType("operator");
        op.setUserName("salihbayraktar");

        OperatorGUI opGUI = new OperatorGUI(op);

    }

    public void loadCoursePatikaCmb() {
        cmb_course_patika.removeAllItems();
        ArrayList<Patika> patikas = Patika.getList();
        for (Patika patika : patikas) {
            cmb_course_patika.addItem(new Item(patika.getId(), patika.getName()));
        }
    }

    public void loadCourseEducatorCmb() {
        cmb_course_educator.removeAllItems();
        ArrayList<User> users = User.getList();
        for (User user : users) {
            if (user.getType().equals("educator")) {
                cmb_course_educator.addItem(new Item(user.getId(), user.getName()));
            }
        }
    }

    public void updateSelectedUser() {
        int selectedRow = tbl_user_list.getSelectedRow();
        this.selectedUser = new User();
        this.selectedUser.setId(Integer.parseInt(tbl_user_list.getValueAt(selectedRow, 0).toString()));
        this.selectedUser.setName(tbl_user_list.getValueAt(selectedRow, 1).toString());
        this.selectedUser.setUserName(tbl_user_list.getValueAt(selectedRow, 2).toString());
        this.selectedUser.setPassword(tbl_user_list.getValueAt(selectedRow, 3).toString());
        this.selectedUser.setType(tbl_user_list.getValueAt(selectedRow, 4).toString());
    }

    private void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);

        for (Course obj : Course.getList()) {
            int i = 0;
            row_course_list[i++] = obj.getId();
            row_course_list[i++] = obj.getName();
            row_course_list[i++] = obj.getLang();
            row_course_list[i++] = obj.getPatika().getName();
            row_course_list[i] = obj.getEducator().getName();
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
            row_patika_list[i++] = obj.getName();
            mdl_patika_list.addRow(row_patika_list);

        }
    }

    public void loadUserModel() {

        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (User user : User.getList()) {
            i = 0;
            row_user_list[i++] = user.getId();
            row_user_list[i++] = user.getName();
            row_user_list[i++] = user.getUserName();
            row_user_list[i++] = user.getPassword();
            row_user_list[i] = user.getType();

            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadUserModel(ArrayList<User> userList) {

        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        for (User user : userList) {
            int i = 0;
            row_user_list[i++] = user.getId();
            row_user_list[i++] = user.getName();
            row_user_list[i++] = user.getUserName();
            row_user_list[i++] = user.getPassword();
            row_user_list[i] = user.getType();

            mdl_user_list.addRow(row_user_list);
        }
    }


}
