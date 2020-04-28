import {Effect, Reducer} from 'umi';

export interface AuthModelState {
    id: number;
}

export interface AuthModelType {
    namespace: 'auth';
    state: AuthModelState | null;
    effects: {
        authRequest: Effect
    };
    reducers: {
        authSuccess: Reducer<AuthModelState>;
    }
}

const AuthModel: AuthModelType = {
    namespace: 'auth',
    state: null,
    effects: {
        *authRequest({payload}, {call, put}) {

        }
    },
    reducers: {
        authSuccess(state, {payload}) {
            return {
                id: payload.id
            }
        }
    }
}

export default AuthModel;