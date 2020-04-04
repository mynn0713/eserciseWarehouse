package com.example.service;

import com.example.pojo.Resume;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IResumeService {
    List<Resume> findAllResume();

    Resume findResumeById(Long id);

    void modifyResume(Resume resume);

    void deleteResumeById(Long id);

    void addResume(Resume resume);
}
