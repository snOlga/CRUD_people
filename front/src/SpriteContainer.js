import { useState } from 'react';
import './styles/App.css';

import Sprite from './Sprite.js';
import MAX_SPRITE from './Sprite.js';

function SpriteContainer({ zIndexFromMain, startX, startY }) {

    let ONE_STEP = 5
    let INTERVAL = 1000
    let PICTURE_SIDE_SIZE = 200

    const [position, setPosition] = useState({
        x: (startX + PICTURE_SIDE_SIZE) < window.innerWidth ? startX : (window.innerWidth - PICTURE_SIDE_SIZE),
        y: (startY + PICTURE_SIDE_SIZE) < window.innerHeight ? startY : (window.innerHeight - PICTURE_SIDE_SIZE)
    });

    function goThere(stepCount, xStep, yStep) {
        xStep = position.x + xStep > 0 ? xStep : 0 - xStep
        yStep = position.y + yStep > 0 ? yStep : 0 - yStep

        xStep = (xStep + PICTURE_SIDE_SIZE) < window.innerWidth ? xStep : 0 - xStep
        yStep = (yStep + PICTURE_SIDE_SIZE) < window.innerHeight ? yStep : 0 - yStep

        for (let i = 0; i < stepCount; i++) {
            setPosition({
                x: position.x + xStep,
                y: position.y + yStep,
            })
        }
    }

    function moveSmt() {
        let rndm = Math.random()
        let num = rndm * 1000
        num = Math.round(num)

        let stepCount = Math.round((Math.random() * 1000) % 6 + 5)

        if (num % 6 == 0) {
            goThere(stepCount, ONE_STEP, 0)
        }
        else if (num % 6 == 1) {
            goThere(stepCount, 0, ONE_STEP)
        }
        else if (num % 6 == 2) {
            goThere(stepCount, ONE_STEP, ONE_STEP)
        }
        else if (num % 6 == 3) {
            goThere(stepCount, 0 - ONE_STEP, 0)
        }
        else if (num % 6 == 4) {
            goThere(stepCount, 0, 0 - ONE_STEP)
        }
        else if (num % 6 == 5) {
            goThere(stepCount, 0 - ONE_STEP, 0 - ONE_STEP)
        }
    }

    setTimeout(() => moveSmt(), INTERVAL);

    return (
        <div className="Sprite"
            style=
            {{
                transform: `translate(${position.x}px, ${position.y}px)`,
                zIndex: { zIndexFromMain }
            }}>
            <Sprite SpriteNumber={Math.random(MAX_SPRITE)}/>
        </div>
    );
}

export default SpriteContainer;
