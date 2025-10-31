package yilee.fsrv.directory.folder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yilee.fsrv.directory.acl.helper.PermissionChecker;
import yilee.fsrv.directory.folder.domain.dto.CreateFolderRequest;
import yilee.fsrv.directory.folder.domain.dto.CreateFolderResponse;
import yilee.fsrv.directory.folder.domain.dto.FolderDto;
import yilee.fsrv.directory.folder.domain.entity.FolderObject;
import yilee.fsrv.directory.folder.exception.*;
import yilee.fsrv.directory.folder.repository.FolderClosureRepository;
import yilee.fsrv.directory.folder.repository.FolderMetricsRepository;
import yilee.fsrv.directory.folder.repository.FolderRepository;
import yilee.fsrv.login.domain.entity.Member;
import yilee.fsrv.login.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final PermissionChecker permissionChecker;
    private final MemberRepository memberRepository;
    private final FolderClosureRepository folderClosureRepository;
    private final FolderMetricsRepository folderMetricsRepository;

    @Transactional
    public CreateFolderResponse createFolder(CreateFolderRequest request) {
        FolderObject parent = folderRepository.findById(request.parentId())
                .orElseThrow(() -> new FolderNotFoundException("[Not found folder: " + request.parentId() + "]"));

        Member member = memberRepository.findById(request.creatorId())
                .orElseThrow(() -> new InvalidFolderCreatorException("[Not found member: " + request.creatorId() + "]"));

        if (parent.isDeleted()) throw new FolderAlreadyDeletedException("[Folder already deleted: " + parent.getId() + "]");

        if (!permissionChecker.canWrite(member, parent)) {
            throw new NotEnoughPermission("[Not enough permission]");
        }

        FolderObject child;
        try {
            child = folderRepository.save(FolderObject.builder()
                    .name(request.name())
                    .parent(parent)
                    .scope(parent.getScope())
                    .ownerId(request.creatorId())
                    .build());
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateFolderNameException("uk_parent_name: parentId, name");
        }

        folderClosureRepository.insertSelf(child.getId());
        folderClosureRepository.linkFromParentAncestors(parent.getId(), child.getId());

        folderMetricsRepository.initIfAbsent(child.getId());
        folderMetricsRepository.incDirectFolder(parent.getId());
        folderMetricsRepository.incDescFolderAncestorsOf(child.getId());

        return new CreateFolderResponse(new FolderDto(child.getId(), child.getName(), child.getOwnerId(), child.getScope()));
    }
}
