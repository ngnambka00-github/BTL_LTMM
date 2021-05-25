package com.ngnam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/")
public class TrangChuController {
    @GetMapping
    public String getDefault() {
        return "trang_chu";
    }
}
