import React, { useContext } from 'react';

import UserContext from '../context/UserContext';

import './chat-messages.css';

export default function ChatMessages({ messages }) {
  const [user] = useContext(UserContext);

  return (
    <>
      {messages.map(msg => {
        const messageStyle = msg.sender.id === user.id ? "sending-message"
          : "receiving-message";
        const container = msg.sender.id === user.id ? "sending"
          : "receiving";
        const usernameStyle = msg.sender.id === user.id ? "sending-username"
                                                        : "receiving-username"
        return (
          <div className={container} key={msg.id}>
            <div className={usernameStyle}>
              {msg.sender.username}
            </div>
            <div className={messageStyle + " message"}>
              {msg.content}
            </div>
          </div>

        );
      })}
    </>
  );
}