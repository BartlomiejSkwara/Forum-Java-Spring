package com.projekt.forum.controllers;

import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.dataTypes.forms.MessagePostForm;
import com.projekt.forum.dataTypes.projection.ThreadProjection;
import com.projekt.forum.entity.ThreadEntity;
import com.projekt.forum.repositories.MessageRepository;
import com.projekt.forum.repositories.ThreadRepository;
import com.projekt.forum.services.MessageService;
import com.projekt.forum.utility.RequestUtility;
import com.projekt.forum.utility.ValidationUtility;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class MessagesController {
    private final AlertManager alertManager;
    private final MessageRepository messageRepository;
    private final ThreadRepository threadRepository;
    private final MessageService messageService;
    private final ValidationUtility validationUtility;
    private final HttpServletResponse httpServletResponse;

    @Autowired
    public MessagesController(AlertManager alertManager, MessageRepository messageRepository, ThreadRepository threadRepository, MessageService messageService, ValidationUtility validationUtility, HttpServletResponse httpServletResponse) {
        this.alertManager = alertManager;
        this.messageRepository = messageRepository;
        this.threadRepository = threadRepository;
        this.messageService = messageService;
        this.validationUtility = validationUtility;
        this.httpServletResponse = httpServletResponse;
    }

    @GetMapping("/thread/{categoryUrl}/{threadId}")
    String displayMessages(
                        @PathVariable() Integer threadId,
                        Model model,
                        @PathVariable String categoryUrl
                        ){
        if (threadId==null){
            alertManager.addAlert(new Alert("Wybrano wątek w niepoprawny sposób", Alert.AlertType.DANGER));
            return "redirect:/error";

        }
        else {
            Optional<ThreadProjection> thread = threadRepository.findProjectionByIdThread(threadId);
            if (thread.isEmpty()) {
                alertManager.addAlert(new Alert("Wybrany wątek nie istnieje :<", Alert.AlertType.DANGER));
                return "redirect:/error";
            }
            else
            {
                model.addAttribute("atr_title", thread.get().getTopic());

                model.addAttribute("atr_threadID", threadId);
                model.addAttribute("atr_categoryUrl", categoryUrl);

                model.addAttribute("atr_messages", messageService.getMessagesByTopic(threadId,0));
                //model.addAttribute("atr_alertManager", alertManager);
                return "MessagesInThread";
            }

        }

    }


    @PostMapping("/thread/{categoryUrl}/{threadId}")
    String displayPage(@PathVariable() Integer threadId, Model model,
                           @PathVariable() String categoryUrl,
                        @RequestParam(value = "page",defaultValue = "0") Integer page
                           ){
        if (threadId==null){
            alertManager.addAlert(new Alert("Wybrano wątek w niepoprawny sposób", Alert.AlertType.DANGER));
            RequestUtility.setupAjaxRedirectionHeaders(httpServletResponse,"/error");

            return "Blank";

        }
        else {
            Optional<ThreadProjection> thread = threadRepository.findProjectionByIdThread(threadId);
            if (thread.isEmpty()) {
                alertManager.addAlert(new Alert("Wybrany wątek nie istnieje :<", Alert.AlertType.DANGER));
                RequestUtility.setupAjaxRedirectionHeaders(httpServletResponse,"/error");

                return "Blank";
            }
            else
            {
                model.addAttribute("atr_title", thread.get().getTopic());

                model.addAttribute("atr_threadID", threadId);
                model.addAttribute("atr_categoryUrl", categoryUrl);

                model.addAttribute("atr_messages", messageService.getMessagesByTopic(threadId,page));
                model.addAttribute("atr_alertManager", alertManager);
                RequestUtility.setupAjaxInsertionHeaders(httpServletResponse);

                return "MessagesInThread :: threadContent";
            }

        }

    }

    @PostMapping(path = "/postMessage/{categoryUrl}/{threadId}")
    public String postMessage(@PathVariable( required = false, name = "threadId") Integer threadId,Model model,
                              @Valid() @ModelAttribute() MessagePostForm messagePostForm,
                              BindingResult bindingResult,
                              @AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable() String categoryUrl,
                              @RequestParam(value = "page",defaultValue = "0") Integer lastPage
    ){
        Optional<ThreadEntity> currentThread  =  threadRepository.findEntityByIdThread(threadId);
        if(threadId==null || currentThread.isEmpty()){
            //Może zniknąć i nie pokazać się na Error
            alertManager.addAlert(new Alert("Próbowałeś dodać wiadomość do niestniejącego wątku", Alert.AlertType.DANGER));
            RequestUtility.setupAjaxRedirectionHeaders(httpServletResponse,"/error");

            return "Blank";
        }

        if (validationUtility.ConvertValidationErrors(bindingResult,alertManager)){
            if(messageService.saveMessage(threadId,messagePostForm,userDetails.getUsername(), currentThread.get())) {
                model.addAttribute("atr_threadID", threadId);
                if((currentThread.get().getMessageCount())%messageService.getPageSize()==1){
                    lastPage++;
                }
                model.addAttribute("atr_messages", messageService.getMessagesByTopic(threadId,lastPage));
                model.addAttribute("atr_categoryUrl", categoryUrl);

                //model.addAttribute("atr_messages", messageRepository.findByThreadEntity_IdThread(threadId));
                model.addAttribute("atr_alertManager", alertManager);
                RequestUtility.setupAjaxInsertionHeaders(httpServletResponse);
                return "MessagesInThread :: threadContent";
            }

            alertManager.addAlert(new Alert("Wystąpił błąd podczas dodawania kategorii", Alert.AlertType.DANGER));
            RequestUtility.setupAjaxRedirectionHeaders(httpServletResponse,"/error");

            return "Blank";

        }

        model.addAttribute("atr_alertManager", alertManager);

        model.addAttribute("atr_messages", messageService.getMessagesByTopic(threadId,lastPage));
        model.addAttribute("atr_categoryUrl", categoryUrl);

        //model.addAttribute("atr_messages", messageRepository.findByThreadEntity_IdThread(threadId));
        model.addAttribute("atr_threadID", threadId);
        RequestUtility.setupAjaxInsertionHeaders(httpServletResponse);
        return "MessagesInThread :: threadContent";

    }

}
