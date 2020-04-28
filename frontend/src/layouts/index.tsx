import React from 'react';
import {Header} from '@/components';
import './index.less';

export default (props: any) => {
    return <div className='layout'>
        <Header/>
        <div className='layout-children'>
            {props.children}
        </div>
    </div>
}