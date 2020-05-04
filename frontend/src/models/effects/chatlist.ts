import chatService from '@/services/chatService';
import {
    actionGetBoxesSuccess, actionGetBoxesFail,
    actionUpdateSuccess
} from '../actions/chatlist';
import {checkAuthenticated} from './auth';
import {getChatlist} from '../selectors/chatlist';
import {moveFirst, sortChatBoxes} from '@/utils/chatroom';

export const getBoxesRequest = function *(action: any, {call, put, select}: {call: Function, put: Function, select: Function}) {
    const isAuthenticated = yield call(checkAuthenticated, select);
    if (!isAuthenticated)
        return;
    
    try {
        const res = yield call(chatService.getChatList);
        if (res.status === 200) {
            sortChatBoxes(res.data, 0, res.data.list.length - 1);
            yield put(actionGetBoxesSuccess(res.data));
        } else {
            yield put(actionGetBoxesFail());
        }
    } catch (e) {
        yield put(actionGetBoxesFail());
    }   
}

export const updateRequest = function *({payload}: any, {call, put, select}: {call: Function, put: Function, select: Function}) {
    const chatList = yield select(getChatlist);

    if (chatList.room[payload.body.roomId] !== undefined) {
        yield put(actionUpdateSuccess({
            room: {
                ...chatList.room,
                [payload.body.roomId]: {
                    id: payload.body.roomId,
                    user1: payload.user1,
                    user2: payload.user2,
                    lastMessage: payload.body.text,
                    timeStamp: payload.body.timeStamp,
                }
            },
            list: moveFirst([...chatList.list], payload.body.roomId)
        }));
    } else {
        yield put(actionUpdateSuccess({
            room: {
                ...chatList.room,
                [payload.body.roomId]: {
                    id: payload.body.roomId,
                    user1: payload.user1,
                    user2: payload.user2,
                    lastMessage: payload.body.text,
                    timeStamp: payload.body.timeStamp,
                }
            },
            list: [payload.body.roomId, ...chatList.list],
        }));
    }
}