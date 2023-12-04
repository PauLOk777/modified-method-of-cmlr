package ua.kpi.ip22mp.trotsiuk.mmcmlr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SingleRunPageController {

    private static final String SINGLE_RUN_PAGE = "singleRun";

    @GetMapping("/")
    public String getMainPage() {
        return SINGLE_RUN_PAGE;
    }
}
