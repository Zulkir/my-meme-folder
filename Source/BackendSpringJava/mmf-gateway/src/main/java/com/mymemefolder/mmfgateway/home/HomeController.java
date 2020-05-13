package com.mymemefolder.mmfgateway.home;

import com.mymemefolder.mmfgateway.images.ImageViewData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {
    @GetMapping(path = "/")
    public List<ImageViewData> getHomeImageList() {
        return new ArrayList<>();
    }

    @GetMapping(path = "/api/images-at/")
    public List<ImageViewData> getHomeImageList2() {
        return new ArrayList<>();
    }
}
