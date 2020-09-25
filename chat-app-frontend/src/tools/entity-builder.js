/**
 * Given HTTP response (successful status 200) for logging in,
 * return updated User object  
 * @param {*} response 
 */
export default function toUser(response) {
  const data = response.data;
  const [id, username, joinedChatrooms, isLoggedIn] = 
    [data.id, data.username, data.joinedChatrooms, true];
  
    return {id, username, joinedChatrooms, isLoggedIn};
}