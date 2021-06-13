package ua.knu.event;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class GetDatesWhenSnowingAndTemperatureLess implements Serializable {
    private String region;
    private Integer temperature;
}
