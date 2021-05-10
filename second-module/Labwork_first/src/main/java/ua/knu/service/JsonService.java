package ua.knu.service;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import ua.knu.model.Faculty;
import ua.knu.model.Group;
import ua.knu.model.Student;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Optional;

@Getter
@FieldDefaults(makeFinal = true)
public class JsonService {

    private Faculty faculty;
    private File storage;

    @SneakyThrows
    public JsonService() {
        this.storage = new File("src/main/resources/faculty.json");

        val json = FileUtils.readFileToString(storage, Charset.defaultCharset());
        this.faculty = JSON.parseObject(json, Faculty.class);
    }

    public void addGroup(Group group) {
        faculty.getGroups().add(group);
    }

    public void updateGroup(String groupName, Group updatedGroup) {
        val optionalGroup = getGroup(groupName);

        if (optionalGroup.isPresent()) {
            val group = optionalGroup.get();

            if (!StringUtils.isEmpty(updatedGroup.getName())) group.setName(updatedGroup.getName());
            if (updatedGroup.getCourse() != null) group.setCourse(updatedGroup.getCourse());
        }
    }

    public void removeGroup(String groupName) {
        val optionalGroup = getGroup(groupName);

        optionalGroup.ifPresent(group -> faculty.getGroups().remove(group));
    }

    public void addStudent(String groupName, Student student) {
        val optionalGroup = getGroup(groupName);

        optionalGroup.ifPresent(group -> group.getStudents().add(student));
    }

    public void updateStudent(String groupName, Student oldStudent, Student updatedStudent) {
        val optionalGroup = getGroup(groupName);

        if (optionalGroup.isPresent()) {
            val group = optionalGroup.get();

            val optionalStudent = getStudent(group, oldStudent);

            if (optionalStudent.isPresent()) {
                val student = optionalStudent.get();

                if (!StringUtils.isEmpty(updatedStudent.getFirstName()))
                    student.setFirstName(updatedStudent.getFirstName());

                if (!StringUtils.isEmpty(updatedStudent.getLastName()))
                    student.setLastName(updatedStudent.getLastName());
            }
        }
    }

    public void removeStudent(String groupName, Student student) {
        val optionalGroup = getGroup(groupName);

        optionalGroup.ifPresent(group -> group.getStudents().remove(student));
    }

    @SneakyThrows
    public void save() {
        FileUtils.writeStringToFile(storage, JSON.toJSONString(faculty), Charset.defaultCharset(), false);
    }

    private Optional<Student> getStudent(Group group, Student studentToFind) {
        return group.getStudents()
                .stream()
                .filter(student -> student.equals(studentToFind))
                .findFirst();
    }

    private Optional<Group> getGroup(String groupName) {
        return faculty.getGroups()
                .stream()
                .filter(group -> group.getName().equals(groupName))
                .findFirst();
    }
}
