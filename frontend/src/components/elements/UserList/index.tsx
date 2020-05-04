import React, {useEffect} from 'react';
import './index.less';
import '@/assets/style/text.less'
import User from './User';
import {connect, UsersModelState} from 'umi';
import {actionGetUsersRequest} from '@/models/actions/users';

const UserList = ({users, getUsers}: any) => {
  useEffect(() => {
      getUsers();
  }, []);

  return (
    <div className='users text-center'>
      {users.get.failed
      ? <p className='error text-red text-bold'>Server error! Cannot get users</p>
      : users.list.map((id: number) => {
          return <User key={id} id={id}
                    name={users.user[id].name}
                    avatar={users.user[id].avatar}
                    online={users.user[id].online}/>
        }
      )}
    </div>
  );
}

const mapStateToProps = ({users}: {users: UsersModelState}) => ({
  users
});

const mapDispatchToProps = (dispatch: Function) => ({
  getUsers: () => dispatch(actionGetUsersRequest())
});

export default connect(mapStateToProps, mapDispatchToProps)(UserList);