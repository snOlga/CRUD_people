import SpriteContainer from './SpriteContainer.js';
import './styles/App.css';

const MAX_SPRITE = 17 //idk how import const from other file

let currentZIndex = 0

function SpriteField({ jsonData }) {

    console.log(MAX_SPRITE)

    const displayWithNames = jsonData.map(
        (citizen) => {
            return (
                <SpriteContainer name={citizen.name}
                    zIndexFromMain={currentZIndex++}
                    startX={Math.random() * window.innerWidth}
                    startY={Math.random() * window.innerHeight}
                    spriteNumber={((citizen.id) % MAX_SPRITE)} />
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
