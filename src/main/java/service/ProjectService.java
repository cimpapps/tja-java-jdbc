package service;

import exceptions.business.InvalidEntityException;
import exceptions.business.ProjectNotFoundException;
import exceptions.technical.JiraSystemException;
import exceptions.technical.Status;
import model.Project;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
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
        } catch (SQLSyntaxErrorException sqlSyntaxErrorException) {
            throw new JiraSystemException(Status.BAD_SQL_QUERY_SYNTAX,
                    "check your syntax man, you suck!!");
        } catch (SQLException sqlException) {
            throw new JiraSystemException(Status.DB_CONNECTION_PROBLEM,
                    "check that you are able to call db in the network");
        }
    }

    public List<Project> listProjectsByName(String projectName) {
        List<Project> projects;
        try (Connection connection = DATA_SOURCE.getConnection()) {
            projects = PROJECT_REPOSITORY.listProjectsByName(connection, projectName);
        } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
            throw new InvalidEntityException("Invalid project. Check project fields. " +
                    "Maybe you have already a project with the same name");
        } catch (SQLException sqlException) {
            throw new JiraSystemException(Status.DB_CONNECTION_PROBLEM,
                    "check that you are able to call db in the network");
        }

        return projects;
    }

    public List<Project> listAllProjects() {
        List<Project> projects;
        try (Connection connection = DATA_SOURCE.getConnection()){
            projects = PROJECT_REPOSITORY.listAll(connection);
        } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
            throw new InvalidEntityException("Invalid project. Check project fields. " +
                    "Maybe you have already a project with the same name");
        } catch (SQLException sqlException) {
            throw new JiraSystemException(Status.DB_CONNECTION_PROBLEM,
                    "check that you are able to call db in the network");
        }
        return projects;
    }

    public Project findProjectById(int id) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            return PROJECT_REPOSITORY.findProjectById(connection, id)
                    .orElseThrow(() -> new ProjectNotFoundException(id));
        } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
            throw new InvalidEntityException("Invalid project. Check project fields. " +
                    "Maybe you have already a project with the same name");
        } catch (SQLException sqlException) {
            throw new JiraSystemException(Status.DB_CONNECTION_PROBLEM,
                    "check that you are able to call db in the network");
        }
    }

    public List<Project> findProjectByUserId(int userId) {
        List<Project> projects;
        try (Connection connection = DATA_SOURCE.getConnection()){
            //TODO check if the user exists with the user repository
            projects = PROJECT_REPOSITORY.listProjectsByUser(connection, userId);
        } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
            throw new InvalidEntityException("Invalid project. Check project fields. " +
                    "Maybe you have already a project with the same name");
        } catch (SQLException sqlException) {
            throw new JiraSystemException(Status.DB_CONNECTION_PROBLEM,
                    "check that you are able to call db in the network");
        }
        return projects;
    }

}
