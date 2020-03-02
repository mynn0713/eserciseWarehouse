package com.lagou.edu.controller;

import com.lagou.edu.pojo.Resume;
import com.lagou.edu.service.IResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/resume")
@Controller
public class ResumeController {
    @Autowired
    IResumeService resumeService;
    @RequestMapping(value = "/table",method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView getList(){
        ModelAndView mad = new ModelAndView();
        List<Resume> resumes = resumeService.findAllResume();

        mad.setViewName("/resume/resumeTable");
        mad.addObject(resumes);
        return mad;
    }

    @RequestMapping(value = "/resumeModifyView",method = RequestMethod.GET)
    public ModelAndView resumeModifyView(Long id){
        ModelAndView mad = new ModelAndView();
        Resume resume = resumeService.findResumeById(id);

        mad.setViewName("/resume/resumeModify");
        mad.addObject(resume);
        return mad;
    }

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public ModelAndView add(Resume resume){
        ModelAndView mad = new ModelAndView();
        resumeService.addResume(resume);

        mad.setViewName("redirect:/resume/table");
        mad.addObject(resume);
        return mad;
    }

    @RequestMapping(value = "/addView" , method = RequestMethod.POST)
    public String addView(){
        /*ModelAndView mad = new ModelAndView();
        mad.setViewName("/resume/resumeAdd");*/
        return "/resume/resumeAdd";
    }

    @RequestMapping(value = "/modify" , method = RequestMethod.POST)
    public ModelAndView modify(Resume resume){
        ModelAndView mad = new ModelAndView();
        resumeService.modifyResume(resume);

        mad.setViewName("redirect:/resume/table");
        mad.addObject(resume);
        return mad;
    }

    @RequestMapping(value = "/delete" , method = RequestMethod.GET)
    public ModelAndView modify(Long id){
        ModelAndView mad = new ModelAndView();
        resumeService.deleteResumeById(id);
        mad.setViewName("redirect:/resume/table");
        return mad;
    }
}
