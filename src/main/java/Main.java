
import model.Project;

import java.sql.SQLException;
import java.util.List;

import static service.ProjectService.PROJECT_SERVICE;


public class Main {

    public static void main(String[] args) throws SQLException, InterruptedException {
        final List<Project> projects = PROJECT_SERVICE.listProjectsByName("Jira");
        System.out.println(projects);
        final Project projectById = PROJECT_SERVICE.findProjectById(9);
        System.out.println(projectById);
        final List<Project> projectByUserId = PROJECT_SERVICE.findProjectByUserId(1);
        System.out.println(projectByUserId);

    }


}

