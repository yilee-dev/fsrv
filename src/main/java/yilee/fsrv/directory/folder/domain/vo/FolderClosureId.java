package yilee.fsrv.directory.folder.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Embeddable
@Getter @EqualsAndHashCode
public class FolderClosureId implements Serializable {
    @Column(name = "ancestor_id") private Long ancestorId;
    @Column(name = "descendant_id") private Long descendantId;
}
