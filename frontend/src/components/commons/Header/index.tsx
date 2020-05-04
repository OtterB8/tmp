import React, {Fragment} from 'react';
import './index.less';
import { connect, Link, AuthInfo, AuthModelState } from 'umi';
import {Avatar, Modal} from 'antd';
import { WechatOutlined, LogoutOutlined } from '@ant-design/icons';
import {actionSignoutRequest} from '@/models/actions/auth';

const showInfo = (info: AuthInfo | null) => {
  Modal.info({
    title: 'Your profile',
    content: (
      <Fragment>
      <div style={{float: 'left'}}>
        {info && info.avatar === null
        ? <Avatar style={{width: 96, height: 96}} src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />
        : <Avatar style={{width: 96, height: 96}} src={info?.avatar} />}
      </div>
      <div>
        <p>Username: {info?.name}</p>
        <p>Email: {info?.email}</p>
        <p>Phone: {info?.phone}</p>
      </div>
      </Fragment>
    ),
    onOk() {},
  });
}

const Header = ({info, signout}: {info: AuthInfo | null, signout: Function}) => {
  return (
    <div className='header container'>
      <div className='icon'>
        <Link to="/"><Avatar size='large' icon={<WechatOutlined/>} /></Link>
      </div>
      {info !== null
      ? <div className='user'>
        <div className='user-name'>
          <p>{info.name}</p>
        </div>
        <div className='user-avatar' onClick={() => showInfo(info)}>
          {info && info.avatar === null
          ? <Avatar size='large' src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />
          : <Avatar size='large' src={info.avatar} />}
        </div>
        <div className='logout'>
          <LogoutOutlined style={{ fontSize: '24px', color: '#6e0178' }}
            onClick={() => signout()} />
        </div>
      </div>
      : null}
    </div>
  );
}

const mapStateToProps = ({auth}: {auth: AuthModelState}) => ({
  info: auth.info
});

const mapDispatchToProps = (dispatch: Function) => ({
  signout: () => dispatch(actionSignoutRequest())
});

export default connect(mapStateToProps, mapDispatchToProps)(Header);
