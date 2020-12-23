package repository;

import model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum ProjectRepository {

    PROJECT_REPOSITORY;

    public static final String WILDCARD = "%";
    private static final String INSERT_PROJECT = "INSERT INTO project (name) VALUES (?)";
    private static final String LIST_ALL_PROJECTS = "SELECT * FROM project";
    private static final String LIST_PROJECT_BY_NAME = "SELECT * FROM project WHERE name LIKE ?";
    private static final String FIND_PROJECT_BY_ID = "SELECT * FROM project WHERE id=?";
    private static final String LIST_PROJECT_WHERE_A_USER_WORKS = "SELECT * FROM project WHERE id IN " +
            "(SELECT project_id from TASK WHERE user_id=?)";
    private static final String UPDATE_PROJECT_NAME = "UPDATE project SET name=? WHERE id=?";
    private static final String DELETE_PROJECT = "DELETE PROJECT WHERE id=?";

    public void createProject(Connection connection, Project project) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_PROJECT)) {
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

    public List<Project> listAll(Connection connection) throws SQLException {
        List<Project> projects = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(LIST_ALL_PROJECTS);
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

    public List<Project> listProjectsByUser(Connection connection, int userId) throws SQLException {
        List<Project> projects = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(LIST_PROJECT_WHERE_A_USER_WORKS)) {
            preparedStatement.setInt(1, userId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                projects.add(mapRowToProject(resultSet));
            }
        }

        return projects;
    }


    private Project mapRowToProject(ResultSet resultSet) throws SQLException {
        final int projectId = resultSet.getInt(1);
        final String name = resultSet.getString(2);
        return Project.builder()
                .id(projectId)
                .name(name)
                .build();
    }
}
