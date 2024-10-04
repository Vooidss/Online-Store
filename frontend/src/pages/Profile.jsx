import React, { useEffect, useState } from 'react'

export default function Profile({ active, setActive, onLogout }) {
    const [user, setUser] = useState({
        name: '',
        login: '',
        money: 0
    })

    async function getUser() {
        const url = 'http://localhost:8060/user/current'
        const token = localStorage.getItem('token')

        try {
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
            })

            const data = await response.json()

            console.log(data)

            setUser(user => ({
                name: data.name,
                login: data.login,
                money: data.money
            }))
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        getUser()
    }, [])

    const activeLink = 'main-window active'
    const defaultLink = 'main-window'

    return (
        <div
            className={active ? activeLink : defaultLink}
            onClick={() => setActive(false)}
        >
            <div
                className="main-window__window-profile"
                onClick={e => e.stopPropagation()}
            >
                <h1 className="main-window__window-profile__head">
                    {user.login}
                </h1>
                <ul className="main-window__window-profile__info">
                    <li>Имя: {user.name}</li>
                    <li>Кошелёк: {user.money}</li>
                </ul>
                <button
                    className="main-window__window-profile__logout"
                    onClick={onLogout}
                >
                    Выйти
                </button>
            </div>
        </div>
    )
}
