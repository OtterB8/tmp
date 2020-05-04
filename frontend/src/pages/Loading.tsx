import React from 'react';
import './Loading.less';
import {Loading} from '@/components';

export default () => {
    return <div className='loading-page'>
        <Loading size={128}/>
    </div>
}