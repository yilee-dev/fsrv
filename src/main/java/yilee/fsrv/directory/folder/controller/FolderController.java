package yilee.fsrv.directory.folder.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yilee.fsrv.directory.folder.domain.dto.CreateFolderRequest;
import yilee.fsrv.directory.folder.domain.dto.CreateFolderResponse;
import yilee.fsrv.directory.folder.service.FolderService;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public CreateFolderResponse createFolder(@Valid @RequestBody CreateFolderRequest request) {
        return folderService.createFolder(request);
    }
}
