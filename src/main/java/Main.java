import model.Project;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import static database.connection.MysqlDataSource.DATA_SOURCE;
import static repository.ProjectRepository.PROJECT_REPOSITORY;
import static service.ProjectService.PROJECT_SERVICE;


public class Main {

    public static void main(String[] args) throws SQLException {

        final List<Project> projectByUserId = PROJECT_SERVICE.findProjectByUserId(1);
        System.out.println(projectByUserId);


        final Connection connection = DATA_SOURCE.getConnection();
        try {

            Project project = Project.builder().name("project31").build();
            PROJECT_REPOSITORY.createProject(connection, project);
            PROJECT_REPOSITORY.createProject(connection, project);
            PROJECT_REPOSITORY.createProject(connection, project);
            PROJECT_REPOSITORY.createProject(connection, project);

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            connection.close();
        }

    }


}

