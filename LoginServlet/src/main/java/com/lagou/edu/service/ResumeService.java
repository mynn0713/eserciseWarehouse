package com.lagou.edu.service;

import com.lagou.edu.dao.ResumeDao;
import com.lagou.edu.pojo.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ResumeService implements IResumeService {
    @Autowired
    ResumeDao resumeDao;
    @Override
    public List<Resume> findAllResume() {
        List<Resume> allResumes = resumeDao.findAll();
        return allResumes;
    }

    @Override
    public Resume findResumeById(Long id) {
        Optional<Resume> resume = resumeDao.findById(id);
        return resume.get();
    }

    @Override
    public void modifyResume(Resume resume) {
        resumeDao.save(resume);
    }

    @Override
    public void deleteResumeById(Long id) {
        resumeDao.deleteById(id);
    }

    @Override
    public void addResume(Resume resume) {
        resumeDao.save(resume);
    }


}
