package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    /*
    PRIMARY_KEY
     */
    private int id;
    /*
    UNIQUE
     */
    private String username;


}
