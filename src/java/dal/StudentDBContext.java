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
import model.Attendance;
import model.Course;
import model.Student;

/**
 *
 * @author Tebellum
 */
public class StudentDBContext extends DBConnect {

    public ArrayList<Student> getStudent(Course c) {
        ArrayList<Student> students = new ArrayList<>();
        try {
            String sql = "SELECT * FROM student";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt(1));
                s.setName(rs.getString(2));
                ArrayList<Attendance> a = new ArrayList<>();
                a.add(getAttendanceBySid(s.getId(), c.getcID()));
                s.setAttendances(a);
                students.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return students;
    }

    private Attendance getAttendanceBySid(int sid, String cid) {
        try {
            String sql = "SELECT * from Attendance\n"
                    + "where [sid] = ? AND cid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, sid);
            stm.setString(2, cid);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Attendance a = new Attendance();
                a.setSid(sid);
                a.setCid(cid);
                a.setPresent(rs.getBoolean(3));
                return a;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

//    public static void main(String[] args) {
//        Course c = new Course();
//        c.setDate(Date.valueOf("2021-11-02"));
//        c.setcID("JPD123");
//        StudentDBContext sdb = new StudentDBContext();
//        for (Student s : sdb.getStudentAttendsByDate(c)) {
//            System.out.println(s.getId() + " " + s.getName());
//        }
//    }
}
