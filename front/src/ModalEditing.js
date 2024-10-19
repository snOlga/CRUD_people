import './styles/App.css';
import { useState } from 'react';

function ModalEditing({ setShow, id, nameOld, hairColorOld, heightOld, passportIDOld, jsonData, token }) {
    const DEFAULT_COLOR = '#000000'
    const ERROR_COLOR = '#FF0000'

    const [name, setName] = useState(nameOld)
    const [hairColor, setHairColor] = useState(hairColorOld)
    const [height, setHeight] = useState(heightOld)
    const [passportID, setPassportID] = useState(passportIDOld)

    const [mainErrorMessage, setMainErrorMessage] = useState('')
    const [passportIDErrorBorder, setPassportIDErrorBorder] = useState(DEFAULT_COLOR)

    const [willDelete, setWillDelete] = useState(false)


    const handleName = (e) => {
        setName(e.target.value)
    }

    const handleHairColor = (e) => {
        setHairColor(e.target.value)
    }

    const handleHeight = (e) => {
        setHeight(e.target.value)
    }

    const handlePassportID = (e) => {
        setPassportID(e.target.value)
    }

    const nameSubstr = event => {
        let lastChar = event.target.value[event.target.value.length - 1]
        if (event.target.value.length >= 1 && lastChar.toUpperCase() == lastChar.toLowerCase()) {
            event.target.value = event.target.value.substr(0, event.target.value.length - 1)
        }
        event.target.value = event.target.value.substr(0, 20)
    }

    const handleEditing = (e) => {
        e.preventDefault()

        if (!isPassportUnique()) {
            return
        }

        sendCitizen()
        setShow(false)
    }

    function isPassportUnique() {
        for (var i = 0; i < jsonData.length; i++) {
            let citizen = jsonData[i];
            if (citizen.passportID == passportID && citizen.id != id) {
                setPassportIDErrorBorder(ERROR_COLOR)
                setMainErrorMessage("Passport ID is not unique!")
                return false
            }
        }
        return true
    }

    function sendCitizen() {
        fetch('http://localhost:17617/api/update_one', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': `Bearer ` + token
            },
            body: JSON.stringify({
                token: token,
                id: id,
                name: name,
                hairColor: hairColor,
                height: height,
                passportID: passportID
            }),
        })
            .then(response => {
                //window.location.reload()
            })
    }

    const handleDeleting = (e) => {
        e.preventDefault()
        setWillDelete(true)
    }

    const deleteCitizen = (e) => {
        e.preventDefault()
        setWillDelete(false)
        setShow(false)

        fetch('http://localhost:17617/api/delete_one', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': `Bearer ` + token
            },
            body: JSON.stringify({
                token: token,
                id: id
            }),
        })
    }

    const closeDeleting = (e) => {
        setWillDelete(false)
    }

    return (
        <div className='Modal'>
            You are editing: {id}

            <p>Name?</p>
            <input type="text" placeholder={nameOld} className='InputFields' onChange={handleName} onInput={nameSubstr} />

            <p>Hair color?</p>
            <input type="color" value={hairColor} className='InputFields' onChange={handleHairColor} />

            <p>Height?</p>
            <input type="number" placeholder={heightOld} className='InputFields' onChange={handleHeight} />

            <p>Passport ID?</p>
            <input type="number" placeholder={passportIDOld} className='InputFields' onChange={handlePassportID} />
            <br />
            <span>{mainErrorMessage}</span>
            <br />
            <button onClick={() => setShow(false)}>just close</button>
            <button onClick={handleEditing}>close and save</button>
            <br />
            <button onClick={handleDeleting}>delete citizen</button>
            {willDelete && <div>Are you sure?
                <br />
                <button onClick={deleteCitizen}>yes</button> <button onClick={closeDeleting}>no</button></div>}
        </div>
    )
}

export default ModalEditing;
