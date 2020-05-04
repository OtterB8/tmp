import {UserInfo} from 'umi';

export const actionGetUsersRequest = () => ({
    type: 'users/getUsersRequest'
});

export const actionGetUsersSuccess = (payload: any) => ({
    type: 'getUsersSuccess',
    payload: payload
});

export const actionGetUsersFail = () => ({
    type: 'getUsersFail'
});

export const actionUpdateUserStatusRequest = (payload: {id: number, online: boolean}) => ({
    type: 'users/updateUserStatusRequest',
    payload
});

export const actionUpdateUserStatusSuccess = (payload: UserInfo[]) => ({
    type: 'users/updateUserStatusSuccess',
    payload
});

export const actionAddNewUser = (payload: UserInfo[]) => ({
    type: 'users/addNewUser',
    payload
});