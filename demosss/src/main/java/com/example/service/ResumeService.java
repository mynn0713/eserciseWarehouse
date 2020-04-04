package com.example.service;

import com.example.pojo.Resume;
import com.example.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ResumeService implements IResumeService {
    @Autowired
    ResumeRepository resumeRepository;
    @Override
    public List<Resume> findAllResume() {
        List<Resume> allResumes = resumeRepository.findAll();
        return allResumes;
    }

    @Override
    public Resume findResumeById(Long id) {
        Optional<Resume> resume = resumeRepository.findById(id);
        return resume.get();
    }

    @Override
    public void modifyResume(Resume resume) {
        resumeRepository.save(resume);
    }

    @Override
    public void deleteResumeById(Long id) {
        resumeRepository.deleteById(id);
    }

    @Override
    public void addResume(Resume resume) {
        resumeRepository.save(resume);
    }


}
