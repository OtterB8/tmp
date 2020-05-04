import React, {useEffect} from 'react';
import Loading from '@/pages/Loading.tsx';
import { connect, AuthModelState, history } from 'umi';
import { getToken } from '@/utils/token';
import {actionAuthRequest} from '@/models/actions/auth';

const AuthWrapper = ({info, login, children, getAuthInfo}: any) => {
    useEffect(() => {
        const pathname = history.location.pathname;

        if (info === null) { // if user info is null
            if (getToken() !== null) { // if token exists
                getAuthInfo(); // get user
            } else {
                if (pathname !== '/signin' && pathname !== '/signup') {
                    history.push('/signin');
                }
            }
        } else { // if user info exists go to chat room
            if (pathname !== '/chatroom')
                history.push('/chatroom');
        }
    }, [info, login, history.location.pathname]);

    if (info === null && getToken() !== null) {
        return <Loading />;
    }

    return <React.Fragment>{children}</React.Fragment>
}

const mapStateToProps = ({auth}: {auth: AuthModelState}) => ({
    info: auth.info,
    login: auth.login
});

const mapDispatchToProps = (dispatch: Function) => ({
    getAuthInfo: () => dispatch(actionAuthRequest())
});

export default connect(mapStateToProps, mapDispatchToProps)(AuthWrapper);