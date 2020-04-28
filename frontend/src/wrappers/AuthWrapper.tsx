import React from 'react';
import Loading from '@/pages/Loading.tsx';
import { connect, AuthModelState, history } from 'umi';
import {getToken, setToken, removeToken} from '@/utils/token';

const AuthWrapper = (props: any) => {
    // if (props.auth === null) {
    //     console.log('zz hehe')
    //     if (getToken() !== null) {
    //         // get user here

    //     } else {
    //         history.push('/signin');
    //     }
    //     return <Loading />;
    // }
    
    return <React.Fragment>{props.children}</React.Fragment>
}

export default connect(({auth}: {auth: AuthModelState}) => ({
    auth
}))(AuthWrapper);