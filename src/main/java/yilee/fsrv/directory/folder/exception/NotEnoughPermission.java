package yilee.fsrv.directory.folder.exception;

public class NotEnoughPermission extends RuntimeException {
    public NotEnoughPermission(String message) {
        super(message);
    }

    public NotEnoughPermission(String message, Throwable cause) {
        super(message, cause);
    }
}
