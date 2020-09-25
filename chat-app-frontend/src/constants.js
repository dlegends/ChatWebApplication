export const headers = {
  'Content-Type': 'application/json',
  'Accept': 'application/json',
};

/**
 * id: int
 * username: string
 * isLoggedIn: boolean
 * 
 * 
 */
export const UserState = {
  token: null,
  id: null,
}

export const API_URL = process.env.HTTP_SERVER_URL || "http://localhost:8080";
export const WS_API_URL = process.env.WS_SERVER_URL || "ws://localhost:8080/ws";
