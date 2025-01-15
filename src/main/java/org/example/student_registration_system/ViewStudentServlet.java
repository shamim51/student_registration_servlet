package org.example.student_registration_system;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.student_registration_system.models.Student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/view")
public class ViewStudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        //getting student list from StudentServlet
        List<Student> studentList = StudentServlet.getStudentList();


        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<body>");
        out.println("<h2>Registered Students</h2>");
        out.println("<table border=\"1\">");
        out.println("<tr>");
        out.println("<th>S.No</th>");
        out.println("<th>Name</th>");
        out.println("<th>Email</th>\n");
        out.println("<th>Course</th>");
        out.println("</tr>");

        int serial = 1;

        for (Student student: studentList){
            out.println("<tr>");
            out.println("<th>"+ serial +"</th>");
            out.println("<th>"+student.getName()+"</th>");
            out.println("<th>"+student.getEmail()+"</th>");
            out.println("<th>"+student.getCourse()+"</th>");
            out.println("</tr>");
            serial++;
        }
        out.println("</table>");
        out.println("<a href=\"register\">Register New Student</a>");
        out.println("</body>");
        out.println("</html>");
    }
}
