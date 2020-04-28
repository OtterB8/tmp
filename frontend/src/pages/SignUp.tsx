import React from 'react';
import { Form, Input, Button } from 'antd';
import authService from '@/services/authService';
import {Link} from 'umi';
import './SignUp.less';

const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 16 },
};

export default () => {
    const onSubmit = async (values: any) => {
        const res = await authService.signup(values.user);
        if (res.status === 200) {
            alert("Sign up success!");
        } else {
            alert("Sign up failed!");
        }
    }

    return <div className='signup'>
        <Form {...layout} name="nest-messages" onFinish={onSubmit}>
            <Form.Item name={['user', 'username']} label="Username" rules={[{ required: true, message: 'Please input your username!' }]} >
                <Input />
            </Form.Item>
            <Form.Item name={['user', 'password']} label="Password" rules={[{ required: true, message: 'Please input your password!' }]} >
                <Input.Password />
            </Form.Item>
            <Form.Item name={['user', 'email']} label="Email" rules={[{ required: true, type: 'email', message: 'Please input your email!' }]}>
                <Input />
            </Form.Item>
            <Form.Item name={['user', 'phone']} label="Phone" rules={[{ required: true, message: 'Please input your phone number!' }]}>
                <Input />
            </Form.Item>
            <Form.Item wrapperCol={{ ...layout.wrapperCol, offset: 8 }}>
                <Button type="primary" htmlType="submit">
                    Sign Up
                </Button>
            </Form.Item>
        </Form>
        <p>Already have an account? <Link to='/signin'>Sign In</Link></p>
    </div>
}