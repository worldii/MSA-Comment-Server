// src/main/frontend/src/App.js

import React, {useEffect, useState} from 'react';
import axios from 'axios';
import Comment from "../src/components/Comments/Comment";

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
          <Comment>
          </Comment>
      </div>
  );
}

export default App;