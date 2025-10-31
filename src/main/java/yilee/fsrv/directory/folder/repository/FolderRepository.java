package yilee.fsrv.directory.folder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yilee.fsrv.directory.folder.domain.entity.FolderObject;

public interface FolderRepository extends JpaRepository<FolderObject, Long> {
}
