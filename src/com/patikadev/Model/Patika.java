package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private int id;
    private String name;

    public Patika() {
    }

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ArrayList<Patika> getList() {
        ArrayList<Patika> patikaList = new ArrayList<>();
        Patika obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patika");
            while (rs.next()) {
                obj = new Patika(rs.getInt("id"), rs.getString("name"));
                patikaList.add(obj);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return patikaList;
    }

    public static void Add(Patika patika) {

        if(getFetch(patika.getId()) != null){
            Helper.showMsg("Patika name has already been added.");
            return;
        }

        String query = "INSERT INTO patika (name) VALUES (?)";
        boolean result = false;
        try {
            PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query);
            pstmt.setString(1, patika.getName());
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


    }

    public static Patika getFetch(int id) {
        String query = "SELECT * FROM patika WHERE id = ?";
        Patika fetchPatika = null;
        try {
            PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                fetchPatika = new Patika();
                fetchPatika.setId(rs.getInt("id"));
                fetchPatika.setName(rs.getString("name"));
            }
            pstmt.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return fetchPatika;
    }

    public static boolean update(int id, String name){
        String query = "UPDATE patika SET name = ? WHERE id = ?";
        boolean result = false;
        PreparedStatement pstmt;

        if(getFetch(id) != null && getFetch(id).getName().equals(name)){
            Helper.showMsg("Patika name has already been added.");
            return false;
        }
        try {
            pstmt = DBConnector.getInstance().prepareStatement(query);
            pstmt.setString(1,name);
            pstmt.setInt(2,id);
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

    public static boolean delete(int id){
        String query = "DELETE FROM patika WHERE id = ?";
        boolean result = false;
        try {
            PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query);
            pstmt.setInt(1,id);
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
}
