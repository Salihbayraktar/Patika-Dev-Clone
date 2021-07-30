package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;

import javax.swing.*;
import java.awt.*;

public class EducatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane educator_tabbed_pane;
    private JTable table1;

    public EducatorGUI(){
            add(wrapper);
            setSize(250, 350);
            setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setTitle(Config.PROJECT_TITLE);
            setResizable(false);
            setVisible(true);
    }
}



