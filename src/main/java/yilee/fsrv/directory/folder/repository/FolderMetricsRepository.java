package yilee.fsrv.directory.folder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yilee.fsrv.directory.folder.domain.entity.FolderMetrics;

public interface FolderMetricsRepository extends JpaRepository<FolderMetrics, Long> {

    @Modifying
    @Query(value = """
        insert into folder_metrics(folder_id, direct_file_count, direct_file_size,
                descendant_file_count, descendant_file_size, 
                direct_folder_count, descendant_folder_count, updated_at)
        values(?1, 0, 0, 0, 0, 0, 0, now())
        on duplicate key update folder_id = folder_id
    """, nativeQuery = true)
    void initIfAbsent(@Param("folderId") Long folderId);

    @Modifying
    @Query(value = """
                update folder_metrics
                    set direct_folder_count = direct_folder_count + 1,
                    updated_at = now()
                   where folder_id = ?1
            """, nativeQuery = true)
    void incDirectFolder(Long parentId);

    @Modifying
    @Query(value = """
            update folder_metrics
            set descendant_folder_count = descendant_folder_count + 1,
            updated_at = now()
            where folder_id in (
                select ancestor_id
                from folder_closure
                where descendant_id = ?1 and ancestor_id <> ?1
            )
            """, nativeQuery = true)
    void incDescFolderAncestorsOf(Long childId);

    @Modifying
    @Query(value = """
                    update folder_metrics
                    set direct_file_count = direct_file_count + 1,
                    direct_file_size = direct_file_size + ?2,
                    updated_at = now()
                    where folder_id = ?1
            """, nativeQuery = true)
    void incDirectFile(Long folderId, long size);

    @Modifying
    @Query(value = """
                update folder_metrics
                    set descendant_file_count = descendant_file_count + 1,
                    descendant_file_size = descendant_file_size + ?2,
                    updated_at = now()
                  where folder_id in (
                    select ancestor_id
                    from folder_closure
                    where descendant_id = ?1
                  )
            """, nativeQuery = true)
    void incDescFileAncestorsOf(Long folderId, long size);
}
