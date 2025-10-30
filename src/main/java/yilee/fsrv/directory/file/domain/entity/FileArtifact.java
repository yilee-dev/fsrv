package yilee.fsrv.directory.file.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.File;

@Entity
@Table(name = "file_artifact")
@Getter @EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor @AllArgsConstructor
public class FileArtifact {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_artifact_id")
    private Long id;

    @Column(length = 50)
    private String platform;

    @Column(length = 255)
    private String processName;

    @Column(length = 512)
    private String installPath;

    @Column(length = 1024)
    private String commandLine;

    @Column(name = "checksum_sha256", length = 64)
    private String checksumSha256;

    @Column(length = 1024)
    private String downloadUrl;
}
