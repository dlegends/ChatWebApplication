import React, { useState, useRef, useEffect, useContext } from 'react';

import { useHistory } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import InputGroup from 'react-bootstrap/InputGroup';

import ChatroomList from './ChatroomList';

import { addChatroom, getFilteredChatroom, getChatrooms } from '../tools/api-utils';

import 'bootstrap/dist/css/bootstrap.min.css';
import FormControl from 'react-bootstrap/FormControl';

import UserContext from '../context/UserContext';

const listViewStyle = {
  display: 'flex',
  flexDirection: 'column',
  height: `100%`,
};

const listHeaderStyle = {
  flex: '1 0 10%',
};

const listStyle = {
  flex: `1 0 90%`,
}

function AddChatroomDialog(props) {
  const chatroomNameRef = useRef('');

  return (
    <>
      <Modal show={props.show}>
        <Modal.Header closeButton>
          <Modal.Title>
            Add Chatroom
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <InputGroup className="mb-3">
            <FormControl
              type="text"
              maxLength={60}
              ref={chatroomNameRef}
              placeholder="Chatroom name" />
          </InputGroup>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => props.handleClose()}>Close</Button>
          <Button variant="primary" onClick={
            () => props.handleAdd(chatroomNameRef.current.value)}>Add</Button>
        </Modal.Footer>
      </Modal>
    </>
  )
}


export default function ChatroomListView(props) {
  const history = useHistory();
  // show modal or not
  const [show, setShow] = useState(false);
  // keep the state of chatroomLists
  const [chatrooms, setChatrooms] = useState([]);
  const [user] = useContext(UserContext);

  // Initialize list of chatrooms when this functional component has mounted
  useEffect(() => {
    getChatrooms(props.displayType, user.id)
      .then(res => {
        setChatrooms(res.data);
      })
      .catch(err => {
        console.log(err);
      });

  }, [props.displayType, user]);

  const handleClose = () => { setShow(false) };
  const handleShow = () => { setShow(true) };
  // handle user creating a chatroom
  const handleAdd = (chatroomName) => {
    addChatroom(chatroomName)
      .then(res => {
        console.log(res);
        setChatrooms([...chatrooms, res.data]);
        setShow(false);
      })
      .catch(err => {
        console.log(err);
        setShow(false);
      });
  };

  const dialog = props.displayType === "all" ? (
    <AddChatroomDialog show={show} handleAdd={handleAdd} handleClose={handleClose} />
  ) : null;

  const onClick = (id) => {
    history.push("/chatrooms/" + id);
  };

  const onSearch = (title) => {
    getFilteredChatroom(title)
      .then(res => {
        setChatrooms(res.data);
      })
  };

  return (
    <div stlye={listViewStyle}>
      <div style={listHeaderStyle}>
        <props.Header handleShow={handleShow} onSearch={onSearch} />
      </div>
      <div style={listStyle}>
        <ChatroomList onClick={onClick} chatrooms={chatrooms} style={listStyle} />
      </div>

      {dialog}

    </div>

  );
}