package yilee.fsrv.directory.file.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import yilee.fsrv.directory.file.domain.enums.FileType;
import yilee.fsrv.directory.folder.domain.entity.FolderObject;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @EqualsAndHashCode(of = "id")
@Table(name = "file_object",
        indexes = {
                @Index(name = "idx_file_folder", columnList = "folder_id"),
                @Index(name = "idx_file_owner", columnList = "owner_id")
        },
        uniqueConstraints = @UniqueConstraint(
                name = "uk_folder_filename_version",
                columnNames = {"folder_id", "original_filename", "version"}
        )
)
@Builder @NoArgsConstructor @AllArgsConstructor
public class FileObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(name = "original_filename", nullable = false)
    private String originalFilename;

    @Column(name = "store_filename", nullable = false)
    private String storeFilename;

    @Column(length = 50)
    private String extension;

    @Column(name = "checksum_sha256", length = 64)
    private String checksumSha256;

    @Column(length = 1024)
    private String storagePath;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "folder_id", nullable = false)
    private FolderObject folder;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Column(name = "version", nullable = false)
    @Builder.Default
    private Integer version = 1;

    @Column(nullable = false)
    private LocalDateTime uploadedAt;

    @Column(nullable = false)
    @Builder.Default
    private boolean deleted = false;

    private LocalDateTime deletedAt;

    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artifact_id")
    private FileArtifact artifact;

    public void markDeleted() {
        if (!this.deleted) {
            this.deleted = true;
            this.deletedAt = LocalDateTime.now();
        }
    }

    @PrePersist
    private void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (this.uploadedAt == null) this.uploadedAt = now;
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
