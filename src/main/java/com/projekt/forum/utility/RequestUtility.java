package com.projekt.forum.utility;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class RequestUtility {
    public static String AjaxInsertParam = "Ajax_insert_param";
    public static String OperationInsert = "INSERT_PAGE";
    public static String OperationReplace = "NEW_PAGE";
    public static String AjaxNewPageUrlParamName = "Ajax_redirection";


    public static void setupAjaxRedirectionHeaders(HttpServletResponse httpServletResponse, String path){
        httpServletResponse.addHeader(RequestUtility.AjaxInsertParam, RequestUtility.OperationReplace);
        httpServletResponse.setHeader("Ajax_redirection", ServletUriComponentsBuilder.fromCurrentRequestUri().replacePath(null).build().toString().concat(path));

    }
    public static void setupAjaxRedirectionHeaders(HttpServletResponse httpServletResponse){
        setupAjaxRedirectionHeaders(httpServletResponse,"");
    }

    public static void setupAjaxInsertionHeaders(HttpServletResponse httpServletResponse){
        httpServletResponse.addHeader(RequestUtility.AjaxInsertParam, RequestUtility.OperationInsert);
    }

}
