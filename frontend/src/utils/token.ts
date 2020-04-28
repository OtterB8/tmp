import {TOKEN_KEY} from '@/constants/common';

export const setToken = (value: string) => {
    localStorage.setItem(TOKEN_KEY, value);
}

export const getToken = () => {
    return localStorage.getItem(TOKEN_KEY);
}

export const removeToken = () => {
    localStorage.removeItem(TOKEN_KEY);
}