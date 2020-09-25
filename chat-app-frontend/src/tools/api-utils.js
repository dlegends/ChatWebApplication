import { headers, API_URL } from '../constants';

import axios from 'axios';

const updateAuth = () => {
  const user = JSON.parse(localStorage.getItem("user"));
  if (!user) return null;
  headers['Authorization'] = "Bearer " + user.token;
}

const removeAuth = () => {
  delete headers['Authorization'];
}

export function addChatroom(chatroomName) {
  updateAuth();
  return axios.post(API_URL + "/chatrooms", { title: chatroomName }, { headers });
}


export function leaveChatroom(chatroomId, userId) {
  updateAuth();
  return axios.delete(API_URL + "/chatrooms/" + chatroomId + "/participants/" + userId,
    { headers })
}

export function getFilteredChatroom(title) {
  updateAuth();
  let url = API_URL + "/chatrooms/search";
  if (title !== "") {
    url += "?q=" + title;
  }
  return axios.get(url, { headers })
}

export function register(registerForm) {
  removeAuth();
  return axios.post(API_URL + '/register', registerForm, { headers })
}

export function login(loginForm) {
  removeAuth();
  return axios.post(API_URL + "/login", loginForm, { headers })
}

export function logout(userId) {
  updateAuth();
  return axios.post(API_URL + "/loggoutt", userId, { headers })
}

export function getChatMessages(chatroomId) {
  updateAuth();
  return axios.get(API_URL + "/chatrooms/" + chatroomId + "/messages",
    { headers })
}

export function addUserToChatroom(chatroomId, userId) {
  updateAuth();
  return axios.post(API_URL + "/chatrooms/" + chatroomId + "/participants",
    userId, { headers })
}

export function getParticipants(chatroomId) {
  updateAuth();
  return axios.get(API_URL + "/chatrooms/" + chatroomId + "/participants", { headers })

}

function getJoinedChatrooms(userId) {
  updateAuth();
  return axios.get(API_URL + "/user/" + userId + "/chatrooms", { headers });
}

export function getChatrooms(displayType, userId) {
  if (displayType === "all") {
    return getFilteredChatroom("");
  } else {
    return getJoinedChatrooms(userId);
  }
}

export function getChatroom(chatroomId) {
  updateAuth();
  return axios.get(API_URL + "/chatrooms/" + chatroomId, { headers });
}