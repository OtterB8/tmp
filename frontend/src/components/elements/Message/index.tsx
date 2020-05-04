import React from 'react';
import './index.less';

export default ({text, className}: any) => {
  return (
    <div className={`message ${className}`}>
      <p>{text}</p>
    </div>
  );
}
