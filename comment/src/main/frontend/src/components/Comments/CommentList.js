import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import Comment from './Comment';
import styles from './Comment.module.scss';
import Comment2 from './Comment';
import Commentlayout from './Commentlayout';
const CommentList = () => {
  const postId = useParams().postId;
  const [commentList, setCommentList] = useState([]);
  const refreshComment = async (id) => {
    getDetail();
  };
  const getDetail = () => {
    axios.get(`/comments/${postId}`).then((response) => {
      console.log(response.data.data);
      setCommentList(response.data.data);
    });
  };

  useEffect(() => {
    axios.get(`/comments/${postId}`).then((response) => {
      // console.log(response.data.data);
      setCommentList(response.data.data);
    });
  }, []);

  return (
    <div>
      <div>
        {commentList.map((item, index) => (
          <div>
            <Commentlayout commentitem={item}></Commentlayout>
          </div>
        ))}
        <Comment postId={postId} refreshfunc={refreshComment}></Comment>
      </div>
    </div>
  );
};

export default CommentList;
