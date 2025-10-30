package yilee.fsrv.directory.folder.helper;


import yilee.fsrv.directory.acl.enums.FolderPermission;

import java.util.EnumSet;
import java.util.Set;

public class PermissionResolver {
    public static Set<FolderPermission> expand(FolderPermission permission) {
        return switch (permission) {
            case WRITE -> EnumSet.of(
                    FolderPermission.WRITE,
                    FolderPermission.READ
            );
            case DELETE -> EnumSet.of(
                    FolderPermission.DELETE,
                    FolderPermission.READ
            );
            case READ -> EnumSet.of(
                    FolderPermission.READ
            );
        };
    }
}
