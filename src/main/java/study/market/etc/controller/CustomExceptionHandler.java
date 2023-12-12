package study.market.etc.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import study.market.etc.config.CustomException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public String handleCustomException(CustomException e, Model model) {

        model.addAttribute("code", e.getErrorCode());
        model.addAttribute("message", e.getMessage());

        return "exception/customForm";
    }
}
