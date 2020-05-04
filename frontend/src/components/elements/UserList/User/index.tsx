import React, {useState} from 'react';
import './index.less';
import '@/assets/style/text.less'
import {Avatar} from 'antd';
import {connect, AuthModelState, ChatModelState} from 'umi';
import {actionGetMessagesRequest} from '@/models/actions/chat';

const User = ({id, avatar, name, online, getMessages, chosenId}: any) => {
  const statusClass = online ? 'text-green' : 'text-red';
  const colorClass = chosenId == id ? 'chosen' : '';

  return (
    <div className={`user ${colorClass}`} onClick={() => getMessages({partnerId: id})}>
      <Avatar src={avatar ? avatar : "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"} />
      <div className='display-info text-start'>
        <div className='name text-bold'>
          <p>{name}</p>
        </div>
        <div className='status'>
          <p className={statusClass}>{online ? 'online' : 'offline'}</p>
        </div>
      </div>
    </div>
  );
}

const mapStateToProps = ({auth, chat}: {auth: AuthModelState, chat: ChatModelState}) => ({
  chosenId: chat.partnerId
});

const mapDispatchToProps = (dispatch: Function) => ({
  getMessages: (payload: any) => dispatch(actionGetMessagesRequest(payload))
});

export default connect(mapStateToProps, mapDispatchToProps)(User);