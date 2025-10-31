package yilee.fsrv.directory.folder.domain.dto;

import lombok.Data;
import lombok.Getter;
import yilee.fsrv.directory.folder.domain.enums.FolderScope;

public record FolderDto (
        Long id,
        String name,
        Long ownerId,
        FolderScope scope
) {
}
