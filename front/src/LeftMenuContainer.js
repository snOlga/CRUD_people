import './styles/App.css';

function LeftMenuContainer() {
  return (
    <div className="LeftMenuContainer">
      <h1>My little City</h1>


      <p>Create new citizen?</p>

      <p>Name?</p>
      <input type="text" placeholder='Robert' />

      <p>Eyes color?</p>
      <input type="color" />

      <p>Hair color?</p>
      <input type="color" />

      <p>Height?</p>
      <input type="number" /> cm

      <p>Birth date?</p>
      <input type="date" />

      <p>Passport ID?</p>
      <input type="number" />

      <p>Nationality?</p>
      <div>
        <input type="radio" id="russiaRadio" name="nationality" checked />
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
    </div>
  );
}

export default LeftMenuContainer;
