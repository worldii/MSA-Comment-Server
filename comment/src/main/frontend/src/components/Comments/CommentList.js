import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const CommentList = () => {
  const postId = useParams().postId;
  const [commentList, setCommentList] = useState([]);

  useEffect(() => {
    console.log(postId);
    // const getCommentList = async () => {
    //   const data = await axios.get(`/comments/${postId}`);
    //   console.log(data);
    //   return data.data.data;
    // };
    axios.get(`/comments/${postId}`).then((response) => {
      console.log(response.data.data);
      setCommentList(response.data.data);
    });

    // console.log(getCommentList);
    // getCommentList().then((result) =>
    //   setCommentList([...commentList, ...result])
    // );

    // console.log(getCommentList());
  }, []);
  return (
    <div>
      <div>
        {commentList.map((item, index) => (
          <div>
            <div>{item.from.fullName}</div>
            <div>{item.from.userName}</div>
            <div> {item.description}</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CommentList;
