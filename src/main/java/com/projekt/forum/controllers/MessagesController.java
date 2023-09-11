package com.projekt.forum.controllers;

import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.dataTypes.dto.MessageDTO;
import com.projekt.forum.dataTypes.projection.ThreadProjection;
import com.projekt.forum.repositories.MessageRepository;
import com.projekt.forum.repositories.ThreadRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class MessagesController {
    private final AlertManager alertManager;
    private final MessageRepository messageRepository;
    private final ThreadRepository threadRepository;
    //private final

    public MessagesController(AlertManager alertManager, MessageRepository messageRepository, ThreadRepository threadRepository) {
        this.alertManager = alertManager;
        this.messageRepository = messageRepository;
        this.threadRepository = threadRepository;
    }

    @GetMapping("/thread/{categoryUrl}/{threadId}")
    String viewMessages(@PathVariable() Integer threadId, Model model, @PathVariable String categoryUrl){
        if (threadId==null){
            model.addAttribute("atr_title", "Error");
            alertManager.addAlert(new Alert("Wybrano wątek w niepoprawny sposób", Alert.AlertType.WARNING));
        }
        else {
            Optional<ThreadProjection> thread = threadRepository.findByIdThread(threadId);
            if (thread.isEmpty()) {
                model.addAttribute("atr_title", "Error");
                alertManager.addAlert(new Alert("Wybrany wątek nie istnieje :<", Alert.AlertType.DANGER));
            }
            else
            {
                model.addAttribute("atr_title", thread.get().getTopic());

                model.addAttribute("atr_threadID", thread.get().getIdThread());
//                for(MessageDTO message: messageRepository.findByThreadEntity_IdThread(threadId))
//                {
//                    System.out.print(message.getContent()+" ");
//                    System.out.print(message.getIdMessage()+" ");
//                    System.out.print(message.getCreationDate()+" ");
//                    System.out.print(message.getAuthorName());
//                    System.out.println("s");
//                }
                model.addAttribute("atr_messages", messageRepository.findByThreadEntity_IdThread(threadId));

            }

        }
        model.addAttribute("atr_alertManager", alertManager);
        return "MessagesInThread";
    }
}
