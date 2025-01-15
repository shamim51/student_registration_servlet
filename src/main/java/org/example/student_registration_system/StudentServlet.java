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
        //res.getWriter().println("hello");

        if(
                (name == null || email == null || course == null) ||

                (name.isEmpty() || email.isEmpty() || course.isEmpty()) ||

                (!(course.equals("Java") || course.equals("Python") || course.equals("JavaScript")))


        ){

            /*
                if anything wrong found redirect the user
                to error page
             */
            processError(req,res,true);

            /*
            and return from here otherwise if the user mail
            is also invalid next if block will execute and
            we don't want that;
             */
            return;
        }


        /*
        Regex for a valid email address
        copied from chatgpt
         */

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

        // Compile the regex
        Pattern pattern = Pattern.compile(emailRegex);

        // Match the email string against the regex
        Matcher matcher = pattern.matcher(email);

        //if the email is in invalid format redirect to error page
        if(!matcher.matches()){
            System.out.println("not a valid email");
            processError(req, res, false);
            return;

        }


        /*
        if every check passes, then creat a new user.
         */
        students.add(new Student(name, email, course));

        PrintWriter out = res.getWriter();

        //a nice confirmation page

        res.setContentType("text/html");
        out.write("<html><head><title>Exception/Error Details</title></head><body>");
        out.write("<h1>student <strong style=\"color:blue\">"+name+"</strong> added successfully</h1>");
        out.write("</body></html>");

    }
    public static List<Student> getStudentList(){
        return students;
    }

    public void processError(HttpServletRequest req, HttpServletResponse res, boolean match) throws IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String course = req.getParameter("course");

        res.setContentType("text/html");

        PrintWriter out = res.getWriter();
        out.write("<html><head><title>Exception/Error Details</title></head><body>");

        /*
        if email is in invalid formal
        so in doPost function we check for format
        and we if we find that the email is in invalid format
        we call processError method with true value in 3rd parameter
        which indicate this function to execute the first if.
         */

        if(!match) {
            out.write("<h3>Error Details</h3>");
            out.write("<strong style=\"color:red\">Please Enter a Valid Email</strong>:<br>");

        }

        /*
        then we check for modified field names
        if user modifies any of those field we
        get a null value for the corresponding variable
         */
        else if (name == null || email == null || course == null) {
            out.write("<h3>Error Details</h3>");
            out.write("<strong style=\"color:red\">please don't modify field name</strong>:<br>");
            out.write("<strong style=\"color:blue\">try again with unmodified field</strong>:");

        }

        /*
        here we check for empty value of any field
        so we are checking for empty value in out html form
        but user can easily bypass this check.

        so this check is also necessary
         */
        else if(name.isEmpty() || email.isEmpty() || course.isEmpty()) {
            out.write("<h3>Error Details</h3>");
            out.write("<strong style=\"color:red\">you can't keep a field empty</strong>:<br>");
            out.write("<strong style=\"color:blue\">please enter all the necessary inputs</strong>");
        }

        /*
        same here for the option value
        there might be some manipulate with option value
         */
        else if (!(course.equals("Java") || course.equals("Python") || course.equals("JavaScript"))) {
            out.write("<h3>Error Details</h3>");
            out.write("<strong style=\"color:red\">try with unmodified option field</strong>:<br>");
        }

        out.write("<br><br>");
        out.println("<a href=\"register\">go to registration page</a>");
        out.write("</body></html>");
    }

}
