package exceptions.technical;

public class JiraSystemException extends RuntimeException {

    private Status status;
    private String description;

    public JiraSystemException(Status status, String description) {
        super();
        this.status = status;
        this.description = description;
    }
}
