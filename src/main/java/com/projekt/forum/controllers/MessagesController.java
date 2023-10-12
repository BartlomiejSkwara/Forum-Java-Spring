package com.projekt.forum.controllers;

import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.dataTypes.forms.MessagePostForm;
import com.projekt.forum.dataTypes.projection.ThreadProjection;
import com.projekt.forum.repositories.MessageRepository;
import com.projekt.forum.repositories.ThreadRepository;
import com.projekt.forum.services.MessageService;
import com.projekt.forum.utility.RequestUtility;
import com.projekt.forum.utility.ValidationUtility;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class MessagesController {
    private final AlertManager alertManager;
    private final MessageRepository messageRepository;
    private final ThreadRepository threadRepository;
    private final MessageService messageService;
    private final ValidationUtility validationUtility;
    private final HttpServletResponse httpServletResponse;

    public MessagesController(AlertManager alertManager, MessageRepository messageRepository, ThreadRepository threadRepository, MessageService messageService, ValidationUtility validationUtility, HttpServletResponse httpServletResponse) {
        this.alertManager = alertManager;
        this.messageRepository = messageRepository;
        this.threadRepository = threadRepository;
        this.messageService = messageService;
        this.validationUtility = validationUtility;
        this.httpServletResponse = httpServletResponse;
    }

    @GetMapping("/thread/{categoryUrl}/{threadId}")
    String viewMessages(@PathVariable() Integer threadId, Model model, @PathVariable String categoryUrl){
        if (threadId==null){
            model.addAttribute("atr_title", "Error");
            alertManager.addAlert(new Alert("Wybrano wątek w niepoprawny sposób", Alert.AlertType.WARNING));
        }
        else {
            Optional<ThreadProjection> thread = threadRepository.findProjectionByIdThread(threadId);
            if (thread.isEmpty()) {
                model.addAttribute("atr_title", "Error");
                alertManager.addAlert(new Alert("Wybrany wątek nie istnieje :<", Alert.AlertType.DANGER));
            }
            else
            {
                model.addAttribute("atr_title", thread.get().getTopic());

                model.addAttribute("atr_threadID", threadId);

                model.addAttribute("atr_messages", messageRepository.findByThreadEntity_IdThread(threadId));

            }

        }
        model.addAttribute("atr_alertManager", alertManager);
        return "MessagesInThread";
    }

    @PostMapping(path = "/postMessage/{threadId}")
    public String postMessage(@PathVariable( required = false, name = "threadId") Integer threadId,Model model,
                              @Valid() @ModelAttribute() MessagePostForm messagePostForm,
                              BindingResult bindingResult,
                              @AuthenticationPrincipal UserDetails userDetails
    ){

            if(threadId==null){
            //Może zniknąć i nie pokazać się na Error
            alertManager.addAlert(new Alert("Próbowałeś dodać wiadomość do niestniejącego wątku", Alert.AlertType.DANGER));
            RequestUtility.setupAjaxRedirectionHeaders(httpServletResponse,"/error");

            return "Blank";
        }

        if (validationUtility.ConvertValidationErrors(bindingResult,alertManager)){
            if(messageService.saveMessage(threadId,messagePostForm,userDetails.getUsername())) {
                model.addAttribute("atr_threadID", threadId);
                model.addAttribute("atr_messages", messageRepository.findByThreadEntity_IdThread(threadId));
                model.addAttribute("atr_alertManager", alertManager);
                RequestUtility.setupAjaxInsertionHeaders(httpServletResponse);
                return "MessagesInThread :: threadContent";
            }

            //Może zniknąć i nie pokazać się na Error
            alertManager.addAlert(new Alert("Wystąpił błąd podczas dodawania kategorii", Alert.AlertType.DANGER));
            RequestUtility.setupAjaxRedirectionHeaders(httpServletResponse,"/error");

            return "Blank";

        }

        model.addAttribute("atr_alertManager", alertManager);
        model.addAttribute("atr_messages", messageRepository.findByThreadEntity_IdThread(threadId));
        model.addAttribute("atr_threadID", threadId);
        RequestUtility.setupAjaxInsertionHeaders(httpServletResponse);
        return "MessagesInThread :: threadContent";

    }

}
