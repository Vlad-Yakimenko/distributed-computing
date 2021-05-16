package ua.knu.persistence.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Student {
    Integer id;
    String firstName;
    String lastName;
    Integer groupId;
}
