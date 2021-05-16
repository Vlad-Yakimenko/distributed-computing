package ua.knu.persistence.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Group {
    private Integer id;
    private String groupName;
    private Integer course;
}
