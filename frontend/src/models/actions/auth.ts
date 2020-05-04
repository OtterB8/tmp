
export const actionAuthRequest = () => ({
    type: 'auth/authRequest'
});

export const actionAuthSuccess = (payload: any) => ({
    type: 'authSuccess',
    payload: payload
});

export const actionAuthFail = () => ({
    type: 'authFail'
});

export const actionSigninRequest = (payload: {username: string, password: string}) => ({
    type: 'auth/signinRequest',
    payload: payload
});

export const actionSigninSuccess = (payload: string) => ({
    type: 'signinSuccess',
    payload: payload
});

export const actionSigninFail = () => ({
    type: 'signinFail'
});

export const actionSignoutRequest = () => ({
    type: 'auth/signoutRequest'
});

export const actionSignoutSuccess = () => ({
    type: 'signoutSuccess'
});

export const actionSignoutFail = () => ({
    type: 'signinFail'
});