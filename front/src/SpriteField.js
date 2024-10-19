import SpriteContainer from './SpriteContainer.js';
import './styles/App.css';

const MAX_SPRITE = 8 //idk how import const from other file

let currentZIndex = 0

function SpriteField({ isAdmin, currentUser, isLogged, jsonData, setShow, setNameEdit, setHeightEdit, setHairColorEdit, setPassportIDEdit, setIDEdit }) {

    const displayWithNames = jsonData.map(
        (citizen) => {
            return (
                <SpriteContainer name={citizen.name}
                    zIndexFromMain={currentZIndex++}
                    startX={Math.random() * window.innerWidth}
                    startY={Math.random() * window.innerHeight}
                    spriteNumber={((citizen.id) % MAX_SPRITE)}
                    isMale={citizen.gender}
                    hairColor={citizen.hairColor}
                    eyeColor={citizen.eyeColor}
                    height={citizen.height}
                    passportID={citizen.passportID}
                    id={citizen.id}
                    setShow={setShow}
                    setNameEdit={setNameEdit}
                    setHeightEdit={setHeightEdit}
                    setHairColorEdit={setHairColorEdit}
                    setPassportIDEdit={setPassportIDEdit}
                    setIDEdit={setIDEdit}
                    isLogged={isLogged}
                    showEdit={currentUser === citizen.owner}
                    owner={citizen.owner}
                    isAdmin={isAdmin} />
            )
        }
    )

    return (
        <div>
            {displayWithNames}
        </div>
    );
}

export default SpriteField;
