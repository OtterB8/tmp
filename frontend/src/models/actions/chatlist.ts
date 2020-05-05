
export const actionReset = () => ({
    type: 'chatlist/reset'
});

export const actionGetBoxesRequest = () => ({
    type: 'chatlist/getBoxesRequest'
});

export const actionGetBoxesSuccess = (payload: any) => ({
    type: 'getBoxesSuccess',
    payload: payload
});

export const actionGetBoxesFail = () => ({
    type: 'getBoxesFail'
});

export const actionUpdateRequest = (payload:any) => ({
    type: 'chatlist/updateRequest',
    payload
});

export const actionUpdateSuccess = (payload: any) => ({
    type: 'updateSuccess',
    payload: payload
});