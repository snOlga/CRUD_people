import './styles/App.css';

import FemaleSprite from './sprites/FemaleSprite.js'
import MaleSprite from './sprites/MaleSprite.js'

function Sprite({ SpriteNumber, isMale, hairColor }) {

    console.log(isMale)

    if (isMale) {
        return (
            <MaleSprite number={SpriteNumber} hairColor={hairColor} />
        )
    }
    else {
        return (
            <FemaleSprite number={SpriteNumber} hairColor={hairColor} />
        )
    }
}

export default Sprite;
