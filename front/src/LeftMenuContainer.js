import './styles/App.css';
import CitizensTable from './CitizensTable';
import { useState } from 'react'

function LeftMenuContainer({ jsonData }) {
  const [name, setName] = useState('');
  const [isMale, setMaleGender] = useState('');
  const [eyeColor, setEyeColor] = useState('');
  const [hairColor, setHairColor] = useState('');
  const [height, setHeight] = useState('');
  const [birthday, setBirthday] = useState('');
  const [passportID, setPassportID] = useState('');
  const [nationality, setNationality] = useState('');
  const [xCoord, setX] = useState('');
  const [yCoord, setY] = useState('');

  const handleName = (e) => {
    setName(e.target.value);
  };

  const handleEyeColor = (e) => {
    setEyeColor(e.target.value);
  };

  const handleHairColor = (e) => {
    setHairColor(e.target.value);
  };

  const handleHeight = (e) => {
    setHeight(e.target.value);
  };

  const handleBirthday = (e) => {
    setBirthday(e.target.value);
  };

  const handlePassportID = (e) => {
    setPassportID(e.target.value);
  };

  const handleNationality = (value) => {
    setNationality(value);
  };

  const handleGender = (value) => {
    console.log(value)
    setMaleGender(value);
  };

  navigator.geolocation.getCurrentPosition((position) => {
    let lat = position.coords.latitude
    let long = position.coords.longitude
    setX(lat)
    setY(long)
  })

  const handleSubmit = async (e) => {
    e.preventDefault()

    fetch('http://localhost:8080/api/send_one', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
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
        window.location.reload()
      })

  }

  return (
    <div className="LeftMenuContainer">
      <h1>My little City</h1>
      <br />

      <p>Create new citizen?</p>

      <p>Name?</p>
      <input type="text" placeholder='Robert' className='InputFields' onChange={handleName} />

      <p>Gender?</p>
      <input type="radio" id="maleRadio" name="maleRadio" value="MALE" onClick={() => handleGender('1')} />
      <label for="maleRadio">Male</label>
      <br />
      <input type="radio" id="femaleRadio" name="maleRadio" value="FEMALE" onClick={() => handleGender('0')} />
      <label for="femaleRadio">Female</label>

      <p>Eyes color?</p>
      <input type="color" className='InputFields' onChange={handleEyeColor} />

      <p>Hair color?</p>
      <input type="color" className='InputFields' onChange={handleHairColor} />

      <p>Height?</p>
      <input type="number" className='InputFields' onChange={handleHeight} />

      <p>Birth date?</p>
      <input type="date" className='InputFields' onChange={handleBirthday} />

      <p>Passport ID?</p>
      <input type="number" className='InputFields' onChange={handlePassportID} />

      <div className='NationalityContainer'>
        <p>Nationality?</p>
        <input type="radio" id="russiaRadio" name="nationality" checked className='RadioInput' value="RUSSIA" onClick={() => handleNationality("RUSSIA")} />
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

      <button className='' onClick={handleSubmit}>submit !</button>

      <CitizensTable jsonData={jsonData} />
    </div>
  );
}

export default LeftMenuContainer;
