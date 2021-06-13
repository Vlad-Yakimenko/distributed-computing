package ua.knu.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CitizenType implements Serializable {
    private String typeName;
    private String language;
}
