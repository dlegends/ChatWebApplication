import { createContext } from 'react';
import { UserState } from '../constants';

const UserContext = createContext(UserState);

export default UserContext;