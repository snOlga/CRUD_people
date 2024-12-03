import './styles/App.css';
import CitizensTable from './CitizensTable';
import ImportHistory from './ImportHistory';
import { useEffect, useState } from 'react'
import { Navigate, useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

function LeftMenuContainer({ jsonData, token, setToken }) {
  const DEFAULT_COLOR = '#000000'
  const ERROR_COLOR = '#FF0000'

  const [name, setName] = useState('')
  const [isMale, setMaleGender] = useState('')
  const [eyeColor, setEyeColor] = useState(DEFAULT_COLOR)
  const [hairColor, setHairColor] = useState(DEFAULT_COLOR)
  const [height, setHeight] = useState('')
  const [birthday, setBirthday] = useState('')
  const [passportID, setPassportID] = useState('')
  const [nationality, setNationality] = useState('')
  const [xCoord, setX] = useState('')
  const [yCoord, setY] = useState('')

  const [mainErrorMessage, setMainErrorMessage] = useState('')
  const [nameErrorBorder, setNameErrorBorder] = useState(DEFAULT_COLOR)
  const [genderErrorColor, setGenderErrorColor] = useState(DEFAULT_COLOR)
  const [heightErrorBorder, setHeightErrorBorder] = useState(DEFAULT_COLOR)
  const [birthdayErrorBorder, setBirthdayErrorBorder] = useState(DEFAULT_COLOR)
  const [passportIDErrorBorder, setPassportIDErrorBorder] = useState(DEFAULT_COLOR)
  const [nationalityErrorColor, setNationalityErrorColor] = useState(DEFAULT_COLOR)

  const navigate = useNavigate()

  const handleName = (e) => {
    setName(e.target.value)
    setNameErrorBorder(DEFAULT_COLOR)
  }

  const handleGender = (value) => {
    setMaleGender(value)
    setGenderErrorColor(DEFAULT_COLOR)
  }

  const handleEyeColor = (e) => {
    setEyeColor(e.target.value)
  }

  const handleHairColor = (e) => {
    setHairColor(e.target.value)
  }

  const handleHeight = (e) => {
    setHeight(e.target.value)
    setHeightErrorBorder(DEFAULT_COLOR)
  }

  const handleBirthday = (e) => {
    setBirthday(e.target.value)
    setBirthdayErrorBorder(DEFAULT_COLOR)
  }

  const handlePassportID = (e) => {
    setPassportID(e.target.value)
    setPassportIDErrorBorder(DEFAULT_COLOR)
  }

  const handleNationality = (value) => {
    setNationality(value)
    setNationalityErrorColor(DEFAULT_COLOR)
  }

  navigator.geolocation.getCurrentPosition((position) => {
    let lat = position.coords.latitude
    let long = position.coords.longitude
    setX(lat)
    setY(long)
  })

  const nameSubstr = event => {
    let lastChar = event.target.value[event.target.value.length - 1]
    if (event.target.value.length >= 1 && lastChar.toUpperCase() == lastChar.toLowerCase()) {
      event.target.value = event.target.value.substr(0, event.target.value.length - 1)
    }
    event.target.value = event.target.value.substr(0, 20)
  }

  const heightSubstr = event => {
    if (event.target.value < 1 || event.target.value > 250) {
      event.target.value = event.target.value.substr(0, event.target.value.length - 1)
    }
    event.target.value = event.target.value.substr(0, 3)
  }

  const passportSubstr = event => {
    if (event.target.value < 1) {
      event.target.value = event.target.value.substr(0, event.target.value.length - 1)
    }
    event.target.value = event.target.value.substr(0, 10)
  }

  function isFilled() {
    if (name == '')
      setNameErrorBorder(ERROR_COLOR)
    if (isMale == '')
      setGenderErrorColor(ERROR_COLOR)
    if (height == '')
      setHeightErrorBorder(ERROR_COLOR)
    if (birthday == '')
      setBirthdayErrorBorder(ERROR_COLOR)
    if (passportID == '')
      setPassportIDErrorBorder(ERROR_COLOR)
    if (nationality == '')
      setNationalityErrorColor(ERROR_COLOR)

    return name != '' && isMale != '' && height != '' && birthday != '' && passportID != '' && nationality != '' && xCoord != '' && yCoord != ''
  }

  const handleSubmit = async (e) => {
    if (!isFilled()) {
      setMainErrorMessage("Some fields are empty!")
      return
    }

    e.preventDefault()

    if (!isPassportUnique()) {
      setMainErrorMessage("Passport ID is not unique!")
      return
    }

    sendCitizen()
  }

  const handleLogOut = e => {
    setToken('')
    navigate('/')
  }

  function isPassportUnique() {
    for (var i = 0; i < jsonData.length; i++) {
      let citizen = jsonData[i];
      if (citizen.passportID == passportID)
        return false
    }
    return true
  }

  function sendCitizen() {
    fetch('http://localhost:17617/api/send_one', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        token: Cookies.get('Token'),
        name: name,
        gender: isMale,
        eyeColor: eyeColor,
        hairColor: hairColor,
        height: height,
        birthday: birthday,
        passportID: passportID,
        nationality: nationality,
        xCoord: xCoord,
        yCoord: yCoord
      }),
    })
      .then(response => {
        //window.location.reload()
      })
  }
  const [fileData, setFileData] = useState("")
  const [filename, setFilename] = useState("")

  function handleImport() {
    fetch('http://localhost:17617/api/send_mass', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(JSON.parse(fileData)),
    })
      .then(response => {
        let jsonResp = response.json()
        jsonResp.then((data) => {
          console.log(data.isSuccessful)
          fetch('http://localhost:17617/history/set_one_history_node', {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              filename: filename,
              isSuccessful: data.isSuccessful
            }),
          })
            .then(response => {
              //window.location.reload()
            })
        })
      })
  }

  function handleFileChange(e) {
    const selectedFile = e.target.files[0]
    setFilename(e.target.files[0].name)
    const reader = new FileReader()
    reader.onload = (event) => {
      setFileData(event.target.result)
    }
    reader.readAsText(selectedFile)
  }

  const [isImportHistory, showImportHistory] = useState(false)
  const [history, setHistory] = useState([])

  const SOCKET_URL = 'http://localhost:17617/ws-endpoint';

    useEffect(() => {
        connectWebSocket()
    }, [])

    function connectWebSocket() {
        const socket = new SockJS(SOCKET_URL)
        let stompClient = Stomp.over(socket)

        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/topic/import_history', data => {
                handleWebSocketMessage(data.body)
            })
        })
    }

    function handleWebSocketMessage(data) {
        setHistory(JSON.parse(data))
    }

  return (
    <div className="LeftMenuContainer">
      <div className='Form'>
        <h1>My little City</h1>
        <br />

        <p>Import them?</p>
        <input type="file" id="input" multiple onChange={handleFileChange} />
        <br />
        <button onClick={handleImport}>import !</button>
        <br />
        <button onClick={(e) => showImportHistory(!isImportHistory)}>show import history</button>
        {
          isImportHistory && <ImportHistory history={history} />
        }

        <p>Create new citizen?</p>

        <p>Name?</p>
        <input type="text" placeholder='Robert' className='InputFields' onChange={handleName} style={{ borderColor: nameErrorBorder }} onInput={nameSubstr} />
        <br />

        <div>
          <p style={{ color: genderErrorColor }}>Gender?</p>
          <input type="radio" id="maleRadio" name="maleRadio" value="MALE" onClick={() => handleGender('1')} />
          <label for="maleRadio">Male</label>
          <br />
          <input type="radio" id="femaleRadio" name="maleRadio" value="FEMALE" onClick={() => handleGender('0')} />
          <label for="femaleRadio">Female</label>
        </div>

        <p>Eyes color?</p>
        <input type="color" className='InputFields' onChange={handleEyeColor} />

        <p>Hair color?</p>
        <input type="color" className='InputFields' onChange={handleHairColor} />

        <p>Height?</p>
        <input type="number" className='InputFields' onChange={handleHeight} style={{ borderColor: heightErrorBorder }} onInput={heightSubstr} />

        <p>Birth date?</p>
        <input type="date" className='InputFields' onChange={handleBirthday} style={{ borderColor: birthdayErrorBorder }} />

        <p>Passport ID?</p>
        <input type="number" className='InputFields' onChange={handlePassportID} style={{ borderColor: passportIDErrorBorder }} onInput={passportSubstr} />

        <div className='NationalityContainer'>
          <p style={{ color: nationalityErrorColor }}>Nationality?</p>
          <input type="radio" id="russiaRadio" name="nationality" className='RadioInput' value="RUSSIA" onClick={() => handleNationality("RUSSIA")} />
          <label for="russiaRadio">Russia</label>
          <br />
          <input type="radio" id="franceRadio" name="nationality" value="FRANCE" onClick={() => handleNationality("FRANCE")} />
          <label for="franceRadio">France</label>
          <br />
          <input type="radio" id="unitedKingdomRadio" name="nationality" value="UNITED_KINGDOM" onClick={() => handleNationality("UNITED_KINGDOM")} />
          <label for="unitedKingdomRadio">United Kingdom</label>
          <br />
          <input type="radio" id="vaticanRadio" name="nationality" value="VATICAN" onClick={() => handleNationality("VATICAN")} />
          <label for="vaticanRadio">Vatican</label>
          <br />
          <input type="radio" id="southKoreaRadio" name="nationality" value="SOUTH_KOREA" onClick={() => handleNationality("SOUTH_KOREA")} />
          <label for="southKoreaRadio">South Korea</label>
        </div>

        <br />
        <button onClick={handleSubmit}>submit !</button>
        <br />
        <span>{mainErrorMessage}</span>
        <br />
        <button onClick={handleLogOut}>log out</button>
      </div >
      <div className="Table">
        <CitizensTable jsonData={jsonData} />
      </div>
    </div>
  );
}

export default LeftMenuContainer;
