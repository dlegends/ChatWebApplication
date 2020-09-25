import React, { useRef } from 'react';

import UserContext from '../context/UserContext';
import { addUserToChatroom, getChatroom, getChatMessages, leaveChatroom } from '../tools/api-utils';
import { WS_API_URL } from '../constants';

import ChatMessages from './ChatMessages';


import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import Button from 'react-bootstrap/Button';

import { Client } from '@stomp/stompjs';

import 'bootstrap/dist/css/bootstrap.min.css';
import './chatroom.css';

function ChatroomUsers({ participants }) {
  return (
    <>
      <h3 className="border-bottom" style={{ textAlign: 'center', fontFamily: 'Times New Roman, Times, serif' }}>
        Participants
      </h3>
      {participants.map(participant => (
        <div className="user-info" key={participant.id}>
          {participant.username}
        </div>
      ))}
    </>
  )
}

function MessageInput({ onClick }) {
  const messageRef = useRef('');

  return (
    <InputGroup style={{ height: '100%' }}>
      <FormControl
        type="text"
        placeholder="Enter message"
        aria-label="Message"
        aria-describedby="basic-addon2"
        maxLength={100}
        style={{ height: '100%' }}
        ref={messageRef}
      />
      <InputGroup.Append>
        <Button variant="outline-secondary"
          onClick={() => {
            onClick(messageRef.current.value);
            messageRef.current.value = '';
          }}>Send</Button>
      </InputGroup.Append>

    </InputGroup>
  )
}

/**
 * Can safely assume when Chatroom is rendered, it means user is 
 * logged in
 */
export default class Chatroom extends React.Component {
  static contextType = UserContext;

  constructor(props, context) {
    super(props);
    this.state = {
      id: null, // int
      title: "",
      chatMessages: [], // Array({id: int, content: string, senderId: int, chatroomId: int})
      participants: [], // Array({id: int, username: string, 
      //        joinedChatrooms: Array(int), isLoggedIn: boolean})
    };

    // Get URL parameters
    this.params = this.props.match.params;

    this.addUser = this.addUser.bind(this);
    this.onChatroomLoad = this.onChatroomLoad.bind(this);
    this.loadChatMessages = this.loadChatMessages.bind(this);
    this.sendMessage = this.sendMessage.bind(this);
    this.setUpStompClient = this.setUpStompClient.bind(this);
    this.leave = this.leave.bind(this);

    this.setUpStompClient();

    // Start loading chatroom data from server
    getChatroom(this.params.chatroomId)
      .then(res => {
        console.log(res);
        this.setState(res.data, this.onChatroomLoad);
      })
      .catch(err => {
        console.log(err);
        // On error, return back to chatrooms list
        this.props.history.push("/chatrooms");
      });
  }

  setUpStompClient() {
    // Below, we create settings and callbacks for
    // our STOMP client.

    // Since our state hasn't be intialized, we must use this.params.chatroomId
    // instead of this.state.id

    const messageCallback = (message) => {
      if (message.body) {
        const chatMessage = JSON.parse(message.body);
        this.setState({
          chatMessages: [...this.state.chatMessages, chatMessage],
        });
      }
    }

    const webSocketURL = WS_API_URL;
    this.client = new Client({
      brokerURL: webSocketURL,
      connectHeaders: {
        login: "guest",
        passcode: "guest",
      },
      debug: function (str) {
        console.log("debug: " + str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    const self = this;

    this.client.onConnect = (frame) => {
      console.log("connected");
      this.client.subscribe("/topic/chatrooms/" + self.params.chatroomId, messageCallback);
    };

    this.client.onStompError = (frame) => {
      console.log("error");
      self.props.params.history.push("/chatrooms");
    };

    this.client.activate();

  }

  componentWillUnmount() {
    this.client.deactivate();
  }

  onChatroomLoad() {
    console.log(this.state);
    this.addUser();
    this.loadChatMessages();
  }

  addUser() {
    const [user] = this.context;
    // Check if this user has not yet joined the chatroom he is entering
    if (!this.state.participants.some(ptcpt => ptcpt.id === user.id)) {
      addUserToChatroom(this.params.chatroomId, user.id)
        .then(res => {
          console.log(res);
          // response is the updated chatroom with this user added
          // So here, we update the user's joinedChatrooms property
          // by pushing this chatroom to it
          this.setState({
            participants: res.data,
          });
        })
        .catch(err => {
          console.log("error");
          console.log(err.response);
        });
    }
  }

  loadChatMessages() {
    // request data at /chatrooms/:chatroomId/messages
    getChatMessages(this.params.chatroomId)
      .then(res => {
        this.setState({
          chatmessages: res.data,
        });
      })
      .catch(err => {
        console.log("error");
        console.log(err.response);
      });
  }

  sendMessage(message) {
    if (message.length > 0) {
      const [user] = this.context;
      const sender = { id: user.id, username: null };
      // have to send string, so stringify data before
      const simpleChatMessage = JSON.stringify({
        id: null,
        content: message,
        sender: sender,
        chatroomId: this.state.id,
        sentOn: null,
      });
      // The backend websocket should update backend resources as it receives
      // new message. So we don't need to make another request to add chatmessage
      // resource.
      this.client.publish({ destination: '/app/chatrooms/' + this.state.id, body: simpleChatMessage });
    }
  }

  leave() {
    const [user] = this.context;
    leaveChatroom(this.state.id, user.id)
      .then(res => {
        this.props.history.push("/chatrooms");
      })
      .catch(err => {
        console.log(err);
      });
  }

  render() {
    const chatroom = this.state;
    return (
      <div className="outer-container">
        <div className="user-container">
          <Button variant="primary" onClick={this.leave}>Leave</Button>
          <ChatroomUsers participants={chatroom.participants} />
        </div>
        <div className="border-left ml-2 chat-and-input-container">
          <div className="chat-container">
            <ChatMessages messages={chatroom.chatMessages} />
          </div>
          <div className="input-container">
            <MessageInput onClick={this.sendMessage} />
          </div>

        </div>

      </div>
    );
  }
}

