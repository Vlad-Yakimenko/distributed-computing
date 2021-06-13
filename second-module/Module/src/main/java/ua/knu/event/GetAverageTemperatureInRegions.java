package ua.knu.event;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class GetAverageTemperatureInRegions implements Serializable {
    private Integer area;
}
