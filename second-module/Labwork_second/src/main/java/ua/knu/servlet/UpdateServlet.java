package ua.knu.servlet;

import jdk.internal.joptsimple.internal.Strings;
import lombok.NonNull;
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

import static ua.knu.util.Constants.Group.*;
import static ua.knu.util.Constants.Other.ENTITY;
import static ua.knu.util.Constants.Other.INDEX;
import static ua.knu.util.Constants.Student.*;

@WebServlet(name = "UpdateServlet", value = "/update")
public class UpdateServlet extends HttpServlet {

    private final Repository<Student> studentRepository;
    private final Repository<Group> groupRepository;

    public UpdateServlet() {
        this.studentRepository = new StudentRepositoryImpl(ConnectionFactory.getConnection());
        this.groupRepository = new GroupRepositoryImpl(ConnectionFactory.getConnection());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (req.getParameter(ENTITY).equals(STUDENT_ENTITY)) {
                val studentId = Integer.parseInt(req.getParameter(STUDENT_ID));

                studentRepository.update(new Student()
                        .setId(studentId)
                        .setFirstName(getUpdatedStringValue(req, UPDATED_FIRST_NAME))
                        .setLastName(getUpdatedStringValue(req, UPDATED_LAST_NAME))
                        .setGroupId(getUpdatedIntegerValue(req, UPDATED_GROUP_ID)));

            } else if (req.getParameter(ENTITY).equals(GROUP_ENTITY)) {
                val groupId = Integer.parseInt(req.getParameter(GROUP_ID));

                groupRepository.update(new Group()
                        .setId(groupId)
                        .setGroupName(getUpdatedStringValue(req, UPDATED_GROUP_NAME))
                        .setCourse(getUpdatedIntegerValue(req, UPDATED_GROUP_COURSE)));
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

    private String getUpdatedStringValue(@NonNull HttpServletRequest req, @NonNull String updatedGroupName) {
        val parameter = req.getParameter(updatedGroupName);

        return parameter.equals(Strings.EMPTY) ? null : parameter;
    }

    private Integer getUpdatedIntegerValue(@NonNull HttpServletRequest req, @NonNull String parameter) {
        int updatedGroupId;

        try {
            updatedGroupId = Integer.parseInt(req.getParameter(parameter));
        } catch (NumberFormatException e) {
            return null;
        }

        return updatedGroupId;
    }
}
