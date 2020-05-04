import {Effect, Reducer} from 'umi';
import {getBoxesRequest, updateRequest} from './effects/chatlist';

export interface RoomInfo {
    id: string,
    user1: number,
    user2: number,
    lastMessage: string,
    timeStamp: number
}

export interface RoomModelState {
    room: {
        [id: string]: RoomInfo
    },
    list: string[],
    get: {failed: boolean}
}

export interface RoomModelType {
    namespace: 'chatlist';
    state: RoomModelState;
    effects: {
        getBoxesRequest: [Effect, {type: string}],
        updateRequest: [Effect, {type: string}],
    };
    reducers: {
        updateSuccess: Reducer<RoomModelState>,
        getBoxesSuccess: Reducer<RoomModelState>,
        getBoxesFail: Reducer<RoomModelState>,
    }
}

const RoomModel: RoomModelType = {
    namespace: 'chatlist',
    state: {
        room: {},
        list: [],
        get: {failed: false}
    },
    effects: {
        getBoxesRequest: [getBoxesRequest, {type: 'takeLatest'}],
        updateRequest: [updateRequest, {type: 'takeLatest'}]
    },
    reducers: {
        getBoxesSuccess(state, {payload}) {
            return {
                room: payload.room,
                list: payload.list,
                get: {failed: false}
            }
        },
        getBoxesFail(state) {
            return {
                room: state ? state.room : {},
                list: state ? state.list : [],
                get: {failed: true}
            };
        },
        updateSuccess(state, {payload}) {
            return {
                room: payload.room,
                list: payload.list,
                get: state ? state.get : {failed: false}
            }
        },
    }
}

export default RoomModel;