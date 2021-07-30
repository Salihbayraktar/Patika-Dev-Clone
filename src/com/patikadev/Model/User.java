package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String userName;
    private String password;
    private String type;

    public User() {
    }

    public User(int id, String name, String userName, String password, String type) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.type = type;
    }

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

    public static ArrayList<User> getEducatorList() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user WHERE type = 'educator'";
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

    public static boolean update(User user) {

        if (!(user.getType().equals("student") || user.getType().equals("educator") || user.getType().equals("operator"))) {
            Helper.showMsg("Invalid Type");
            return false;
        }

        User findUser = getFetch(user.getUserName());
        if (findUser != null && findUser.getId() != user.getId()) {
            Helper.showMsg("Username has already been added.");
            return false;
        }
        String query = "UPDATE user SET name = ?, userName = ?, password = ?, type = ? WHERE id = ?";

        boolean result = false;

        try {
            PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getUserName());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getType());
            pstmt.setInt(5, user.getId());
            result = pstmt.executeUpdate() != -1;
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static boolean delete(int id) {

        String query = "DELETE FROM user WHERE (id) IN(?)";
        boolean result = false;
        try {
            PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query);
            pstmt.setInt(1, id);

            result = pstmt.executeUpdate() != -1;
            pstmt.close();
            System.out.println("User Deleted Where Id : " + id);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    public static boolean add(String name, String userName, String password, String type) {

        if (getFetch(userName) != null) {
            Helper.showMsg("Username has already been added.");
            return false;
        }

        String query = "INSERT INTO user(name,userName,password,type) VALUES(?,?,?,?)";
        boolean result = false;

        try {
            PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, userName);
            pstmt.setString(3, password);
            pstmt.setString(4, type);

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

    public static ArrayList<User> searchUserList(String name, String userName, String type) {

        ArrayList<User> searchedUserList = new ArrayList<>();
        User obj;

        String query = "SELECT * FROM user WHERE name LIKE ? AND userName LIKE ? AND type LIKE ?";

        try {
            PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query);
            pstmt.setString(1, '%' + name + '%');
            pstmt.setString(2, '%' + userName + '%');
            pstmt.setString(3, '%' + type + '%');
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUserName(rs.getString("userName"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
                searchedUserList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return searchedUserList;
    }

    public static User getFetch(String uname) {
        User obj = null;
        String query = "SELECT * FROM user WHERE userName = ?";

        try {
            PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query);
            pstmt.setString(1, uname);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUserName(rs.getString("userName"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
            }
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public static User getFetch(int id) {
        User obj = null;
        String query = "SELECT * FROM user WHERE id = ?";

        try {
            PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUserName(rs.getString("userName"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
            }
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public static User getFetch(String uname, String password) {
        User obj = null;
        String query = "SELECT * FROM user WHERE userName = ? AND password = ?";

        try {
            PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query);
            pstmt.setString(1, uname);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUserName(rs.getString("userName"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
            }
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
