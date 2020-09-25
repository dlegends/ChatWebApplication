import React, { useState, useContext } from 'react';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { Link } from 'react-router-dom';

import UserContext from '../context/UserContext';
import Routes from '../Routes';
import { UserState } from '../constants';

import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';


const page = {
	display: 'flex',
	flexDirection: 'column',
	height: '100%',
	maxWidth: `100%`,
}

const header = {
	flex: `0 0 auto`,
}

const body = {
	flex: `1 0 90%`,
	maxHeight: `90%`,
	maxWidth: `100%`,
}

function Header({ style }) {
	const [user] = useContext(UserContext);
	const loginOptions = user.token ? (
		<Nav className="navbar-right">
			<NavDropdown title="Profile" id="basic-nav-dropdown" className="mr-3">
				<NavDropdown.Item as={Link} to="/user/chatrooms">Joined Chatrooms</NavDropdown.Item>
			</NavDropdown>
			<Nav.Link as={Link} to="/logout">Logout</Nav.Link>
		</Nav>
	) : (
			<>
				<Nav className="navbar-right">
					<Nav.Link as={Link} to="/login">Login</Nav.Link>
				</Nav>
				<Nav className="navbar-right">
					<Nav.Link as={Link} to="/register">Register</Nav.Link>
				</Nav>
			</>
		);
	return (
		<Navbar bg="light" expand="lg" style={style}>
			<Navbar.Brand href="/home">Chat Anywhere</Navbar.Brand>
			<Navbar.Toggle aria-controls="basic-navbar-nav" />
			<Navbar.Collapse id="basic-navbar-nav">
				<Nav className="mr-auto">
					<Nav.Link as={Link} to="/chatrooms">Chatrooms</Nav.Link>
				</Nav>
				{loginOptions}
			</Navbar.Collapse>
		</Navbar>
	)
}


function App() {
	const userState = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user"))
		: UserState;
	const userHook = useState(userState);

	return (
		<UserContext.Provider value={userHook} style={page}>
			<div style={page}>
				<div style={header}>
					<Header />
				</div>
				<div style={body}>
					<Routes />
				</div>
			</div>
		</UserContext.Provider>

	);
}

export default App;