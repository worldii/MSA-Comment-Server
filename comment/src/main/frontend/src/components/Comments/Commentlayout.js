import React from 'react';
import styles from './Commentlayout.module.scss';

function Commentlayout({ commentitem }) {
  console.log(commentitem);
  const description = commentitem.description;
  const user = commentitem.from;
  const likes = commentitem.likes;
  return (
    <div className={styles.layout}>
      <div className={styles.writer}>
        {user.userName} @{user.fullName}
      </div>
      <div className={styles.content}>{description}</div>
    </div>
  );
}

export default Commentlayout;
