package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Task {

    private int id;
    private String title;
    private String description;
    private String status;
    private int userId;
    private int projectId;

}
