package yilee.fsrv.directory.folder.exception;

public class InvalidFolderCreatorException extends RuntimeException {
    public InvalidFolderCreatorException(String message) {
        super(message);
    }

    public InvalidFolderCreatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
