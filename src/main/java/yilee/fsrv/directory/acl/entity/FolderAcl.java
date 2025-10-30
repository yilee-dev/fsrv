package yilee.fsrv.directory.acl.entity;

import jakarta.persistence.*;
import lombok.*;
import yilee.fsrv.directory.acl.enums.FolderPermission;
import yilee.fsrv.directory.folder.domain.entity.FolderObject;

@Entity
@Table(name = "folder_acl",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_folder_subject",
                columnNames = {"folder_id", "subject_id", "permission"}
        ),
        indexes = {
                @Index(name = "idx_acl_folder", columnList = "folder_id"),
                @Index(name = "idx_acl_subject", columnList = "subject_id"),
                @Index(name = "idx_acl_permission", columnList = "permission")
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FolderPermission permission;
}