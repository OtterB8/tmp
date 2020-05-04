import usersService from '@/services/usersService';
import {
    actionGetUsersSuccess, actionGetUsersFail,
    actionUpdateUserStatusSuccess
} from '../actions/users';
import {checkAuthenticated} from './auth';
import {getUsers} from '../selectors/users';
import {getAuthId} from '../selectors/auth';

export const getUsersRequest = function *(action: any, {call, put, select}: {call: Function, put: Function, select: Function}) {
    const isAuthenticated = yield call(checkAuthenticated, select);
    if (!isAuthenticated)
        return;

    const authId = yield select(getAuthId);
    
    try {
        const res = yield call(usersService.getUsers);
        if (res.status === 200) {
            res.data.user[authId].online = true;
            yield put(actionGetUsersSuccess(res.data));
        } else {
            yield put(actionGetUsersFail());
        }
    } catch (e) {
        yield put(actionGetUsersFail());
    }   
}

export const updateUserStatusRequest = function *({payload}: any, {call, put, select}: {call: Function, put: Function, select: Function}) {
    const users = yield select(getUsers);
    if (users[payload.id] === undefined)
        return;

    yield put(actionUpdateUserStatusSuccess({
        ...users,
        [payload.id]: {
            ...users[payload.id],
            online: payload.online
        }
    }));
}