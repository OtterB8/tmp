import {Effect, Reducer} from 'umi';
import {authRequest, signinRequest, signoutRequest} from './effects/auth';
import {removeToken, setToken} from '@/utils/token';
import {history} from 'umi';

export interface AuthInfo {
    id: number,
    name: string,
    email: string,
    phone: string,
    avatar: string
}

export interface AuthModelState {
    info: AuthInfo | null,
    login: {
        result: boolean
    },
    logout: {
        result: boolean
    }
}

export interface AuthModelType {
    namespace: 'auth';
    state: AuthModelState;
    effects: {
        authRequest: [Effect, {type: string}],
        signinRequest: [Effect, {type: string}],
        signoutRequest: [Effect, {type: string}]
    };
    reducers: {
        authSuccess: Reducer<AuthModelState>,
        authFail: Reducer<AuthModelState>,
        signinSuccess: Reducer<AuthModelState>,
        signinFail: Reducer<AuthModelState>,
        signoutSuccess: Reducer<AuthModelState>,
        signoutFail: Reducer<AuthModelState>
    }
}

const AuthModel: AuthModelType = {
    namespace: 'auth',
    state: {
        info: null,
        login: {
            result: false
        },
        logout: {
            result: false
        }
    },
    effects: {
        authRequest: [authRequest, {type: 'takeLatest'}],
        signinRequest: [signinRequest, {type: 'takeLatest'}],
        signoutRequest: [signoutRequest, {type: 'takeLatest'}],
    },
    reducers: {
        authSuccess(state, {payload}) {
            return {
                info: {
                    ...payload
                },
                login: {result: true},
                logout: state ? state.logout: {result: false}
            }
        },
        authFail(state) {
            removeToken();
            history.push('/signin');
            return {
                info: null,
                login: state ? state.login: {result: false},
                logout: state ? state.logout: {result: false}
            };
        },
        signinSuccess(state, {payload}) {
            setToken(payload);
            history.push('/chatroom');
            return {
                info: null,
                login: {result: true},
                logout: state ? state.logout: {result: false}
            }
        },
        signinFail(state) {
            removeToken();
            return {
                info: null,
                login: {result: false},
                logout: state ? state.logout: {result: false}
            };
        },
        signoutSuccess(state) {
            removeToken();
            return {
                info: null,
                login: {result: false},
                logout: {result: true}
            }
        },
        signoutFail(state) {
            return {
                info: state ? state.info : null,
                login: state ? state.login: {result: false},
                logout: {result: false}
            };
        },
    }
}

export default AuthModel;