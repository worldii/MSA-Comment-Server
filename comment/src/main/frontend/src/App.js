// src/main/frontend/src/App.js

import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Route } from 'react-router-dom';
import CommentList from './components/Comments/CommentList';
function App() {
  // const [hello, setHello] = useState('');
  //
  // useEffect(() => {
  //   axios.get('/api/hello')
  //       .then(response => setHello(response.data))
  //       .catch(error => console.log(error))
  // }, []);

  return (
    <div>
      <Route exact path="/comments/:postId">
        <CommentList></CommentList>
      </Route>
    </div>
  );
}

export default App;
