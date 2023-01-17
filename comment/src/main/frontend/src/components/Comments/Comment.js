import React from 'react';
import Commentlayout from './Commentlayout';
import styles from './Comment.module.scss';
import { useState } from 'react';
import axios from 'axios';

const Comment = ({ postId, refreshfunc }) => {
  const [comment, setComment] = useState('');

  const handleClickComment = (e) => {
    setComment(e.currentTarget.value);
  };

  const onSubmit = (event) => {
    event.preventDefault();
    let body = {
      description: comment,
    };
    axios.post(`/comments/${postId}`, body).then((response) => {
      console.log(response);
      setComment('');
      refreshfunc(postId);
    });
  };
  return (
    <div>
      <form className={styles.formLayout} onSubmit={onSubmit}>
        <div className={styles.commentLayout} style={{ marginLeft: '40px' }}>
          <textarea
            className={styles.comment}
            placeholder="댓글을 입력해주세요"
            onChange={handleClickComment}
            value={comment}
          ></textarea>
          <button className={styles.button} onClick={onSubmit}>
            등록
          </button>
        </div>
      </form>{' '}
    </div>
  );
};

export default Comment;
