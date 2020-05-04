import baseRequest from './baseRequest';
import {MESSAGES_API, CHAT_LIST_API} from '@/constants/api';

export default {
    getMessages: (id: string) => baseRequest.get(`${MESSAGES_API}?id=${id}`),
    getChatList: () => baseRequest.get(CHAT_LIST_API)
}