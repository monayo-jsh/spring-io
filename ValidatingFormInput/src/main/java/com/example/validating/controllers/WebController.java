package com.example.validating.controllers;

import com.example.validating.domain.PersonRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class WebController implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/")
    public String showForm(PersonRequest personRequest) {
        return "form";
    }

    @PostMapping("/")
    public String checkPersonRequest(@Valid PersonRequest personRequest, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "form";
        }

        return "redirect:/results";
    }
}
