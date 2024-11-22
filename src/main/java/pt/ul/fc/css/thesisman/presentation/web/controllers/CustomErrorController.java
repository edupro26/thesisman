package pt.ul.fc.css.thesisman.presentation.web.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_SERVLET_NAME);
        if (statusCode != null) {
            if (statusCode == 404) {
                return "error/404";
            }
        }
        return "error/error";
    }

    public String getErrorPath() {
        return "/error";
    }
}
