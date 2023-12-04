package ua.kpi.ip22mp.trotsiuk.mmcmlr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MultipleRunsPageController {

    private static final String MULTIPLE_RUNS_PAGE = "multipleRuns";

    @GetMapping("/multiple-runs")
    public String getMultipleRunsPage() {
        return MULTIPLE_RUNS_PAGE;
    }
}
