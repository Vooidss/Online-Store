import React, { useState } from 'react'

export default function Authorization({
    active,
    setActive,
    setAuthentication,
}) {
    const [login, setLogin] = useState('')
    const [password, setPassword] = useState('')
    const [isError, setError] = useState(false)

    const activeLink = 'mainWindow active'
    const defaultLink = 'mainWindow'

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
        >
            <div
                className="mainWindow__authenticationWindow"
                onClick={e => e.stopPropagation()}
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
                <button
                    className="mainWindow__authenticationWindow__button"
                    onClick={authentication}
                >
                    Войти
                </button>
            </div>
        </div>
    )
}
