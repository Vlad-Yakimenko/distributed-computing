package ua.knu.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Group {
    private String name;
    private Integer course;
    private List<Student> students;
}
