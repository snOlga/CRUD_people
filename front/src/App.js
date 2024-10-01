import SpriteField from './SpriteField.js';
import LeftMenuContainer from './LeftMenuContainer.js';
import './styles/App.css';
import { useState, useEffect } from 'react';

let currentZIndex = 0

function App() {

  const [tableValue, setTable] = useState([])

  useEffect(() => {
    fetchServer()
  }, [])

  function fetchServer() {
    fetch('http://localhost:8080/api/get_all', {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
    })
      .then(response => {
        let jsonResp = response.json();
        console.log(jsonResp);
        jsonResp.then((data) => setTable(data));
      })
  }

  return (
    <div className="App">
      <link rel="preconnect" href="https://fonts.googleapis.com" />
      <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
      <link href="https://fonts.googleapis.com/css2?family=Tiny5&display=swap" rel="stylesheet" />
      <LeftMenuContainer jsonData={tableValue} />
      <SpriteField jsonData={tableValue} />
    </div>
  );
}

export default App;
