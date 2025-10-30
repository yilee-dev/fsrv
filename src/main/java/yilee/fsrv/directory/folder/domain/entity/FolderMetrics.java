package yilee.fsrv.directory.folder.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "folder_metrics")
@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class FolderMetrics {

    @Id
    @Column(name = "folder_id")
    private Long folderId;

    private long directFileCount;
    private long directFileSize;
    private long descendantFileCount;
    private long descendantFileSize;
    private long directFolderCount;
    private long descendantFolderCount;

    private LocalDateTime updatedAt;

    @PrePersist @PreUpdate
    void update() {
        this.updatedAt = LocalDateTime.now();
    }

    public void incDirectFile(long size){ directFileCount++; directFileSize += size; }
    public void decDirectFile(long size){ directFileCount--; directFileSize -= size; }
    public void incDescFile(long size){ descendantFileCount++; descendantFileSize += size; }
    public void decDescFile(long size){ descendantFileCount--; descendantFileSize -= size; }
    public void incDirectFolder(){ directFolderCount++; }
    public void decDirectFolder(){ directFolderCount--; }
    public void incDescFolder(long n){ descendantFolderCount += n; }
    public void decDescFolder(long n){ descendantFolderCount -= n; }
}
