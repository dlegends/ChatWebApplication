import React from 'react';
import Button from 'react-bootstrap/Button';

import ChatroomSearch from './ChatroomSearch';

import 'bootstrap/dist/css/bootstrap.min.css';

const row = {
  display: 'flex',
}

const rightEnd = {
  marginLeft: 'auto',
  marginRight: '20px',
}

export function AllChatroomListHeader({ handleShow, onSearch }) {
  return (
    <div className="border-bottom" style={row}>
      <h1 className="ml-3">Chatrooms</h1>
      <Button className="ml-5" variant="outline-primary" onClick={handleShow}>
        Add Chatroom
      </Button>
      <div style={rightEnd}>
        <ChatroomSearch onSearch={onSearch} />
      </div>
    </div>
  )
}

export function UserChatroomListHeader({ onSearch }) {
  return (
    <div className="border-bottom" style={row}>
      <h1 className="ml-3">Joined Chatrooms</h1>
      <div style={rightEnd}>
        <ChatroomSearch onSearch={onSearch} />
      </div>
    </div>
  )
} 