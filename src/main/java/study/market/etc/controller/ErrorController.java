package study.market.etc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error/401")
    public String error401Page() {
        return "/error/401";
    }

    @GetMapping("/error/403")
    public String error403Page() {
        return "/error/403";
    }

}
