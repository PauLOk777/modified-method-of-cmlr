package ua.kpi.ip22mp.trotsiuk.mmcmlr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    private static final String MAIN_PAGE = "main";

    @GetMapping("/")
    public String getMainPage() {
        return MAIN_PAGE;
    }
}
