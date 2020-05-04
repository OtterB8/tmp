import authService from '@/services/authService';
import {
    actionAuthSuccess, actionAuthFail,
    actionSigninSuccess, actionSigninFail,
    actionSignoutSuccess, actionSignoutFail
} from '../actions/auth';
import {
    actionDisconnect
} from '../actions/chatroom';
import {getAuthStatus} from '@/models/selectors/auth';

export const authRequest = function *(action: any, {call, put}: {call: Function, put: Function}) {
    try {
        const res = yield call(authService.getInfo);
        if (res.status === 200) {
            yield put(actionAuthSuccess(res.data));
        } else {
            yield put(actionAuthFail());
        }
    } catch (e) {
        yield put(actionAuthFail());
    }
    
}

export const signinRequest = function *(action: any, {call, put}: {call: Function, put: Function}) {
    try {
        const res = yield call(authService.signin, action.payload);
        if (res.status === 200) {
            yield put(actionSigninSuccess(res.data.accessToken));
        } else {
            yield put(actionSigninFail());
        }
    } catch(e) {
        yield put(actionSigninFail());
    }
}

export const signoutRequest = function *(action: any, {call, put}: {call: Function, put: Function}) {
    yield put (actionDisconnect());
    yield put(actionSignoutSuccess());
}

export const checkAuthenticated = function *(select: Function) {
    return yield select(getAuthStatus);
}