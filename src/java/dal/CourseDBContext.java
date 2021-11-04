/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;

/**
 *
 * @author Tebellum
 */
public class CourseDBContext extends DBConnect {

       public ArrayList<Course> getCourses() {
              ArrayList<Course> courses = new ArrayList<>();
              String sql = "SELECT [cid]\n"
                      + "      ,[date]\n"
                      + "  FROM [Course]";

              try {
                     PreparedStatement stm = connection.prepareStatement(sql);
                     ResultSet rs = stm.executeQuery();

                     while (rs.next()) {
                            Course course = new Course();
                            course.setcID(rs.getString("cid"));
                            course.setDate(rs.getDate("date"));
                            courses.add(course);
                     }
              } catch (SQLException ex) {
                     Logger.getLogger(CourseDBContext.class.getName()).log(Level.SEVERE, null, ex);
              }
              return courses;
       }

       public Course getCourseById(String cid) {
              Course course = new Course();
              String sql = "SELECT [cid]\n"
                      + "      ,[date]\n"
                      + "  FROM [Course]"
                      + "  WHERE cid = ?";
              try {
                     PreparedStatement stm = connection.prepareStatement(sql);
                     stm.setString(1, cid);
                     ResultSet rs = stm.executeQuery();
                     if (rs.next()) {
                            course.setcID(rs.getString("cid"));
                            course.setDate(rs.getDate("date"));
                     }
              } catch (SQLException ex) {
                     Logger.getLogger(CourseDBContext.class.getName()).log(Level.SEVERE, null, ex);
              }
              return course;
       }

       public static void main(String[] args) {
              CourseDBContext cdb = new CourseDBContext();
              for (Course course : cdb.getCourses()) {
                     System.out.println(course.getcID() + " " + course.getDate());
              }
              System.out.println(cdb.getCourseById("JPD123").getcID());
       }
}
