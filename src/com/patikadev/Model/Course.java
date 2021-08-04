package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.*;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String lang;

    private Patika patika;
    private User educator;

    public Course() {

    }

    public Course(int id, int user_id, int patika_id, String name, String lang) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.lang = lang;
        this.patika = Patika.getFetch(patika_id);
        this.educator = User.getFetch(user_id);
    }

    public static ArrayList<Course> getList() {
        ArrayList<Course> courses = new ArrayList<>();

        String query = "SELECT * FROM course";

        try {
            PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String lang = rs.getString("lang");

                Course course = new Course(id, user_id, patika_id, name, lang);
                courses.add(course);
                //System.out.println(rs.getString("lang"));
            }
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return courses;
    }

    public static boolean add(int user_id, int patika_id, String name, String lang) {

        if (getFetch(name) != null) {
            Helper.showMsg("Course name has already been added.");
            return false;
        }

        String query = "INSERT INTO course (user_id, patika_id, name, lang) VALUES (?, ?, ?, ?)";
        boolean result = false;
        try {
            PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query);
            pstmt.setInt(1, user_id);
            pstmt.setInt(2, patika_id);
            pstmt.setString(3, name);
            pstmt.setString(4, lang);
            result = pstmt.executeUpdate() != -1;
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (result) {
            Helper.showMsg("done");
        } else {
            Helper.showMsg("error");
        }

        return result;
    }

    public static boolean deleteAllEducatorCourses(int user_id) {

        String query = "DELETE FROM course WHERE user_id = ?";
        boolean result = false;
        try {
            PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query);
            pstmt.setInt(1, user_id);
            result = pstmt.executeUpdate() != -1;
            pstmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    public static boolean deleteAllPatikasInCourses(int patika_id) {

        String query = "DELETE FROM course WHERE patika_id = ?";
        boolean result = false;
        try {
            PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query);
            pstmt.setInt(1, patika_id);
            result = pstmt.executeUpdate() != -1;
            pstmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    public static Course getFetch(String name) {
        String query = "SELECT * FROM course WHERE name = ?";
        Course obj = null;
        try {
            PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                obj = new Course(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("patika_id"), rs.getString("name"), rs.getString("lang"));
            }
            pstmt.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }

    public static Course getFetch(int id) {
        String query = "SELECT * FROM course WHERE id = ?";
        Course obj = null;
        try {
            PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                obj = new Course(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("patika_id"), rs.getString("name"), rs.getString("lang"));
            }
            pstmt.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }

    public static ArrayList<Course> getCoursesByPatikaId(int patikaId) {
        ArrayList<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM course WHERE patika_id = " + patikaId;
        Course course;
        try (Connection connection = DBConnector.getInstance();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setUser_id(resultSet.getInt("user_id"));
                course.setPatika_id(resultSet.getInt("patika_id"));
                course.setName(resultSet.getString("name"));
                course.setLang(resultSet.getString("lang"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    /*
       public static ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        User obj;
        try (Statement st = DBConnector.getInstance().createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUserName(rs.getString("userName"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
                userList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }
     */
    public static ArrayList<Course> getEducatorCourses(User u) {
        ArrayList<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM course WHERE user_id = " + u.getId();
        Course obj;
        try (Statement st = DBConnector.getInstance().createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                obj = new Course();
                obj.setId(rs.getInt("id"));
                obj.setUser_id(rs.getInt("user_id"));
                obj.setPatika_id(rs.getInt("patika_id"));
                obj.setName(rs.getString("name"));
                obj.setLang(rs.getString("lang"));
                courses.add(obj);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return courses;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }
}
