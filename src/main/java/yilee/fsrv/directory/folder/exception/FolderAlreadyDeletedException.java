package yilee.fsrv.directory.folder.exception;

public class FolderAlreadyDeletedException extends RuntimeException {
    public FolderAlreadyDeletedException(String message) {
        super(message);
    }

    public FolderAlreadyDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
