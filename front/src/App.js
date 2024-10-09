import LoggedPage from './LoggedPage.js';
import UnloggedPage from './UnloggedPage.js';
import './styles/App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { useState } from 'react'

function App() {

  const [token, setToken] = useState('')

  return (
    <div className="App">
      <link rel="preconnect" href="https://fonts.googleapis.com" />
      <link rel="preconnect" href="https://fonts.gstatic.com" crossOrigin />
      <link href="https://fonts.googleapis.com/css2?family=Tiny5&display=swap" rel="stylesheet" />
      <Router>
        <Routes>
          <Route path="/" element={<UnloggedPage setToken={setToken}/>} />
          <Route path="/mycity" element={<LoggedPage token={token}/>} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
