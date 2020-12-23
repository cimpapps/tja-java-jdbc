package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Project {

    /*
    primary key, AI
     */
    private int id;
    /*
    UNIQUE
    NON NULL
     */
    private String name;

}
