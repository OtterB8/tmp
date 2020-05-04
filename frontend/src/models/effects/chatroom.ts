import {disconnect} from '@/utils/websocket';
import { actionReceiveMessage } from '../actions/chat';
import { actionUpdateRequest } from '../actions/chatlist';
import { actionUpdateUserStatusRequest, actionAddNewUser } from '../actions/users';
import {TYPE_MESSAGE, TYPE_ERROR, TYPE_USER_STATUS, TYPE_NEW_USER} from '@/constants/messageType';
import {getRoomId} from '@/models/selectors/chat';
import {getAnotherUser} from '@/utils/chatroom';

export const disconnectSocket = function *() {
    try {
        disconnect();
    } catch (e) {}
}

export const processMessage = function *({payload}: any, {call, put, select}: {call: Function, put: Function, select: Function}) {
    const data = JSON.parse(payload);

    const roomId = yield select(getRoomId);

    switch (data.type) {
        case TYPE_MESSAGE:
            if (data.body.roomId === roomId) {
                yield put(actionReceiveMessage(data.body));
            }
            yield put(actionUpdateRequest({
                body: data.body,
                user1: data.body.sender,
                user2: getAnotherUser(data.body.sender, data.body.roomId)
            }));
            break;
        case TYPE_USER_STATUS:
            yield put(actionUpdateUserStatusRequest({
                id: data.id,
                online: data.online
            }))
            break;
        case TYPE_NEW_USER:
            yield put(actionAddNewUser(data.user));
        case TYPE_ERROR:
            break;
    }
}