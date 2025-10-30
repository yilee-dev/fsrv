package yilee.fsrv.directory.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yilee.fsrv.directory.file.domain.entity.FileObject;

public interface FileRepository extends JpaRepository<FileObject, Long> {
}
