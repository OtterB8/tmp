import {checkAuthenticated} from './auth';
import {getPartnerId, getRoomId} from '@/models/selectors/chat';
import {sendMessage} from '@/utils/websocket';
import chatService from '@/services/chatService';
import {
    actionGetMessagesSuccess, actionGetMessagesFail,
    actionSetPartner
} from '../actions/chat';
import {genRoomId} from '@/utils/chatroom';
import {getAuthId} from '@/models/selectors/auth';

export const getMessagesRequest = function *({payload}: any, {call, put, select}: {call: Function, put: Function, select: Function}) {
    const authId = yield select(getAuthId);
    const roomId = genRoomId(payload.partnerId, authId);
    try {
        yield put(actionSetPartner({partnerId: payload.partnerId, roomId}));
        const res = yield call(chatService.getMessages, roomId);
        if (res.status === 200) {
            yield put(actionGetMessagesSuccess(res.data));
        } else {
            yield put(actionGetMessagesFail());
        }
    } catch (e) {
        yield put(actionGetMessagesFail());
    }   
}

export const sendMessageRequest = function *({payload}: any, {call, select}: {call: Function, select: Function}) {
    const isAuthenticated = yield call(checkAuthenticated, select);
    if (!isAuthenticated)
        return;

    const sender = yield select(getAuthId);
    const receiver = yield select(getPartnerId);
    const roomId = yield select(getRoomId);

    try {
        sendMessage(payload, sender, receiver, roomId);
    } catch (e) {}
}