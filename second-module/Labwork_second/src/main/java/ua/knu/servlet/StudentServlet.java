package ua.knu.servlet;

import lombok.SneakyThrows;
import lombok.val;
import ua.knu.persistence.ConnectionFactory;
import ua.knu.persistence.model.Student;
import ua.knu.persistence.repository.Repository;
import ua.knu.persistence.repository.StudentRepositoryImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.knu.util.Constants.Group.GROUP_ID;
import static ua.knu.util.Constants.Other.INDEX;
import static ua.knu.util.Constants.Student.*;

@WebServlet(name = "studentsServlet", value = "/students")
public class StudentServlet extends HttpServlet {

    private final Repository<Student> studentRepository;

    public StudentServlet() {
        this.studentRepository = new StudentRepositoryImpl(ConnectionFactory.getConnection());
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(STUDENTS, studentRepository.findAll());
        req.setAttribute(STUDENT_TITLE, "Students: ");

        try {
            req.getRequestDispatcher(INDEX).forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            val firstName = req.getParameter(FIRST_NAME);
            val lastName = req.getParameter(LAST_NAME);
            val groupId = Integer.parseInt(req.getParameter(GROUP_ID));

            studentRepository.save(new Student()
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setGroupId(groupId));

            req.getRequestDispatcher(INDEX).forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public void destroy() {
        studentRepository.close();
        super.destroy();
    }
}
