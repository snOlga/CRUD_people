import { useState } from 'react';
import './App.css';

import sprite1 from './sprites/1.png';
import sprite2 from './sprites/2.png';
import sprite3 from './sprites/3.png';
import sprite4 from './sprites/4.png';
import sprite5 from './sprites/5.png';
import sprite6 from './sprites/6.png';
import sprite7 from './sprites/7.png';
import sprite8 from './sprites/8.png';
import sprite9 from './sprites/9.png';
import sprite10 from './sprites/10.png';
import sprite11 from './sprites/11.png';
import sprite12 from './sprites/12.png';
import sprite13 from './sprites/13.png';
import sprite14 from './sprites/14.png';
import sprite15 from './sprites/15.png';
import sprite16 from './sprites/16.png';
import sprite17 from './sprites/17.png';

function Sprite({ SpriteNumber, zIndexFromMain, startX, startY }) {

    let ONE_STEP = 5
    let INTERVAL = 1000

    const [position, setPosition] = useState({
        x: startX,
        y: startY
    });

    function goThere(stepCount, xStep, yStep) {
        console.log(stepCount)
        xStep = position.x + xStep > 0 ? xStep : 0 - xStep
        yStep = position.y + yStep > 0 ? yStep : 0 - yStep
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

    let currentSprite = sprite1

    switch (SpriteNumber) {
        case 1:
            currentSprite = sprite1
            break;
        case 2:
            currentSprite = sprite2
            break;
        case 3:
            currentSprite = sprite3
            break;
        case 4:
            currentSprite = sprite4
            break;
        case 5:
            currentSprite = sprite5
            break;
        case 6:
            currentSprite = sprite6
            break;
        case 7:
            currentSprite = sprite7
            break;
        case 8:
            currentSprite = sprite8
            break;
        case 9:
            currentSprite = sprite9
            break;
        case 10:
            currentSprite = sprite10
            break;
        case 11:
            currentSprite = sprite11
            break;
        case 12:
            currentSprite = sprite12
            break;
        case 13:
            currentSprite = sprite13
            break;
        case 14:
            currentSprite = sprite14
            break;
        case 15:
            currentSprite = sprite15
            break;
        case 16:
            currentSprite = sprite16
            break;
        case 17:
            currentSprite = sprite17
            break;
    }

    return (
        <div className="Sprite"
            style=
            {{
                transform: `translate(${position.x}px, ${position.y}px)`,
                zIndex: { zIndexFromMain }
            }}>
            <img src={currentSprite} alt="sprite" className='SpriteImage' />
        </div>
    );
}

export default Sprite;
