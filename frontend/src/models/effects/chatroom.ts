import {disconnect} from '@/utils/websocket';
import {
    actionReceiveMessage,
    actionReset as actionResetChat
} from '../actions/chat';
import {
    actionUpdateRequest,
    actionReset as actionResetChatlist
} from '../actions/chatlist';
import {
    actionUpdateUserStatusRequest,
    actionAddNewUser,
    actionReset as actionResetUsers
} from '../actions/users';
import {
    actionReset as actionResetAuth
} from '../actions/auth';
import {
    actionReset as actionResetChatroom
} from '../actions/chatroom';
import {TYPE_MESSAGE, TYPE_ERROR, TYPE_USER_STATUS, TYPE_NEW_USER} from '@/constants/messageType';
import {getRoomId} from '@/models/selectors/chat';
import {getAnotherUser} from '@/utils/chatroom';

export const resetState = function *(action: any, {put}: {put: Function}) {
    yield put(actionResetAuth());
    yield put(actionResetUsers());
    yield put(actionResetChat());
    yield put(actionResetChatlist());
    yield put(actionResetChatroom());
}

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
            console.log('zz websocket error')
            break;
    }
}