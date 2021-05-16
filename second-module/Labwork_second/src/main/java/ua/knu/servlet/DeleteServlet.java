package ua.knu.servlet;

import lombok.SneakyThrows;
import lombok.val;
import ua.knu.persistence.ConnectionFactory;
import ua.knu.persistence.model.Group;
import ua.knu.persistence.model.Student;
import ua.knu.persistence.repository.GroupRepositoryImpl;
import ua.knu.persistence.repository.Repository;
import ua.knu.persistence.repository.StudentRepositoryImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static ua.knu.util.Constants.Group.GROUP_ID;
import static ua.knu.util.Constants.Other.INDEX;
import static ua.knu.util.Constants.Student.STUDENT_ID;

@WebServlet(name = "DeleteServlet", value = "/delete")
public class DeleteServlet extends HttpServlet {

    private final Repository<Student> studentRepository;
    private final Repository<Group> groupRepository;

    public DeleteServlet() {
        this.studentRepository = new StudentRepositoryImpl(ConnectionFactory.getConnection());
        this.groupRepository = new GroupRepositoryImpl(ConnectionFactory.getConnection());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        val studentId = Optional.ofNullable(req.getParameter(STUDENT_ID));
        val groupId = Optional.ofNullable(req.getParameter(GROUP_ID));

        try {
            if (studentId.isPresent()) {
                studentRepository.removeById(Integer.parseInt(studentId.get()));
            } else {
                groupId.ifPresent(s -> groupRepository.removeById(Integer.parseInt(s)));
            }

            req.getRequestDispatcher(INDEX).forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public void destroy() {
        studentRepository.close();
        groupRepository.close();
        super.destroy();
    }
}
