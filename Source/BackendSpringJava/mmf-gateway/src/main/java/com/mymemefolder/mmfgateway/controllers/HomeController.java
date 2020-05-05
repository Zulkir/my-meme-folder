package com.mymemefolder.mmfgateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {
    @GetMapping(path = "/")
    public List<ImageWithThumbnail> getHomeImageList() {
        return new ArrayList<>();
    }
}
