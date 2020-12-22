package service;

import exceptions.business.InvalidEntityException;
import exceptions.business.ProjectNotFoundException;
import exceptions.technical.JiraSystemException;
import exceptions.technical.Status;
import model.Project;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static database.connection.MysqlDataSource.DATA_SOURCE;
import static repository.ProjectRepository.PROJECT_REPOSITORY;

public enum ProjectService {

    PROJECT_SERVICE;

    public void createProject(String projectName) {
        Project project = new Project(projectName);

        try (Connection connection = DATA_SOURCE.getConnection()) {
            PROJECT_REPOSITORY.createProject(connection, project);

        } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
            throw new InvalidEntityException("Invalid project. Check project fields. " +
                    "Maybe you have already a project with the same name");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new JiraSystemException(Status.DB_CONNECTION_PROBLEM,
                    "check that you are able to call db in the network");
        }
    }

    public List<Project> listProjectsByName(String projectName) {
        List<Project> projects = new ArrayList<>();

        try (Connection connection = DATA_SOURCE.getConnection()) {
            projects = PROJECT_REPOSITORY.listProjectsByName(connection, projectName);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return projects;
    }

    public Project findProjectById(int id) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            return PROJECT_REPOSITORY.findProjectById(connection, id)
                    .orElseThrow(() -> new ProjectNotFoundException(id));
        } catch (SQLException sqlException) {
            throw new JiraSystemException(Status.DB_CONNECTION_PROBLEM,
                    "check that you are able to call db in the network");
        }
    }

}
