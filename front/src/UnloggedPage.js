import SpriteField from './SpriteField.js';
import LoginField from './LoginField.js';
import './styles/App.css';
import { useState, useEffect } from 'react';

import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

let currentZIndex = 0

function UnloggedPage({ setCookie, setToken, setCurrentUser, setAdmin }) {

    const [tableValue, setTable] = useState([])
    const [showModal, setShow] = useState(false)
    const [nameForEditing, setNameForEditing] = useState('')
    const [heightForEditing, setHeightForEditing] = useState('')
    const [hairColorForEditing, setHairColorForEditing] = useState('')
    const [passportIDForEditing, setPassportIDForEditing] = useState('')
    const [idForEditing, setIDForEditing] = useState('')

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
            <LoginField setCookie={setCookie} setToken={setToken} setCurrentUser={setCurrentUser} setAdmin={setAdmin} />
            <SpriteField isLogged={false} jsonData={tableValue} setShow={setShow} setNameEdit={setNameForEditing} setHeightEdit={setHeightForEditing} setHairColorEdit={setHairColorForEditing} setPassportIDEdit={setPassportIDForEditing} setIDEdit={setIDForEditing} />
        </div>
    );
}

export default UnloggedPage;
