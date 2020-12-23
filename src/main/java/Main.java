
import model.Project;

import java.sql.SQLException;
import java.util.List;

import static service.ProjectService.PROJECT_SERVICE;


public class Main {

    public static void main(String[] args)  {

        final List<Project> projectByUserId = PROJECT_SERVICE.findProjectByUserId(1);
        System.out.println(projectByUserId);

    }


}

