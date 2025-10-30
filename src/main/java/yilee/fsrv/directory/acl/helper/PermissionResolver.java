package yilee.fsrv.directory.acl.helper;


import yilee.fsrv.directory.acl.enums.FolderPermission;

import java.util.EnumSet;
import java.util.Set;

public final class PermissionResolver {

    private PermissionResolver() {}

    public static Set<FolderPermission> requireOrHigher(FolderPermission required) {
        return switch (required) {
            case READ -> EnumSet.of(FolderPermission.READ, FolderPermission.WRITE, FolderPermission.DELETE);
            case WRITE -> EnumSet.of(FolderPermission.WRITE, FolderPermission.DELETE);
            case DELETE -> EnumSet.of(FolderPermission.DELETE);
        };
    }
}
