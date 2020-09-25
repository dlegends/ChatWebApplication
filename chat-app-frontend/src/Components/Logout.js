import React, { useEffect, useContext } from 'react';

import { logout } from '../tools/api-utils';

import { useHistory } from 'react-router-dom';
import UserContext from '../context/UserContext';


export default function Logout() {
  const history = useHistory();
  const [, setContextUser] = useContext(UserContext);
  useEffect(() => {
    localStorage.clear();
    setContextUser({
      token: null,
      id: null
    });
    history.push("/login");
  }, [history, setContextUser]);

  return <> </>;
}