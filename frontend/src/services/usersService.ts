import baseRequest from './baseRequest';
import {USERS_API} from '@/constants/api';

export default {
    getUsers: () => baseRequest.get(USERS_API)
}