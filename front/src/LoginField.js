import './styles/App.css';
import { useState } from 'react'
import axios from 'axios'
import { Navigate, useNavigate } from 'react-router-dom';

function LoginField({ setCookie, setToken, setCurrentUser, setAdmin }) {
    const DEFAULT_COLOR = '#000000'
    const ERROR_COLOR = '#FF0000'

    const [willLogIn, setWillLogIn] = useState(true)
    const [willSignUp, setWillSignUp] = useState(false)

    const [nickname, setNickname] = useState('')
    const [login, setLogin] = useState('')
    const [password, setPassword] = useState('')

    const [x, setX] = useState('')
    const [y, setY] = useState('')

    const [mainErrorMessage, setMainErrorMessage] = useState('')
    const [errorBorder, setErrorBorder] = useState(DEFAULT_COLOR)

    const navigate = useNavigate()

    navigator.geolocation.getCurrentPosition((position) => {
        let lat = position.coords.latitude
        let long = position.coords.longitude
        setX(lat)
        setY(long)
    })

    const onLogIn = (e) => {
        setWillLogIn(true)
        setWillSignUp(false)
        setMainErrorMessage("")
        setErrorBorder(DEFAULT_COLOR)
    }

    const onSignUp = (e) => {
        setWillLogIn(false)
        setWillSignUp(true)
        setMainErrorMessage("")
        setErrorBorder(DEFAULT_COLOR)
    }

    const handleLogIn = (e) => {
        fetch('http://localhost:17617/auth/log_in', {
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
            .then(response => response.json())
            .then(data => {
                if (data.isSuccessful === "true") {
                    setToken(data.token)
                    setCurrentUser(data.nickname)

                    let expires = new Date()
                    expires.setTime(expires.getTime() + 86400000)

                    setCookie('CurrentUser', data.nickname, { path: '/', expires })
                    setCookie('Token', data.token, { path: '/', expires })
                    if (data.role === "admin") {
                        setAdmin(true)
                        setCookie('IsAdmin', true, { path: '/', expires })
                    }
                    navigate('/mycity')
                }
                else {
                    setMainErrorMessage("Incorrect credentials")
                    setErrorBorder(ERROR_COLOR)
                }
            })
    }

    const handleSignUp = (e) => {
        fetch('http://localhost:17617/auth/sign_up', {
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
            .then(data => {
                if (data.isSuccessful === "true") {
                    setToken(data.token)
                    setCurrentUser(data.nickname)

                    let expires = new Date()
                    expires.setTime(expires.getTime() + 86400000)

                    setCookie('CurrentUser', data.nickname, { path: '/', expires })
                    setCookie('Token', data.token, { path: '/', expires })
                    navigate('/mycity')
                } else {
                    setMainErrorMessage("User already exists")
                    setErrorBorder(ERROR_COLOR)
                }
            })
    }

    const loginSubstr = event => {
        event.target.value = event.target.value.substr(0, 20)
    }

    return (
        <div className="LeftMenuContainer">
            <div className='Form'>
                <button onClick={onLogIn}>Log in?</button> <button onClick={onSignUp}>Sign up?</button>

                {willLogIn &&
                    <div>
                        <p>Login?</p>
                        <input type="text" onChange={(e) => setLogin(e.target.value)} style={{ borderColor: errorBorder }} onInput={loginSubstr} />
                        <br />
                        <p>Password?</p>
                        <input type="password" onChange={(e) => setPassword(e.target.value)} style={{ borderColor: errorBorder }} onInput={loginSubstr} />
                        <br />
                        <p></p>
                        <input type="hidden" onChange={(e) => setPassword(e.target.value)} />
                        <br />
                        <span>{mainErrorMessage}</span>
                        <br />
                        <button onClick={handleLogIn}>let's go!</button>
                    </div>}

                {willSignUp &&
                    <div>
                        <p>Nickname?</p>
                        <input type="text" onChange={(e) => setNickname(e.target.value)} style={{ borderColor: errorBorder }} onInput={loginSubstr} />
                        <br />
                        <p>Login?</p>
                        <input type="text" onChange={(e) => setLogin(e.target.value)} style={{ borderColor: errorBorder }} onInput={loginSubstr} />
                        <br />
                        <p>Password?</p>
                        <input type="password" onChange={(e) => setPassword(e.target.value)} onInput={loginSubstr} />
                        <br />
                        <span>{mainErrorMessage}</span>
                        <br />
                        <button onClick={handleSignUp}>let's go!</button>
                    </div>}
            </div >
        </div>
    );
}

export default LoginField;
