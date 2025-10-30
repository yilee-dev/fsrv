package yilee.fsrv.directory.acl.entity;

import jakarta.persistence.*;
import lombok.*;
import yilee.fsrv.directory.acl.enums.FolderPermission;
import yilee.fsrv.directory.folder.domain.entity.FolderObject;
import yilee.fsrv.login.domain.entity.Member;

import java.time.LocalDateTime;

@Entity
@Table(name = "folder_acl",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_folder_subject",
                columnNames = {"folder_id", "subject_id", "permission"}
        ),
        indexes = {
                @Index(name = "idx_acl_folder", columnList = "folder_id"),
                @Index(name = "idx_acl_subject", columnList = "subject_id"),
                @Index(name = "idx_acl_permission", columnList = "permission"),
                @Index(name = "idx_acl_folder_subject", columnList = "folder_id, subject_id")
        }
)
@Getter @EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor @AllArgsConstructor
public class FolderAcl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private FolderObject folder;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(name = "granted_by_id")
    private Long grantedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FolderPermission permission;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public static FolderAcl of(FolderObject folder, Long subjectId, FolderPermission p, Long grantedBy) {
        return FolderAcl.builder()
                .folder(folder)
                .subjectId(subjectId)
                .permission(p)
                .grantedBy(grantedBy)
                .build();
    }
}