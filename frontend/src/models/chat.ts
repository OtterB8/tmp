import {Effect, Reducer} from 'umi';
import {getMessagesRequest, sendMessageRequest} from './effects/chat';
import {CHAT_STATUS} from '@/constants/status';

export interface MessageType {
    id: number,
    roomId: string,
    sender: number,
    text: string,
    timeStamp: number
}

export interface ChatModelState {
    messages: MessageType[],
    status: string,
    partnerId: number | null | undefined,
    roomId: string | null | undefined
}

export interface ChatModelType {
    namespace: 'chat';
    state: ChatModelState;
    effects: {
        getMessagesRequest: [Effect, {type: string}],
        sendMessage: [Effect, {type: string}]
    };
    reducers: {
        reset: Reducer<ChatModelState>,
        getMessagesStatus: Reducer<ChatModelState>,
        setPartner: Reducer<ChatModelState>,
        getMessagesSuccess: Reducer<ChatModelState>,
        getMessagesFail: Reducer<ChatModelState>,
        receiveMessage: Reducer<ChatModelState>,
    }
}

const ChatModel: ChatModelType = {
    namespace: 'chat',
    state: {
        messages: [],
        status: CHAT_STATUS.VOID,
        partnerId: null,
        roomId: null
    },
    effects: {
        getMessagesRequest: [getMessagesRequest, {type: 'takeLatest'}],
        sendMessage: [sendMessageRequest, {type: 'takeLatest'}]
    },
    reducers: {
        reset() {
            return {
                messages: [],
                status: CHAT_STATUS.VOID,
                partnerId: null,
                roomId: null
            }
        },
        getMessagesStatus(state, {payload}) {
            return {
                messages: state ? state.messages : [],
                status: payload,
                partnerId: state?.partnerId,
                roomId: state?.roomId
            }
        },
        setPartner(state, {payload}) {
            return {
                messages: [],
                status: CHAT_STATUS.GETTING,
                partnerId: payload.partnerId,
                roomId: payload.roomId
            }
        },
        getMessagesSuccess(state, {payload}) {
            return {
                messages: payload,
                status: CHAT_STATUS.SUCCESS,
                partnerId: state?.partnerId,
                roomId: state?.roomId
            }
        },
        getMessagesFail(state) {
            return {
                messages: [],
                status: CHAT_STATUS.ERROR,
                partnerId: state?.partnerId,
                roomId: state?.roomId
            };
        },
        receiveMessage(state, {payload}) {
            return {
                messages: state ? [...state.messages, payload] : [payload],
                status: state ? state.status : CHAT_STATUS.SUCCESS,
                partnerId: state?.partnerId,
                roomId: state?.roomId
            };
        }
    }
}

export default ChatModel;