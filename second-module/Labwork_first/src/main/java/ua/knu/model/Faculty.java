package ua.knu.model;

import lombok.Value;

import java.util.List;

@Value
public class Faculty {
    List<Group> groups;
}
