package yilee.fsrv.directory.folder.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yilee.fsrv.directory.folder.domain.vo.FolderClosureId;

@Entity
@Table(name = "folder_closure")
@Getter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of = "id")
public class FolderClosure {

    @EmbeddedId
    private FolderClosureId id;

    @Column(nullable = false)
    private int depth;

    @MapsId("ancestorId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ancestor_id", insertable = false, updatable = false)
    private FolderObject ancestor;

    @MapsId("descendantId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "descendant_id", insertable = false, updatable = false)
    private FolderObject descendant;
}
