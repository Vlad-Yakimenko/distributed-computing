package ua.knu.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;

@Data
@Accessors(chain = true)
public class Weather implements Serializable {
    private String region;
    private Date date;
    private Integer temperature;
    private Precipitation precipitation;
}
