package com.projekt.forum.handlers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class DBExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DataAccessResourceFailureException.class})
    protected String handleDataAccessFailure(RuntimeException ex, WebRequest webRequest, Model model, HttpServletResponse httpServletResponse){
        //model.addAttribute("atr_errMSG", "Niestety Mamy obecnie komplikacje :<");
        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return "Error";
    }

}
