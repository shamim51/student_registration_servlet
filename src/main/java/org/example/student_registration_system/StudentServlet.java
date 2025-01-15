package org.example.student_registration_system;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.student_registration_system.models.Student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/register")
public class StudentServlet extends HttpServlet {
    public static final List<Student> students = new ArrayList<>();

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{

        PrintWriter out = res.getWriter();

        /*
        painting html
        for taking student info
         */
        out.println("<html>");
        out.println("<h2>Student Registration</h2>");
        out.println("<form action=\"register\" method=\"post\">");
        out.println("Name: <input type=\"text\" name=\"name\" required><br>");
        out.println("Email: <input type=\"email\" name=\"email\" required><br>");
        out.println("Course:");
        out.println("<select name=\"course\">");
        out.println(" <option value=\"Java\">Java</option>");
        out.println("<option value=\"Python\">Python</option>");
        out.println(" <option value=\"JavaScript\">JavaScript</option>");
        out.println(" </select><br>");
        out.println("<input type=\"submit\" value=\"Register\">");
        out.println(" </form>");
        out.println("</body>");
        out.println("</html>");


    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String course = req.getParameter("course");


        /*
        inside condition,
         1. we are checking for null input,
         if user modify field name from form,
         we will get null value

         2. we are checking that no field is empty

         3. we are checking that user don't put
          anything other than we specify by modifying
          html
         */

        if(
                (name == null || email == null || course == null) ||

                (name.isEmpty() || email.isEmpty() || course.isEmpty()) ||

                (!(course.equals("Java") || course.equals("Python") || course.equals("JavaScript")))


        ){

            /*
                if anything wrong found redirect the user
                to error page
             */
            res.sendRedirect("error.jsp");

            /*
            and return from here otherwise if the user mail
            is also invalid next if block will execute and
            redirect which will create an error, because we cant
            another redirect after calling one;
             */
            return;
        }

        //copied from chatgpt
        // Regex for a valid email address
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

        // Compile the regex
        Pattern pattern = Pattern.compile(emailRegex);

        // Match the email string against the regex
        Matcher matcher = pattern.matcher(email);

        //if the email is in invalid format redirect to error page
        if(!matcher.matches()){
            System.out.println("not a valid email");
            res.sendRedirect("error.jsp");
        }


        /*
        if every check passes, then creat a new user.
         */
        students.add(new Student(name, email, course));
    }

    public static List<Student> getStudentList(){
        return students;
    }
}
