import React, { useContext, useRef, useState } from 'react';
import UserContext from '../context/UserContext';
import { useHistory } from 'react-router-dom';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import { register } from '../tools/api-utils';
import { isValidUsername, isValidPassword } from '../tools/validate-info';


function Register() {
  const history = useHistory();
  const [user] = useContext(UserContext);
  const usernameRef = useRef('');
  const passwordRef = useRef('');

  const [invalidUsername, setInvalidUsername] = useState(false);
  const [invalidPassword, setInvalidPassword] = useState(false);
  // const [password, setPassword] = useState('');
  if (user.token) {
    history.push("/chatrooms");
  }
  const handleRegister = (event) => {
    event.preventDefault();
    const username = usernameRef.current.value;
    const password = passwordRef.current.value;
    const registerForm = { username, password };
    register(registerForm)
      .then(res => {
        history.push("/login");
        return res;
      })
      .catch(err => {
        console.log(err);
      });
  };

  const validateUsername = () => {
    if (isValidUsername(usernameRef.current.value)) {
      setInvalidUsername(false);
    } else {
      setInvalidUsername(true);
    }
  }

  const validatePassword = () => {
    if (isValidPassword(passwordRef.current.value)) {
      setInvalidPassword(false);
    } else {
      setInvalidPassword(true);
    }
  }

  return (
    <Form onSubmit={handleRegister}>
      <Form.Row>
        <Col xs="auto" md={{ span: 2, offset: 5 }}>
          <Form.Group>
            <Form.Label>Register</Form.Label>
            <Form.Control maxLength={15}
              ref={usernameRef}
              onChange={validateUsername}
              type="username"
              placeholder="Enter username" />
            {invalidUsername ? (
              <span style={{ color: 'red' }}>
                Username must contain alphabets and/or numbers and be between 5 and 15 characters
              </span>
            ) : null}
            <Form.Control maxLength={30}
              ref={passwordRef}
              onChange={validatePassword}
              type="password"
              placeholder="Enter password" />
            {invalidPassword ? (
              <span style={{ color: 'red' }}>
                Password must contain alphabets and numbers and be between 6 and 30 characters
              </span>
            ) : null}
          </Form.Group>
        </Col>
      </Form.Row>
      <Form.Row>
        <Col xs="auto" md={{ span: 2, offset: 5 }}>
          <Button variant="primary" type="submit">
            Submit
          </Button>
        </Col>
      </Form.Row>
    </Form>
  )
}

export default Register;