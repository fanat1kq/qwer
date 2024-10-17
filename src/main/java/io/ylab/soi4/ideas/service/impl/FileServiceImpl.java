package io.ylab.soi4.ideas.service.impl;

import io.ylab.soi4.ideas.model.File;
import io.ylab.soi4.ideas.repository.FileRepository;
import io.ylab.soi4.ideas.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public void create(File file) {
        fileRepository.save(file);
    }
}
