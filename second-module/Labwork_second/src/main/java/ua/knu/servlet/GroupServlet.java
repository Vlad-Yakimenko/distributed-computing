package ua.knu.servlet;

import lombok.SneakyThrows;
import lombok.val;
import ua.knu.persistence.ConnectionFactory;
import ua.knu.persistence.model.Group;
import ua.knu.persistence.repository.GroupRepositoryImpl;
import ua.knu.persistence.repository.Repository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.knu.util.Constants.Group.*;
import static ua.knu.util.Constants.Other.INDEX;

@WebServlet(name = "groupsServlet", value = "/groups")
public class GroupServlet extends HttpServlet {

    private final Repository<Group> groupRepository;

    public GroupServlet() {
        this.groupRepository = new GroupRepositoryImpl(ConnectionFactory.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(GROUPS, groupRepository.findAll());
        req.setAttribute(GROUPS_TITLE, "Groups: ");

        try {
            req.getRequestDispatcher(INDEX).forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            val name = req.getParameter(GROUP_NAME);
            val course = Integer.parseInt(req.getParameter(COURSE));

            groupRepository.save(new Group()
                    .setGroupName(name)
                    .setCourse(course));

            req.getRequestDispatcher(INDEX).forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public void destroy() {
        groupRepository.close();
        super.destroy();
    }
}
