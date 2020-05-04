import {Effect, Reducer, Subscription} from 'umi';
import {disconnectSocket, processMessage} from './effects/chatroom';
import {CHATROOM_STATUS} from '@/constants/status';
import {connectToServer} from '@/utils/websocket';
import {
    actionConnectSuccess, actionConnectFail,
    actionProcessMessage
} from './actions/chatroom';

export interface ChatRoomModelState {
    status: string
}

export interface ChatRoomModelType {
    namespace: 'chatroom';
    state: ChatRoomModelState;
    effects: {
        disconnect: [Effect, {type: string}],
        processMessage: [Effect, {type: string}],
    };
    reducers: {
        connectSuccess: Reducer<ChatRoomModelState>,
        connectFail: Reducer<ChatRoomModelState>,
    },
    subscriptions: {
        websocket: Subscription
    }
}

const ChatRoomModel: ChatRoomModelType = {
    namespace: 'chatroom',
    state: {
        status: CHATROOM_STATUS.VOID,
    },
    effects: {
        disconnect: [disconnectSocket, {type: 'takeLatest'}],
        processMessage: [processMessage, {type: 'takeLatest'}],
    },
    reducers: {
        connectSuccess(state) {
            return {
                status: CHATROOM_STATUS.SUCCESS,
            }
        },
        connectFail(state) {
            return {
                status: CHATROOM_STATUS.ERROR,
            };
        },
    },
    subscriptions: {
        websocket({dispatch, history}) {
            return history.listen(({ pathname }) => {
                if (pathname === '/chatroom') {
                    connectToServer((frame: any) => {
                        const command = (typeof frame === 'string') ? frame: frame.command;
                        switch (command) {
                            case 'CONNECTED':
                                dispatch(actionConnectSuccess());
                                break;
                            case 'ERROR':
                                dispatch(actionConnectFail());
                                break;
                            case 'MESSAGE':
                                dispatch(actionProcessMessage(frame.body));
                                break;
                        }
                    });
                }
            });
        }
    }
}

export default ChatRoomModel;