package repository;

import model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum  ProjectRepository {

    PROJECT_REPOSITORY;

    private static final String INSERT_PROJECT = "INSERT INTO project (name) VALUES (?)";
    private static final String LIST_ALL_PROJECTS = "SELECT * FROM project";
    private static final String LIST_PROJECT_BY_NAME = "SELECT * FROM project WHERE name LIKE ?";
    private static final String FIND_PROJECT_BY_ID = "SELECT * FROM project WHERE id=?";
    private static final String LIST_PROJECT_WHERE_A_USER_WORKS = "SELECT * FROM project WHERE id IN " +
            "(SELECT project_id from TASK WHERE user_id=?) ?";
    private static final String UPDATE_PROJECT_NAME = "UPDATE project SET name=? WHERE id=?";
    private static final String DELETE_PROJECT = "DELETE PROJECT WHERE id=?";
    public static final String WILDCARD = "%";

    public void createProject(Connection connection, Project project) throws SQLException {
       try(PreparedStatement ps = connection.prepareStatement(INSERT_PROJECT)){
           ps.setString(1, project.getName());
           ps.executeUpdate();
       }
    }


    public List<Project> listProjectsByName(Connection connection, String projectName) throws SQLException {
        List<Project> projects = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(LIST_PROJECT_BY_NAME)) {
            preparedStatement.setString(1, WILDCARD + projectName + WILDCARD);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                projects.add(mapRowToProject(resultSet));
            }
        }

        return projects;
    }

    public Optional<Project> findProjectById(Connection connection, int id) throws SQLException {
        Project project = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_PROJECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                project = mapRowToProject(resultSet);
            }
        }
        return Optional.ofNullable(project);
    }



    private Project mapRowToProject(ResultSet resultSet) throws SQLException {
        final int projectId = resultSet.getInt(1);
        final String name = resultSet.getString(2);
        return new Project(projectId, name);
    }
}
