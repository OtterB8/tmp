import axios from 'axios';
import {getToken} from '@/utils/token';

export default {
    get(url: string, headers: any = null, withCredentials = true) {
        if (withCredentials) {
            if (!headers)
                headers = {};
            headers['Authorization'] = 'Bearer ' + getToken();
        }

        return axios.get(url, {headers});
    },
    post(url: string, body: any = null, headers: any = null, withCredentials = true) {
        if (withCredentials) {
            if (!headers)
                headers = {};
            headers['Authorization'] = 'Bearer ' + getToken();
        }

        return axios.post(url, body, {headers})
    }
}