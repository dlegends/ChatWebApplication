import React, { useContext, useRef, useEffect, useState } from 'react';
import UserContext from '../context/UserContext';
import { useHistory } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import Col from 'react-bootstrap/Col';

import HttpStatus from 'http-status-codes';

import { login } from '../tools/api-utils';


function Login() {
  const history = useHistory();

  const [, setUser] = useContext(UserContext);
  const usernameRef = useRef('');
  const passwordRef = useRef('');

  const [invalid, setInvalid] = useState(false);

  useEffect(() => {
    // Ensure user in localstorage exists and user from context is logged in
    // Works because assumption is that both users from localstorage and context is
    // synchronized.
    const user = localStorage.getItem("user");
    if (user && user.token) {
      history.push("/chatrooms");
    }
  }, []);

  // Handle login by interaction with server
  const handleLogin = (event) => {
    event.preventDefault();
    const username = usernameRef.current.value;
    const password = passwordRef.current.value;
    const loginForm = { username, password };
    login(loginForm)
      .then(res => {
        console.log(res);
        localStorage.setItem("user", JSON.stringify(res.data));
        setUser(res.data);
        // Upon success, update user status and redirect
        history.push("/chatrooms");
      })
      .catch(err => {
        console.dir(err);
        if (err.response && err.response.status === HttpStatus.UNAUTHORIZED) {
          setInvalid(true);
        }
      });

  };

  return (
    <Form onSubmit={handleLogin}>
      <Form.Row>
        <Col xs="auto" md={{ span: 2, offset: 5 }}>
          <Form.Group>
            <Form.Label htmlFor="inlineFormInput">Login</Form.Label>
            <Form.Control ref={usernameRef} type="username" placeholder="Enter username" />
            <Form.Control ref={passwordRef} type="password" placeholder="Enter password" />
          </Form.Group>
        </Col>
      </Form.Row>
      <Form.Row>
        <Col xs="auto" md={{ span: 2, offset: 5 }}>
          {invalid ? (
            <p style={{ color: 'red' }}>Username and/or Password Do Not Match</p>
          ) : null}
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

export default Login;