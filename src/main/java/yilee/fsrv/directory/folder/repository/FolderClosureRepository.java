package yilee.fsrv.directory.folder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import yilee.fsrv.directory.folder.domain.entity.FolderClosure;
import yilee.fsrv.directory.folder.domain.vo.FolderClosureId;

public interface FolderClosureRepository extends JpaRepository<FolderClosure, FolderClosureId> {
    @Modifying
    @Query(value = """
            insert into folder_closure(ancestor_id, descendant_id, depth)
            values(?1, ?1, 0)
        """, nativeQuery = true)
    void insertSelf(Long childId);

    @Modifying
    @Query(value = """
                insert into folder_closure(ancestor_id, descendant_id, depth)
                select fc.ancestor_id, ?2, fc.depth + 1
                from folder_closure fc
                where fc.descendant_id = ?1
            """, nativeQuery = true)
    void linkFromParentAncestors(Long parentId, Long childId);
}
