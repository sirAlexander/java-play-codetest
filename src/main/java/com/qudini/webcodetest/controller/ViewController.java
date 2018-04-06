package com.qudini.webcodetest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
	
	@RequestMapping("/upload")
    public String upload() {
        return "uploadForm";
    }

}
