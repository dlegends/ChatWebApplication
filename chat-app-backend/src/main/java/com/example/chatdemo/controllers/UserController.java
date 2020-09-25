package com.example.chatdemo.controllers;

import com.example.chatdemo.entities.ChatroomDTO;
import com.example.chatdemo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    /**
     * Get joined chatrooms of the user with given <userId>
     * @param userId the id of user whose joined chatrooms are to be returned
     * @return the joined chatrooms of the user with given <userId>
     */
    @GetMapping(path = "/user/{userId}/chatrooms")
    public Set<ChatroomDTO> getJoinedChatrooms(@PathVariable long userId) {
        return userService.getJoinedChatrooms(userId);
    }

    @GetMapping(path = "/hello")
    public String test() {
        return "Hello";
    }

//    @GetMapping("/test")
//    public List<String> home(Model model, HttpSession session) {
//        @SuppressWarnings("unchecked")
//        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
//
//        if (messages == null) {
//            messages = new ArrayList<>();
//        }
//        model.addAttribute("sessionMessages", messages);
//        model.addAttribute("sessionId", session.getId());
//
//        return messages;
//    }
//
//    @PostMapping("/persistMessage")
//    public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
//        @SuppressWarnings("unchecked")
//        List<String> msgs = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
//        logger.warn("Session Id: " + request.getSession().getId());
//        if (msgs == null) {
//            msgs = new ArrayList<>();
//        }
//        logger.warn(request.toString());
//        msgs.add(msg);
//        request.getSession().setAttribute("MY_SESSION_MESSAGES", msgs);
//        System.out.println("hee");
//        Map<Object, Object> map = redisTemplate.opsForHash()
//                .entries("spring:session:sessions:d6266bbe-0867-4389-9c76-e09e7b14ef2c");
//        System.out.println(map);
//        logger.warn("value is: " + map.get("sessionAttr:MY_SESSION_MESSAGES"));
//        return "hello";
//    }
//
//    @PostMapping("/destroy")
//    public String destroySession(HttpServletRequest request) {
//        request.getSession().invalidate();
//        return "redirect:/test";
//    }
}
