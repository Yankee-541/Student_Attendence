/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import dal.AttendanceDBContext;
import dal.CourseDBContext;
import dal.StudentDBContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Attendance;
import model.Course;
import model.Student;

/**
 *
 * @author Tebellum
 */
public class AttendanceController extends HttpServlet {

       /**
        * Handles the HTTP <code>GET</code> method.
        *
        * @param request servlet request
        * @param response servlet response
        * @throws ServletException if a servlet-specific error occurs
        * @throws IOException if an I/O error occurs
        */
       @Override
       protected void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
              StudentDBContext sdb = new StudentDBContext();

              CourseDBContext cdb = new CourseDBContext();
              ArrayList<Course> courses = cdb.getCourses();

              String cid = request.getParameter("cid");

              ArrayList<Student> students;
              if (cid != null && !cid.equals("null") && cid.length() != 0) {
                     Course course = cdb.getCourseById(cid);
                     students = sdb.getStudent(course);
                     request.setAttribute("cid", cid);

              } else {
                     students = new ArrayList<>();
              }
              request.setAttribute("students", students);
              request.setAttribute("courses", courses);
              request.getRequestDispatcher("Attendance.jsp").forward(request, response);
       }

       /**
        * Handles the HTTP <code>POST</code> method.
        *
        * @param request servlet request
        * @param response servlet response
        * @throws ServletException if a servlet-specific error occurs
        * @throws IOException if an I/O error occurs
        */
       @Override
       protected void doPost(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
              StudentDBContext sdb = new StudentDBContext();
              Enumeration paramNames = request.getParameterNames();
              String cid = request.getParameter("cid");
              CourseDBContext cdb = new CourseDBContext();
              Course course = cdb.getCourseById(cid);
              ArrayList<Student> students = sdb.getStudent(course);
              ArrayList<Attendance> tmpStudents = new ArrayList<>();
//
              while (paramNames.hasMoreElements()) {
                     String paramName = (String) paramNames.nextElement();
                     String[] paramValues = request.getParameterValues(paramName);
                     if (paramValues[0].compareTo("attend") == 0) {
                            for (Student s : students) {
                                   if (s.getId() == Integer.parseInt(paramName)) {
                                          Attendance a = new Attendance();
                                          a.setCid(cid);
                                          a.setSid(s.getId());
                                          a.setPresent(true);
                                          tmpStudents.add(a);
                                          break;
                                   }
                            }
                     }
              }

              AttendanceDBContext adb = new AttendanceDBContext();
              boolean isSuccess = adb.insertAttendace(tmpStudents);
              response.sendRedirect("at");
       }
       /**
        * Returns a short description of the servlet.
        *
        * @return a String containing servlet description
        */
       @Override
       public String getServletInfo() {
              return "Short description";
       }// </editor-fold>
}
