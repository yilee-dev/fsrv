package yilee.fsrv.directory.acl.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import yilee.fsrv.directory.acl.enums.FolderPermission;
import yilee.fsrv.directory.acl.repository.FolderAclRepository;
import yilee.fsrv.directory.folder.domain.entity.FolderObject;
import yilee.fsrv.login.domain.entity.Member;

import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PermissionChecker {
    private final FolderAclRepository folderAclRepository;

    public boolean hasPermission(Member member, FolderObject folder, FolderPermission required) {
        if (Objects.equals(folder.getOwnerId(), member.getId())) {
            return true;
        }

        Set<FolderPermission> acceptable = PermissionResolver.requireOrHigher(required);

        return folderAclRepository.existsByFolderIdAndSubjectIdAndPermissionIn(folder.getId(), member.getId(), acceptable);
    }

    public boolean canRead(Member m, FolderObject f) {
        return hasPermission(m, f, FolderPermission.READ);
    }

    public boolean canWrite(Member m, FolderObject f) {
        return hasPermission(m, f, FolderPermission.WRITE);
    }

    public boolean canDelete(Member m, FolderObject f) {
        return hasPermission(m, f, FolderPermission.DELETE);
    }
}
