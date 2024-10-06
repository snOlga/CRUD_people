import SpriteField from './SpriteField.js';
import LeftMenuContainer from './LeftMenuContainer.js';
import ModalEditing from './ModalEditing.js';
import './styles/App.css';
import { useState, useEffect } from 'react';

let currentZIndex = 0

function App() {

  const [tableValue, setTable] = useState([])
  const [showModal, setShow] = useState(false)
  const [nameForEditing, setNameForEditing] = useState('')
  const [heightForEditing, setHeightForEditing] = useState('')
  const [hairColorForEditing, setHairColorForEditing] = useState('')
  const [passportIDForEditing, setPassportIDForEditing] = useState('')
  const [idForEditing, setIDForEditing] = useState('')

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
        jsonResp.then((data) => setTable(data));
      })
  }

  return (
    <div className="App">
      <link rel="preconnect" href="https://fonts.googleapis.com" />
      <link rel="preconnect" href="https://fonts.gstatic.com" crossOrigin />
      <link href="https://fonts.googleapis.com/css2?family=Tiny5&display=swap" rel="stylesheet" />
      <LeftMenuContainer jsonData={tableValue} />
      <SpriteField jsonData={tableValue} setShow={setShow} setNameEdit={setNameForEditing} setHeightEdit={setHeightForEditing} setHairColorEdit={setHairColorForEditing} setPassportIDEdit={setPassportIDForEditing} setIDEdit={setIDForEditing} />
      {showModal && <ModalEditing setShow={setShow} jsonData={tableValue} nameOld={nameForEditing} hairColorOld={hairColorForEditing} heightOld={heightForEditing} passportIDOld={passportIDForEditing} id={idForEditing} />}
    </div>
  );
}

export default App;
