import { useState, useEffect } from 'react'
import './styles/App.css'

import Sprite from './Sprite.js'
import MAX_SPRITE from './Sprite.js'

const ONE_STEP = 5
const INTERVAL = 1000
const PICTURE_SIDE_SIZE = 200

function SpriteContainer({ zIndexFromMain, startX, startY }) {
    const [position, setPosition] = useState({
        x: (startX + PICTURE_SIDE_SIZE) < window.innerWidth ? startX : (window.innerWidth - PICTURE_SIDE_SIZE),
        y: (startY + PICTURE_SIDE_SIZE) < window.innerHeight ? startY : (window.innerHeight - PICTURE_SIDE_SIZE),
    })
    const [isDragging, setIsDragging] = useState(false)
    const [timeOutID, setTimeOutID] = useState(null)

    useEffect(() => {
        if (!isDragging) {
            setTimeOutID(setTimeout(() => goingSomewhere(), INTERVAL))
            clearTimeout(timeOutID)
        }
    }, [isDragging, position])

    function goingSomewhere() {
        let rndm = Math.random()
        let chooseWhere = Math.round(rndm * 1000)

        let stepCount = Math.round((Math.random() * 1000) % 6 + 5)

        if (chooseWhere % 6 === 0) {
            goThere(stepCount, ONE_STEP, 0)
        } else if (chooseWhere % 6 === 1) {
            goThere(stepCount, 0, ONE_STEP)
        } else if (chooseWhere % 6 === 2) {
            goThere(stepCount, ONE_STEP, ONE_STEP)
        } else if (chooseWhere % 6 === 3) {
            goThere(stepCount, -ONE_STEP, 0)
        } else if (chooseWhere % 6 === 4) {
            goThere(stepCount, 0, -ONE_STEP)
        } else if (chooseWhere % 6 === 5) {
            goThere(stepCount, -ONE_STEP, -ONE_STEP)
        }
    }

    function goThere(stepCount, xStep, yStep) {
        xStep = position.x + xStep > 0 ? xStep : 0 - xStep
        yStep = position.y + yStep > 0 ? yStep : 0 - yStep

        xStep = (xStep + PICTURE_SIDE_SIZE) < window.innerWidth ? xStep : 0 - xStep
        yStep = (yStep + PICTURE_SIDE_SIZE) < window.innerHeight ? yStep : 0 - yStep

        for (let i = 0; i < stepCount; i++) {
            setPosition((prevPosition) => ({
                x: prevPosition.x + xStep,
                y: prevPosition.y + yStep,
            }))
        }
    }

    function handleDragStart() {
        clearTimeout(timeOutID)
        setIsDragging(true)
    }

    function handleDragEnd() {
        setIsDragging(false)
    }

    function replaceByMouse(event) {
        if (isDragging && event.clientX > 0 && event.clientY > 0) {
            setPosition({
                x: event.clientX - PICTURE_SIDE_SIZE / 2,
                y: event.clientY - PICTURE_SIDE_SIZE / 2,
            })
        }
    }

    return (
        <div
            className="Sprite"
            style={{
                transform: `translate(${position.x}px, ${position.y}px)`,
                zIndex: zIndexFromMain,
                width: `${PICTURE_SIDE_SIZE}px`,
                height: `${PICTURE_SIDE_SIZE}px`,
            }}
            draggable
            onDrag={replaceByMouse}
            onDragStart={handleDragStart}
            onDragEnd={handleDragEnd}
        >
            <Sprite SpriteNumber={Math.floor(Math.random() * MAX_SPRITE)} />
        </div>
    )
}

export default SpriteContainer
