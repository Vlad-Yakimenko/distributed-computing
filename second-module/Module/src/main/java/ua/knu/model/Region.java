package ua.knu.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Region implements Serializable {
    private String name;
    private String area;
    private String citizenType;
}
