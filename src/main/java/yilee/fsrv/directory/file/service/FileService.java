package yilee.fsrv.directory.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yilee.fsrv.directory.file.domain.entity.FileObject;
import yilee.fsrv.login.domain.entity.Member;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {
}
