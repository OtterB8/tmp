import React, {useEffect} from 'react';
import './index.less';
import '@/assets/style/text.less';
import {connect, RoomModelState, AuthModelState, UsersModelState} from 'umi';
import {actionGetBoxesRequest} from '@/models/actions/chatlist';
import ChatBox from './ChatBox';

const UserList = ({chatlist, getBoxes, authId}: any) => {
  useEffect(() => {
      getBoxes();
  }, []);

  return (
    <div className='chatlist text-center'>
      {chatlist.get.failed
      ? <p className='error text-red text-bold'>Server error! Cannot get chat boxes</p>
      : chatlist.list.map((id: string) => 
        chatlist.room[id] && <ChatBox key={id} lastMessage={chatlist.room[id].lastMessage}
            id={chatlist.room[id].user1 === authId
              ? chatlist.room[id].user2
              : chatlist.room[id].user1} />
      )}
    </div>
  );
}

const mapStateToProps = ({chatlist, auth}: {chatlist: RoomModelState, auth: AuthModelState}) => ({
  chatlist,
  authId: auth.info?.id
});

const mapDispatchToProps = (dispatch: Function) => ({
  getBoxes: () => dispatch(actionGetBoxesRequest())
});

export default connect(mapStateToProps, mapDispatchToProps)(UserList);