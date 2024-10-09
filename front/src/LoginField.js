import './styles/App.css';
import { useState } from 'react'

function LoginField() {
    const [willLogIn, setWillLogIn] = useState(true)
    const [willSignUp, setWillSignUp] = useState(false)

    const [nickname, setNickname] = useState('')
    const [login, setLogin] = useState('')
    const [password, setPassword] = useState('')

    const [x, setX] = useState('')
    const [y, setY] = useState('')

    navigator.geolocation.getCurrentPosition((position) => {
        let lat = position.coords.latitude
        let long = position.coords.longitude
        setX(lat)
        setY(long)
    })

    const onLogIn = (e) => {
        setWillLogIn(true)
        setWillSignUp(false)
    }

    const onSignUp = (e) => {
        setWillLogIn(false)
        setWillSignUp(true)
    }

    const handleLogIn = (e) => {
        fetch('http://localhost:8080/auth/log_in', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                login: login,
                password: password,
                x: x,
                y: y
            }),
        })
            .then(response => {
                console.log(response)
            })
    }

    const handleSignUp = (e) => {
        fetch('http://localhost:8080/auth/sign_up', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                nickname: nickname,
                login: login,
                password: password,
                x: x,
                y: y
            }),
        })
            .then(response => response.json())
            .then(data => console.log(data.token))
    }

    return (
        <div className="LeftMenuContainer">
            <div className='Form'>
                <button onClick={onLogIn}>Log in?</button> <button onClick={onSignUp}>Sign up?</button>

                {willLogIn &&
                    <div>
                        <p>Login?</p>
                        <input type="text" onChange={(e) => setLogin(e.target.value)} />
                        <br />
                        <p>Password?</p>
                        <input type="password" onChange={(e) => setPassword(e.target.value)} />
                        <br />
                        <p></p>
                        <input type="hidden" onChange={(e) => setPassword(e.target.value)} />
                        <br />
                        <button onClick={handleLogIn}>let's go!</button>
                    </div>}

                {willSignUp &&
                    <div>
                        <p>Nickname?</p>
                        <input type="text" onChange={(e) => setNickname(e.target.value)} />
                        <br />
                        <p>Login?</p>
                        <input type="text" onChange={(e) => setLogin(e.target.value)} />
                        <br />
                        <p>Password?</p>
                        <input type="password" onChange={(e) => setPassword(e.target.value)} />
                        <br />
                        <button onClick={handleSignUp}>let's go!</button>
                    </div>}
            </div >
        </div>
    );
}

export default LoginField;
