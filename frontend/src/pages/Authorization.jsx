import React, { useEffect, useState } from 'react'

export default function Authorization({
    active,
    setActive,
    setAuthentication,
}) {
    const [login, setLogin] = useState('')
    const [password, setPassword] = useState('')

    const[user,setUser] = useState({
        name : '',
        secondName : '',
        login : '',
        password: '',
        email: ''
    })

    const [isError, setError] = useState(false)
    const [isReg, setReg] = useState(false)

    const activeLink = 'mainWindow active'
    const defaultLink = 'mainWindow'

    function setRegistration(){
        if(isReg === false){
            setReg(true);
        }else{
            setReg(false)
        }
    }

    const registration = async e => {
        e.preventDefault()
            await fetch(
                'http://localhost:8060/auth/registration',
                {
                    method: 'POST',
                    headers: {
                        'Content-Type' : 'application/json'
                    },
                    body: JSON.stringify(user)
                }
            )
                .then(response => response.json())
                .then(data => {
                        if (data.code === 200) {
                            setReg(false);
                        }else{
                            console.error(data.error)
                        }
                    }
                )
                .then(error => console.error(error))

        }

    const authentication = async e => {
        e.preventDefault()
        const credentials = { login, password }

        try {
            const response = await fetch(
                'http://localhost:8060/auth/authentication',
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(credentials),
                },
            )

            const data = await response.json()

            if (data.code === 200) {
                console.log('Success:', data.token)
                localStorage.setItem('token', data.token)
                setError(data.failed)

                setAuthentication(true)
                setActive(false)
            } else {
                console.error('Error:', data.error)
                setError(data.failed)
            }
        } catch (error) {
            console.error('Network error:', error)
        }
    }

    return (
        <div
            className={active ? activeLink : defaultLink}
            onClick={() => setActive(false)}
        >{isReg ? (
            <div
                className="mainWindow__authenticationWindow" style = {{
                    height: '500px'
            }
            }
                onClick={e => e.stopPropagation()}
            >
                <h1 className="mainWindow__authenticationWindow__head">
                    Регистрация
                </h1>
                <div className="mainWindow__authenticationWindow__inputs">
                    <input
                        required
                        className="mainWindow__authenticationWindow__input"
                        placeholder="Логин"
                        value={user.login}
                        onChange={e => setUser(user => ({
                            ...user,
                            login: e.target.value}
                        ))}
                    />
                    <input
                        required
                        className="mainWindow__authenticationWindow__input"
                        placeholder="Пароль"
                        value={user.password}
                        onChange={e => setUser(user => ({
                                ...user,
                                password: e.target.value}
                        ))}
                    />
                    <input
                        required
                        className="mainWindow__authenticationWindow__input"
                        placeholder="Имя"
                        value={user.name}
                        onChange={e => setUser(user => ({
                                ...user,
                                name: e.target.value}
                        ))}
                    />
                    <input
                        className="mainWindow__authenticationWindow__input"
                        required
                        placeholder="Фамилия"
                        value={user.secondName}
                        onChange={e => setUser(user => ({
                                ...user,
                                secondName: e.target.value}
                        ))}
                    />
                    <input
                        required
                        type="email"
                        className="mainWindow__authenticationWindow__input"
                        placeholder="Почта"
                        value={user.email}
                        onChange={e => setUser(user => ({
                                ...user,
                                email: e.target.value}
                        ))}
                    />
                </div>
                <div className="mainWindow__authenticationWindow__under">
                    <button
                        className="mainWindow__authenticationWindow__under__botton"
                        onClick={registration}
                    >
                        Зарегистрироваться
                    </button>
                    <a className="mainWindow__authenticationWindow__under__checkout" onClick={setRegistration}>
                        Войти
                    </a>
                </div>
            </div>

        ) : (
            <div
                className="mainWindow__authenticationWindow"
                onClick={e => e.stopPropagation()}
                style ={{
                    gridTemplateRows: '40% 40% 20%'
                }}
            >
                <h1 className="mainWindow__authenticationWindow__head">
                    Авторизация
                </h1>
                <div className="mainWindow__authenticationWindow__inputs">
                    <input
                        className="mainWindow__authenticationWindow__input"
                        placeholder="Логин"
                        value={login}
                        onChange={e => setLogin(e.target.value)}
                    />
                    <input
                        className="mainWindow__authenticationWindow__input"
                        placeholder="Пароль"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                    />
                    <a
                        className={
                            isError
                                ? 'mainWindow__authenticationWindow__error active'
                                : 'mainWindow__authenticationWindow__error'
                        }
                    >
                        Неверный логин или пароль
                    </a>
                </div>
                <div className="mainWindow__authenticationWindow__under">
                    <button
                        className="mainWindow__authenticationWindow__under__botton"
                        onClick={authentication}
                    >
                        Войти
                    </button>
                    <a className="mainWindow__authenticationWindow__under__checkout" onClick={setRegistration}>
                        Зарегестрироваться
                    </a>
                </div>
            </div>
        )}
        </div>
    )
}
