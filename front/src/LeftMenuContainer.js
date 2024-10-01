import './styles/App.css';
import CitizensTable from './CitizensTable';
import { useState } from 'react'

function LeftMenuContainer({ jsonData }) {
  // const [name, setName] = useState('');
  // const [eyeColor, setEyeColor] = useState('');
  // const [hairColor, setHairColor] = useState('');
  // const [height, setHeight] = useState('');
  // const [birthday, setBirthday] = useState('');
  // const [birthday, setBirthday] = useState('');
  
  return (
    <div className="LeftMenuContainer">
      <h1>My little City</h1>
      <br/>

      <p>Create new citizen?</p>

      <p>Name?</p>
      <input type="text" placeholder='Robert' className='InputFields' />

      <p>Eyes color?</p>
      <input type="color" className='InputFields' />

      <p>Hair color?</p>
      <input type="color" className='InputFields' />

      <p>Height?</p>
      <input type="number" className='InputFields' />

      <p>Birth date?</p>
      <input type="date" className='InputFields' />

      <p>Passport ID?</p>
      <input type="number" className='InputFields' />

      <div className='NationalityContainer'>
        <p>Nationality?</p>
        <input type="radio" id="russiaRadio" name="nationality" checked className='RadioInput' />
        <label for="russiaRadio">Russia</label>
        <br />
        <input type="radio" id="franceRadio" name="nationality" />
        <label for="franceRadio">France</label>
        <br />
        <input type="radio" id="unitedKingdomRadio" name="nationality" />
        <label for="unitedKingdomRadio">United Kingdom</label>
        <br />
        <input type="radio" id="vaticanRadio" name="nationality" />
        <label for="vaticanRadio">Vatican</label>
        <br />
        <input type="radio" id="southKoreaRadio" name="nationality" />
        <label for="southKoreaRadio">South Korea</label>
      </div>

      <button className=''>submit !</button>

      <CitizensTable jsonData={jsonData}/>
    </div>
  );
}

export default LeftMenuContainer;
