import React from 'react';
import './index.less';
import { connect, AuthModelState, Link } from 'umi';
import {Avatar} from 'antd';
import { WechatOutlined } from '@ant-design/icons';

const Header = (props: any) => {
  return (
    <div className='header container'>
      <div className='icon'>
        <Link to="/"><Avatar size='large' icon={<WechatOutlined/>} /></Link>
      </div>
      {props.auth !== null
      ? <div className='user'>
        <div className='user-name'>
          <p>Huấn Rose</p>
        </div>
        <div className='user-avatar'>
          {props.auth.avatar === null 
          ? <Avatar size='large' src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />
          : <Avatar size='large' src={props.auth.avatar} />}
        </div>
      </div>
      : null}
    </div>
  );
}

export default connect(({auth}: {auth: AuthModelState}) => ({
    auth
}))(Header);
