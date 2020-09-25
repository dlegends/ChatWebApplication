import React from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';

import Login from './Components/Login';
import Logout from './Components/Logout';
import Register from './Components/Register';
import ChatroomListView from './Components/ChatroomListView';
import { AllChatroomListHeader, UserChatroomListHeader } from './Components/ChatroomListHeaders';
import Chatroom from './Components/Chatroom';

// Below two functions ensure components are rendered depending on user's logged in status
// and redirected when logged-in status is not met

const ensureLogin = (props, Component, customProps = {}) => {
	const user = JSON.parse(localStorage.getItem("user"));
	if (user && user.token) {
		console.log("rendering");
		return <Component {...props} {...customProps} />;
	}
	else {
		return <Redirect to="/login" />;
	}
};

const ensureLogout = (props, Component, customProps = {}) => {
	const user = JSON.parse(localStorage.getItem("user"));
	if (user && user.token) {
		return <Redirect to="/chatrooms" />;
	}
	else {
		return <Component {...props} {...customProps} />;
	}
};

function InvalidLink() {
	return (
		<h1>Invalid Link</h1>
	)
}

function ProtectedRoute({ isAllowed, ...props }) {
	return isAllowed ? <Route {...props} /> : <Redirect to="/login" />;
}

function Routes() {
	return (
		<Switch>
			<Route exact path="/login" render={(props) => ensureLogout(props, Login)} />
			<Route exact path="/register" render={(props) => ensureLogout(props, Register)} />
			<Route exact path="/logout" render={(props) => ensureLogin(props, Logout)} />
			<Route exact path="/chatrooms/:chatroomId"
				render={(props) => ensureLogin(props, Chatroom)} />
			<Route exact path="/chatrooms" render={
				(props) => ensureLogin(props, ChatroomListView, {
					displayType: 'all',
					Header: AllChatroomListHeader,
				})
			} />

			<Route exact path="/user/chatrooms" render={
				(props) => ensureLogin(props, ChatroomListView, {
					displayType: 'user',
					Header: UserChatroomListHeader,
				})
			} />

			<Route>
				<InvalidLink />
			</Route>
		</Switch>
	);
}

export default Routes;