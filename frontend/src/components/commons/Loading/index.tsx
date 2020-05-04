import React from 'react';
import './index.less';
import {LoadingOutlined} from '@ant-design/icons';

export default ({size}: any) => {
  return (
    <div className='loading'>
      <LoadingOutlined style={{fontSize: size || 64}}/>
    </div>
  );
}
