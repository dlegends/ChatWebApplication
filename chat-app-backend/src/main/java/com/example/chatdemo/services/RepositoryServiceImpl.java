package com.example.chatdemo.services;

import com.example.chatdemo.entities.*;
import com.example.chatdemo.exceptions.*;
import com.example.chatdemo.repositories.ChatMessageRepository;
import com.example.chatdemo.repositories.ChatroomRepository;
import com.example.chatdemo.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Set;
import java.util.stream.Collectors;

@Component
class RepositoryServiceImpl implements RepositoryService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatroomRepository chatroomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(
            RepositoryServiceImpl.class);

    @Override
    public Set<ChatroomDTO> getJoinedChatrooms(Long userId) {
        Set<Chatroom> joinedChatrooms = userRepository
                .findById(userId) // Find User entity
                .orElseThrow(() -> new UserNotFoundException(userId))
                                                            // throw error if not found
                .getJoinedChatrooms();
        return joinedChatrooms
                .stream()
                .map(this::toChatroomDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public ChatroomDTO createChatroom(String title) {
        Chatroom chatroom = new Chatroom();
        chatroom.setTitle(title);
        return toChatroomDTO(chatroomRepository.save(chatroom));
    }

    @Override
    public Set<ChatroomDTO> getChatrooms() {
        return chatroomRepository.findAll()
                .stream()
                .map(this::toChatroomDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public ChatroomDTO getChatroom(final Long id) {
        return toChatroomDTO(chatroomRepository.findById(id)
                .orElseThrow(() -> new ChatroomNotFoundException(id)));
    }

    @Override
    public Set<UserDTO> getParticipants(Long chatroomId) {
        // We find chatroom by <chatroomId>, validate it exists,
        // map its participants to DTOs, and return
        return chatroomRepository
                .findById(chatroomId)
                .orElseThrow(
                        () -> new ChatroomNotFoundException(chatroomId))
                .getParticipants().stream()
                .map(this::toUserDTO).collect(Collectors.toSet());
    }

    /**
     * Add user to chatroom with <chatroomId>
     * Precondition: <user> is not in chatroom with given <chatroomId>
     * @param chatroomId id of a chatroom to add the user to
     * @param userId id of a user being added to the chatroom
     * @return updated chatroom with given chatroomId
     */
    @Override
    public Set<UserDTO> addUserToChatroom(final Long chatroomId, Long userId) {
        Chatroom chatroom = chatroomRepository.findById(chatroomId)
                .orElseThrow(() -> new ChatroomNotFoundException(chatroomId));
        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        // if (user.id in {chatroom.participants.id's}) throw new Exception
        chatroom.getParticipants().add(foundUser);
        foundUser.getJoinedChatrooms().add(chatroom);
        return chatroomRepository.save(chatroom)
                .getParticipants()
                .stream()
                .map(this::toUserDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Remove participant with <userId> from chatroom with <chatroomId>.
     * Throw error if either chatroom with <chatroomId> doesn't exist or
     * participant with <userId> doesn't exist in the chatroom
     * @param chatroomId the id of chatroom
     * @param userId the id of participant
     */
    @Override
    public void removeParticipant(Long chatroomId, Long userId) {
        Chatroom chatroom = chatroomRepository.findById(chatroomId).orElseThrow(
                () -> new ChatroomNotFoundException(chatroomId)
        );
        User participant = chatroom.getParticipants()
                .stream()
                .filter(ptcpt -> ptcpt.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundInChatroomException(chatroomId, userId));
        chatroom.getParticipants().remove(participant);
        chatroomRepository.save(chatroom);
    }

    @Override
    public ChatMessageDTO saveChatMessage(ChatMessageDTO chatMessageDTO) {
        final Long userId = chatMessageDTO.getSender().getId();
        final Long chatroomId = chatMessageDTO.getChatroomId();
        User sender = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Chatroom chatroom = chatroomRepository.findById(chatroomId)
                .orElseThrow(() -> new ChatroomNotFoundException(chatroomId));

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(chatMessageDTO.getContent());
        chatMessage.setChatroom(chatroom);
        chatMessage.setSender(sender);
        chatMessage.setSentOn(chatMessageDTO.getSentOn());

        ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);
        chatMessageDTO.setId(savedChatMessage.getId());
        chatMessageDTO.setSender(toUserDTO(sender));
        return chatMessageDTO;
    }

    @Override
    public Set<ChatMessageDTO> getChatMessages(Long chatroomId) {
        return chatroomRepository
                .findById(chatroomId)
                .orElseThrow(
                        () -> new ChatroomNotFoundException(chatroomId))
                .getChatMessages()
                .stream()
                .map(this::toChatMessageDTO).collect(Collectors.toSet());
    }

    @Override
    public Set<ChatroomDTO> getFilteredChatrooms(String title) {
        return chatroomRepository
                .findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::toChatroomDTO).collect(Collectors.toSet());
    }

    private UserDTO toUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private ChatMessageDTO toChatMessageDTO(ChatMessage chatMessage) {
        ChatMessageDTO chatMessageDTO = modelMapper.map(chatMessage, ChatMessageDTO.class);
        chatMessageDTO.setSender(toUserDTO(chatMessage.getSender()));
        chatMessageDTO.setChatroomId(chatMessage.getChatroom().getId());
        return chatMessageDTO;
    }

    private ChatroomDTO toChatroomDTO(Chatroom chatroom) {
        ChatroomDTO chatroomDTO = modelMapper.map(chatroom, ChatroomDTO.class);
        chatroom.getChatMessages().forEach(chatMessage ->
                chatroomDTO.getChatMessages().add(toChatMessageDTO(chatMessage))
        );
        chatroom.getParticipants().forEach(user -> chatroomDTO
                .getParticipants().add(toUserDTO(user))
        );
        return chatroomDTO;
    }
}
