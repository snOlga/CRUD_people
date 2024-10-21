import LoggedPage from './LoggedPage.js';
import UnloggedPage from './UnloggedPage.js';
import './styles/App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { useEffect, useState } from 'react'
import { useCookies } from 'react-cookie'
import Cookies from 'js-cookie'

function App() {

  const [token, setToken] = useState('') //TODO: local storage
  const [currentUser, setCurrentUser] = useState('')
  const [isAdmin, setAdmin] = useState(false)
  const [cookies, setCookie] = useCookies(['Token', 'IsAdmin', 'CurrentUser'])

  useEffect(() => {
    setAdmin(Cookies.get('IsAdmin'))
    setCurrentUser(Cookies.get('CurrentUser'))
  }, [])

  return (
    <div className="App">
      <link rel="preconnect" href="https://fonts.googleapis.com" />
      <link rel="preconnect" href="https://fonts.gstatic.com" crossOrigin />
      <link href="https://fonts.googleapis.com/css2?family=Tiny5&display=swap" rel="stylesheet" />
      <Router>
        <Routes>
          <Route path="/" element={<UnloggedPage setCookie={setCookie} setToken={setToken} setCurrentUser={setCurrentUser} setAdmin={setAdmin} />} />
          <Route path="/mycity" element={<LoggedPage cookies={cookies} token={token} currentUser={currentUser} isAdmin={isAdmin} setToken={setToken} />} />
        </Routes>
      </Router>
    </div>
  )
}

export default App;
