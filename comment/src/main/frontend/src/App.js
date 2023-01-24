// src/main/frontend/src/App.js
import { Route } from 'react-router-dom';
import CommentList from './components/Comments/CommentList';
function App() {
  return (
    <div>
      <Route exact path="/comments/:postId">
        <CommentList></CommentList>
      </Route>
    </div>
  );
}

export default App;
