import React from 'react';
import {Header} from '../components';

export default (props: any) => {
    return <div>
        <Header/>
        <h1>Layout</h1>
        {props.children}
    </div>
}