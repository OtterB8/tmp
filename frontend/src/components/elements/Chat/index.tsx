import React, {Fragment, useEffect} from 'react';
import './index.less';
import '@/assets/style/text.less';
import {connect, ChatModelState, MessageType, AuthModelState, UsersModelState} from 'umi';
import {Form, Input, Button, Avatar} from 'antd';
import {Message} from '@/components';
import { actionSendMessage } from '@/models/actions/chat';

const Chat = ({messages, status, partnerId, sendMessage, authId, users}: any) => {
  const [form] = Form.useForm();

  useEffect(() => {
    const chatFrame = document.getElementById('chat-frame');
    if (chatFrame) {
      chatFrame.scrollTop = chatFrame.scrollHeight;
    }
  });

  const onFinish = (values: any) => {
    sendMessage(values.message);
    form.resetFields();
    document.getElementById("message")?.focus();
  };

  return (
    <div className='chat'>
      {partnerId && users[partnerId]
      ? <Fragment>
      <div className='partner'>
        <Avatar src={users[partnerId].avatar ? users[partnerId].avatar : "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"} />
        <div className='display-info text-start'>
          <div className='name text-bold'>
            <p>{users[partnerId].name}</p>
          </div>
          <div className='status'>
            <p className={users[partnerId].online ? 'text-green' : 'text-red'}>
              {users[partnerId].online ? 'online' : 'offline'}
            </p>
          </div>
        </div>
      </div>
      <div className='messages' id='chat-frame'>
        {messages.map((message: MessageType) => {
          return <Message key={message.id} text={message.text}
            className={message.sender === authId ? 'owner' : ''}/>})}
      </div>
      <div className='texting'>
      <Form form={form} layout="inline" onFinish={onFinish}>
        <Form.Item name="message" rules={[{required: true, message: ''}]}>
          <Input placeholder="Message"/>
        </Form.Item>
        <Form.Item shouldUpdate={true}>
          {() => (
            <Button type="primary" htmlType="submit">
              Send
            </Button>
          )}
        </Form.Item>
      </Form>
      </div>
      </Fragment>
      : null}
    </div>
  );
}

const mapStateToProps = ({chat, auth, users}: {chat: ChatModelState, auth: AuthModelState, users: UsersModelState}) => ({
  messages: chat.messages,
  status: chat.status,
  partnerId: chat.partnerId,
  authId: auth.info?.id,
  users: users.user
});

const mapDispatchToProps = (dispatch: Function) => ({
  sendMessage: (message: string) => dispatch(actionSendMessage(message))
});

export default connect(mapStateToProps, mapDispatchToProps)(Chat);