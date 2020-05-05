import {Effect, Reducer} from 'umi';
import {getUsersRequest, updateUserStatusRequest} from './effects/users';

export interface UserInfo {
    id: number,
    name: string,
    avatar: string,
    online: boolean
}

export interface UsersModelState {
    user: {
        [id: number]: UserInfo
    },
    list: number[],
    get: {failed: boolean}
}

export interface UsersModelType {
    namespace: 'users';
    state: UsersModelState;
    effects: {
        getUsersRequest: [Effect, {type: string}],
        updateUserStatusRequest: [Effect, {type: string}],
    };
    reducers: {
        reset: Reducer<UsersModelState>,
        getUsersSuccess: Reducer<UsersModelState>,
        getUsersFail: Reducer<UsersModelState>,
        updateUserStatusSuccess: Reducer<UsersModelState>,
        addNewUser: Reducer<UsersModelState>
    }
}

const UsersModel: UsersModelType = {
    namespace: 'users',
    state: {
        user: [],
        list: [],
        get: {failed: false}
    },
    effects: {
        getUsersRequest: [getUsersRequest, {type: 'takeLatest'}],
        updateUserStatusRequest: [updateUserStatusRequest, {type: 'takeLatest'}]
    },
    reducers: {
        reset() {
            return {
                user: [],
                list: [],
                get: {failed: false}
            };
        },
        getUsersSuccess(state, {payload}) {
            return {
                user: payload.user,
                list: payload.list,
                get: {failed: false}
            }
        },
        getUsersFail(state) {
            return {
                user: state ? state.user : [],
                list: state ? state.list : [],
                get: {failed: true}
            };
        },
        updateUserStatusSuccess(state, {payload}) {
            return {
                user: payload,
                list: state ? state.list : [],
                get: state ? state.get : {failed: false}
            }
        },
        addNewUser(state, {payload}) {
            return {
                user: state
                    ? {[payload.id]: payload, ...state.user}
                    : {[payload.id]: payload},
                list: state ? [payload.id, ...state.list] : [payload.id],
                get: state ? state.get : {failed: false}
            }
        },
    }
}

export default UsersModel;