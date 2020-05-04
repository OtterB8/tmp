
export const actionGetMessagesRequest = (payload: any) => ({
    type: 'chat/getMessagesRequest',
    payload
});

export const actionGetMessagesSuccess = (payload: any) => ({
    type: 'getMessagesSuccess',
    payload: payload
});

export const actionGetMessagesFail = () => ({
    type: 'getMessagesFail'
});

export const actionSetPartner = (payload: any) => ({
    type: 'setPartner',
    payload
});

export const actionSendMessage = (payload: string) => ({
    type: 'chat/sendMessage',
    payload
});

export const actionReceiveMessage = (payload: any) => ({
    type: 'chat/receiveMessage',
    payload
});

