import SpriteField from './SpriteField.js';
import LeftMenuContainer from './LeftMenuContainer.js';
import ModalEditing from './ModalEditing.js';
import './styles/App.css';
import { useState, useEffect } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';

import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

let currentZIndex = 0

function LoggedPage({ token, currentUser, isAdmin, setToken }) {

  const [tableValue, setTable] = useState([])
  const [showModal, setShow] = useState(false)
  const [nameForEditing, setNameForEditing] = useState('')
  const [heightForEditing, setHeightForEditing] = useState('')
  const [hairColorForEditing, setHairColorForEditing] = useState('')
  const [passportIDForEditing, setPassportIDForEditing] = useState('')
  const [idForEditing, setIDForEditing] = useState('')
  const navigate = useNavigate()

  const BASE_URL = 'http://localhost:17617';
  const SOCKET_URL = 'http://localhost:17617/ws-endpoint';

  useEffect(() => {
    fetchServer()
    connectWebSocket()
  }, [])

  function fetchServer() {
    fetch('http://localhost:17617/api/get_all', {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
    })
      .then(response => {
        let jsonResp = response.json()
        jsonResp.then((data) =>
          setTable(data))
      })
  }

  function connectWebSocket() {
    const socket = new SockJS(SOCKET_URL)
    let stompClient = Stomp.over(socket)

    stompClient.connect({}, function (frame) {
      stompClient.subscribe('/topic/citizen', data => {
        handleWebSocketMessage(data.body)
      })
    })
  }

  function handleWebSocketMessage(data) {
    setTable(JSON.parse(data))
  }

  return (
    <div className="App">
      <link rel="preconnect" href="https://fonts.googleapis.com" />
      <link rel="preconnect" href="https://fonts.gstatic.com" crossOrigin />
      <link href="https://fonts.googleapis.com/css2?family=Tiny5&display=swap" rel="stylesheet" />
      <LeftMenuContainer jsonData={tableValue} token={token} setToken={setToken} />
      <SpriteField isAdmin={isAdmin} currentUser={currentUser} isLogged={true} jsonData={tableValue} setShow={setShow} setNameEdit={setNameForEditing} setHeightEdit={setHeightForEditing} setHairColorEdit={setHairColorForEditing} setPassportIDEdit={setPassportIDForEditing} setIDEdit={setIDForEditing} />
      {showModal && <ModalEditing token={token} setShow={setShow} jsonData={tableValue} nameOld={nameForEditing} hairColorOld={hairColorForEditing} heightOld={heightForEditing} passportIDOld={passportIDForEditing} id={idForEditing} />}
    </div>
  );
}

export default LoggedPage;
