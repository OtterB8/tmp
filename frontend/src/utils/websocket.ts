import Stomp, {Frame} from 'stompjs';
import SockJS from 'sockjs-client';
import {SOCKET_ENDPOINT} from '@/constants/api';
import { getToken } from '@/utils/token';

let socket = null;
let stompClient: Stomp = null;

export const connectToServer = (onReceived: Function) => {
    if (stompClient != null)
        stompClient.disconnect();

    socket = new SockJS(SOCKET_ENDPOINT);
    stompClient = Stomp.over(socket);

    stompClient.connect({
        "Authorization": `Bearer ${getToken()}`
    }, (frame: Frame) => {
        onReceived(frame);
        stompClient.subscribe('/topic/userstatus', onReceived);
        stompClient.subscribe('/topic/newuser', onReceived);
        stompClient.subscribe('/user/queue/message', onReceived);
    }, onReceived);
}

export const disconnect = () => {
    stompClient.disconnect();
}

export const sendMessage = (message: string, sender: number, receiver: number, roomId: string) => {
    if (stompClient === null)
        return;

    stompClient.send('/app/chatting', {}, JSON.stringify({
        sender: sender,
        receiver: receiver,
        roomId: roomId,
        message: message
    }));
}