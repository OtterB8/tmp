import React, {Fragment} from 'react';
import './index.less';
import '@/assets/style/text.less'
import {Avatar} from 'antd';
import {connect, UsersModelState, ChatModelState} from 'umi';
import {actionGetMessagesRequest} from '@/models/actions/chat';

const ChatBox = ({users, id, lastMessage, partnerId, getMessages}: any) => {
  const colorClass = partnerId === id ? 'chosen' : '';

  return (
    <div className={`chatbox ${colorClass}`} onClick={() => getMessages({partnerId: id})}>
      {users[id] &&
      <Fragment>
        <Avatar src={users[id].avatar ? users[id].avatar
          : "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"} />
        <div className='display-box text-start'>
          <div className='name text-bold'>
            <p>{users[id].name}</p>
          </div>
          <div className='last-message'>
            <p>{lastMessage}</p>
          </div>
        </div>
      </Fragment>}
    </div>
  );
}

const mapStateToProps = ({users, chat}: {users: UsersModelState, chat: ChatModelState}) => ({
  users: users.user,
  partnerId: chat.partnerId
});

const mapDispatchToProps = (dispatch: Function) => ({
  getMessages: (payload: any) => dispatch(actionGetMessagesRequest(payload))
});

export default connect(mapStateToProps, mapDispatchToProps)(ChatBox);