package yilee.fsrv.directory.folder.exception;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DuplicateFolderNameException extends RuntimeException {
    public DuplicateFolderNameException() {
        super();
    }

    public DuplicateFolderNameException(String message) {
        super(message);
    }

    public DuplicateFolderNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateFolderNameException(Throwable cause) {
        super(cause);
    }

    protected DuplicateFolderNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
