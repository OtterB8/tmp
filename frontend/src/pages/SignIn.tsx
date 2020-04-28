import React from 'react';
import { Form, Input, Button } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import authService from '@/services/authService';
import {setToken} from '@/utils/token';
import {Link, history} from 'umi';
import './SignIn.less';

export default (props: any) => {
    const onFinish = async (values: any) => {
        const res = await authService.signin(values);
        if (res.status === 200) {
            alert("Sign in success!");
            setToken(res.data.accessToken);
            history.push('/chatroom');
        } else {
            alert("Sign in failed!");
        }
    };

    return <div className="signin">
        <Form name="normal_login" className="login-form" initialValues={{ remember: true }} onFinish={onFinish}>
            <Form.Item name="username" rules={[{ required: true, message: 'Please input your Username!' }]}>
                <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Username" />
            </Form.Item>
            <Form.Item name="password" rules={[{ required: true, message: 'Please input your Password!' }]}>
                <Input prefix={<LockOutlined className="site-form-item-icon" />} type="password" placeholder="Password"/>
            </Form.Item>

            <Form.Item>
                <Button type="primary" htmlType="submit" className="login-form-button">
                Log in
                </Button>
            </Form.Item>
        </Form>
        <p>Don't have an account? <Link to='/signup'>Sign Up</Link></p>
    </div>
}