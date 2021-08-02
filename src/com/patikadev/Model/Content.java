package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Content {
    int id;
    int courseId;
    int educatorId;
    String title;
    String explanation;
    String youtubeLink;
    String quizQuestions;
    Course course;

    public Content() {
    }

    public Content(int id, int courseId, int educatorId, String title, String explanation, String youtubeLink, String quizQuestions) {
        this.id = id;
        this.courseId = courseId;
        this.educatorId = educatorId;
        this.title = title;
        this.explanation = explanation;
        this.youtubeLink = youtubeLink;
        this.quizQuestions = quizQuestions;
    }

    public static ArrayList<Content> getContentsByUserId(User user) {
        ArrayList<Content> contents = new ArrayList<>();
        String query = "SELECT * FROM content WHERE educator_id = " + user.getId();
        Content obj;
        try (Statement st = DBConnector.getInstance().createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                obj = new Content();
                obj.setId(rs.getInt("id"));
                obj.setCourseId(rs.getInt("course_id"));
                obj.setEducatorId(rs.getInt("educator_id"));
                obj.setTitle(rs.getString("title"));
                obj.setExplanation(rs.getString("explanation"));
                obj.setYoutubeLink(rs.getString("youtube_link"));
                obj.setQuizQuestions(rs.getString("quiz_questions"));
                //System.out.println(rs.getString("title"));
                //System.out.println(obj.getTitle());
                contents.add(obj);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return contents;
    }

    public static ArrayList<Content> getFilteredContents(User user, String courseName, String contentTitle) {
        ArrayList<Content> contents = new ArrayList<>();
        String query = "";
        if ("All".equals(courseName) && "All".equals(contentTitle)) {
            return getContentsByUserId(user);
        } else if ("All".equals(courseName)) {
            System.out.println("All coursename e eşit");
            query = "SELECT * FROM content WHERE title = '" + contentTitle + "'";
        } else if ("All".equals(contentTitle)) {
            System.out.println("All contenttitle a eşit");
            int courseId = Course.getFetch(courseName).getId();
            query = "SELECT * FROM content WHERE course_id = " + courseId;
        } else {
            System.out.println("hepsi alldan farklı");
            int courseId = Course.getFetch(courseName).getId();
            query = "SELECT * FROM content WHERE course_id = " + courseId + " AND title = '" + contentTitle + "'";
        }
        Content obj;
        System.out.println(query);
        //query = "SELECT * FROM content";
        try (Statement st = DBConnector.getInstance().createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("While a girdi");
                obj = new Content();
                obj.setId(rs.getInt("id"));
                obj.setCourseId(rs.getInt("course_id"));
                obj.setEducatorId(rs.getInt("educator_id"));
                obj.setTitle(rs.getString("title"));
                obj.setExplanation(rs.getString("explanation"));
                obj.setYoutubeLink(rs.getString("youtube_link"));
                obj.setQuizQuestions(rs.getString("quiz_questions"));
                //System.out.println(rs.getString("title"));
                //System.out.println(obj.getTitle());
                contents.add(obj);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return contents;
    }

    public static ArrayList<Course> getDistinctCourses(User user) {
        String query = "SELECT DISTINCT(course_id)  FROM content WHERE educator_id = " + user.getId();
        ArrayList<Course> courses = new ArrayList<>();

        Course obj;
        try (Statement st = DBConnector.getInstance().createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                obj = Course.getFetch(rs.getInt("course_id"));
                /*obj.setId(rs.getInt("id"));
                obj.setCourseId(rs.getInt("course_id"));
                obj.setEducatorId(rs.getInt("educator_id"));
                obj.setTitle(rs.getString("title"));
                obj.setExplanation(rs.getString("explanation"));
                obj.setYoutubeLink(rs.getString("youtube_link"));
                obj.setQuizQuestions(rs.getString("quiz_questions"));
                System.out.println(rs.getString("title"));
                System.out.println(obj.getTitle());*/
                courses.add(obj);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return courses;
    }

    public static boolean update(int id, String title, String explanation, String youtubeLink, String quizQuestions) {
        String query = "UPDATE content SET title = ?, explanation = ?, youtube_link = ?, quiz_questions = ? WHERE id = " + id;
        boolean result = false;
        try (PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, explanation);
            pstmt.setString(3, youtubeLink);
            pstmt.setString(4, quizQuestions);
            //pstmt.setString(5,quizQuestions);
            result = pstmt.executeUpdate() != -1;

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

    public static boolean addContent(String courseName, User user, String title, String explanation, String youtubeLink, String quizQuestions) {
        int courseId = Course.getFetch(courseName).getId();
        int educatorId = user.getId();
        boolean result = false;
        String query = "INSERT INTO content (course_id, educator_id, title, explanation, youtube_link, quiz_questions) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query)) {

            pstmt.setInt(1, courseId);
            pstmt.setInt(2, educatorId);
            pstmt.setString(3, title);
            pstmt.setString(4, explanation);
            pstmt.setString(5, youtubeLink);
            pstmt.setString(6, quizQuestions);

            result = pstmt.executeUpdate() != -1;

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

    //Content.getDistinctTitles
    public static ArrayList<String> getDistinctTitles(User user, String courseName) {
        //coursename e göre hepsini getir
        ArrayList<String> titles = new ArrayList<>();
        String query = "";
        if ("All".equals(courseName)) {
            query = "SELECT DISTINCT(title) FROM content WHERE educator_id = " + user.getId();
        } else {
            int courseId = Course.getFetch(courseName).getId();
            query = "SELECT DISTINCT(title) FROM content WHERE course_id = " + courseId;
        }

        try (Statement st = DBConnector.getInstance().createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                String title = rs.getString("title");
                //System.out.println(title);
                titles.add(title);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return titles;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM content WHERE id = " + id;
        boolean result = false;
        try (PreparedStatement pstmt = DBConnector.getInstance().prepareStatement(query)) {
            result = pstmt.executeUpdate() != -1;
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(String quizQuestions) {
        this.quizQuestions = quizQuestions;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getEducatorId() {
        return educatorId;
    }

    public void setEducatorId(int educatorId) {
        this.educatorId = educatorId;
    }
}
