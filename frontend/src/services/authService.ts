import baseRequest from './baseRequest';
import {SIGNUP_API, SIGNIN_API} from '@/constants/api';

export default {
    signup: (body: any, headers: any = null) => baseRequest.post(SIGNUP_API, body, headers),
    signin: (body: any, headers: any = null) => baseRequest.post(SIGNIN_API, body, headers)
}