package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Task {

    /*
    primary key AI
     */
    private int id;
    /*
    NOT NULL
     */
    private String title;
    /*
    NOT MANDATORY
     */
    private String description;
    /*
    FA-l enum. TODO, IN_PROGRESS, IN_VALIDATION, COMPLETED
    default value - TODO
    cand se va face update la status, si nu va fi in TODO,
    asigura-te ca i se asigneaza si un userId. Altfel arunca exceptie
     */
    private TaskStatus status;
    /*
    NOT MANDATORY AT CREATION
     */
    private int userId;
    /*
    MANDATORY
     */
    private int projectId;
    /*
    default 0
     */
    private int workedHours;

}
