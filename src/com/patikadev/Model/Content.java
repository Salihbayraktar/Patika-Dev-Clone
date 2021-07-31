package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.net.URL;
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

    public static ArrayList<Content> getContentsByUserId(User user){
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
                System.out.println(rs.getString("title"));
                System.out.println(obj.getTitle());
                contents.add(obj);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return contents;
    }

    public static ArrayList<Course> getDistinctCourses(User user){
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
