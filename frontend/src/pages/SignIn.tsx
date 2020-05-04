import React from 'react';
import './SignIn.less';
import { Form, Input, Button } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { Link,connect } from 'umi';
import { actionSigninRequest } from '@/models/actions/auth';

const SignIn = ({signin}: any) => {
    const onFinish = async (values: any) => {
        signin(values);
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

const mapDispatchToProps = (dispatch: Function) => ({
    signin: (credentials: any) => dispatch(actionSigninRequest(credentials))
});

export default connect(null, mapDispatchToProps)(SignIn);