package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.model.Forum;
import com.project.model.ForumRepo;


@Controller
public class ForumController {

	@Autowired
	ForumRepo repof;
	
	@GetMapping("/showForum")
	public String showDataF(Model model) {
			List<Forum> f = repof.showAll();
			model.addAttribute("listF",f);
			return "home";
	}
    @GetMapping("/insertForum")
    public String insertForum(Model model) {
    	model.addAttribute("forum", new Forum());
        return "insertForum";
    }
	
	@PostMapping("/addF")
	public String add(@ModelAttribute Forum forum, Model model) {
	    // Set default values if not provided in the form
	        forum.setAuthor("not");
	        forum.setLove(0);

	    // Insert data into the database
	    repof.insertData(forum);

	    return "redirect:/";
	}

//		return "redirect:/";
//		Forum ans = new Forum();
//		ans.setDetail("Hello");
//		ans.setAuthor("GJ");
//		ans.setLove(1);
//		ans.setPostDate(new Date(System.currentTimeMillis()));
//		repof.insertData(ans);
//		System.out.println("Insert Successful");


	@GetMapping("/addlove/{id}")
    public String change(@PathVariable Integer id, Model model) {
		Forum forum = repof.findById(id);
        forum.upLove();
        repof.update(forum);
        return "redirect:/";  
    }
}
