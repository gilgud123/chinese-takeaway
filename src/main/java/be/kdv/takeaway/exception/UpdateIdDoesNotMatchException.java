package be.kdv.takeaway.exception;

/**
 * To be thrown when an update is requested where the id in the path did not match the id of the entity provided in the body of the request.
 */
public class UpdateIdDoesNotMatchException extends RuntimeException {

    private String pathId;

    private String bodyId;

    public UpdateIdDoesNotMatchException(final String pathId, final String bodyId) {
        this.pathId = pathId;
        this.bodyId = bodyId;
    }

    public String getPathId() {
        return pathId;
    }

    public String getBodyId() {
        return bodyId;
    }
}
