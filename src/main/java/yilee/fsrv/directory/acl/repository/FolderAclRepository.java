package yilee.fsrv.directory.acl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yilee.fsrv.directory.acl.entity.FolderAcl;
import yilee.fsrv.directory.acl.enums.FolderPermission;

import java.util.Collection;
import java.util.Set;

public interface FolderAclRepository extends JpaRepository<FolderAcl, Long> {
    boolean existsByFolderIdAndSubjectIdAndPermissionIn(Long folderId, Long subjectId, Set<FolderPermission> acceptable);
}
