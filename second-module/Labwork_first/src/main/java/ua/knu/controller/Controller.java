package ua.knu.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import ua.knu.model.Group;
import ua.knu.model.Student;
import ua.knu.service.JsonService;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    @FXML
    private TreeView<String> mTreeView;

    @FXML
    private TextField mFirstName;
    @FXML
    private TextField mLastName;
    @FXML
    private TextField mGroupName;
    @FXML
    private TextField mGroupCourse;

    private JsonService jsonService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.jsonService = new JsonService();

        printTreeView();
    }

    public void addStudent() {
        String groupName = getGroupName(getSelectedTreeItem());

        val firstName = mFirstName.getText();
        val lastName = mLastName.getText();

        if (StringUtils.isEmpty(mFirstName.getText()) || StringUtils.isEmpty(lastName)) {
            throw new IllegalArgumentException("first name and last name are required");
        }

        jsonService.addStudent(groupName, new Student(firstName, lastName));
        printTreeView();
    }

    public void updateStudent() {
        val selectedTreeItem = getSelectedTreeItem();
        val groupName = getGroupName(selectedTreeItem.getParent());

        val split = Arrays.stream(selectedTreeItem.getValue().split(StringUtils.SPACE))
                .filter(element -> !StringUtils.isEmpty(element))
                .collect(Collectors.toList());
        val oldFirstName = split.get(0);
        val oldLastName = split.get(1);

        val newFirstName = mFirstName.getText();
        val newLastName = mLastName.getText();

        jsonService.updateStudent(groupName, new Student(oldFirstName, oldLastName), new Student(newFirstName, newLastName));
        printTreeView();
    }

    public void removeStudent() {
        val selectedTreeItem = getSelectedTreeItem();
        val groupName = getGroupName(selectedTreeItem.getParent());

        val split = selectedTreeItem.getValue().split(StringUtils.SPACE);
        val firstName = split[0];
        val lastName = split[1];

        jsonService.removeStudent(groupName, new Student(firstName, lastName));
        printTreeView();
    }

    public void addGroup() {
        val groupName = mGroupName.getText();
        val course = Integer.parseInt(mGroupCourse.getText());

        jsonService.addGroup(new Group(groupName, course, new ArrayList<>()));
        printTreeView();
    }

    public void removeGroup() {
        val groupName = getGroupName(getSelectedTreeItem());

        jsonService.removeGroup(groupName);
        printTreeView();
    }

    public void updateGroup() {
        val groupName = getGroupName(getSelectedTreeItem());
        val updatedGroupName = mGroupName.getText();
        val updatedCourse = !StringUtils.isEmpty(mGroupCourse.getText()) ? Integer.parseInt(mGroupCourse.getText()) : null;

        jsonService.updateGroup(groupName, new Group(updatedGroupName, updatedCourse, new ArrayList<>()));
        printTreeView();
    }

    public void save() {
        jsonService.save();
    }

    private String getGroupName(TreeItem<String> treeItem) {
        return treeItem.getValue().split(StringUtils.SPACE)[1];
    }

    private TreeItem<String> getSelectedTreeItem() {
        val selectedItem = mTreeView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            throw new IllegalArgumentException("No item selected");
        }

        return selectedItem;
    }

    private void printTreeView() {
        mTreeView.setRoot(null);
        val groups = jsonService.getFaculty().getGroups();

        TreeItem<String> root = new TreeItem<>("Faculty");

        for (val group : groups) {
            val groupInfo = String.format("Group: %s course: %s", group.getName(), group.getCourse());
            val students = group.getStudents();
            val groupTreeItem = new TreeItem<>(groupInfo);

            for (val student : students) {
                val studentInfo = String.format("%s %s", student.getFirstName(), student.getLastName());
                groupTreeItem.getChildren().add(new TreeItem<>(studentInfo));
            }

            root.getChildren().add(groupTreeItem);
        }

        mTreeView.setRoot(root);
    }
}
