import React from 'react';
import './ChatRoom.less';
import { Row, Col } from 'antd';
import {UserList, ChatList, Chat} from '@/components';

const ChatRoom = () => {
    return <div className='chatroom'>
        <Row>
            <Col span={4}>
                <UserList />
            </Col>
            <Col span={14}>
                <Chat />
            </Col>
            <Col span={6}>
                <ChatList />
            </Col>
        </Row>
    </div>
}

export default ChatRoom;