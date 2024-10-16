import React, { useEffect, useState } from 'react';

export default function Profile({ active, setActive, onLogout }) {
    const [user, setUser] = useState({
        name: '',
        login: '',
        money: 0
    });
    const [money, setMoney] = useState('');
    const token = localStorage.getItem('token');

    const handleChange = (e) => {
        const value = e.target.value;

        const numericValue = value.replace(/\D/g, '');

        setMoney(numericValue);
    };

    useEffect(() => {
        console.log("a");
    }, [user.money])

    async function topUpBalance(){
        const url = 'http://localhost:8060/user/replenishment'

        await fetch(url, {
            method: 'PATCH',
            headers: {
                'Content-Type' : 'application/json',
                Authorization: `Bearer ${token}`,
            },
            body: money
        })
            .then(response => response.json())
            .then(data => window.location.reload())
            .catch(error => console.error(error))
    }

    async function getUser() {
        const url = 'http://localhost:8060/user/current';

        try {
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                }
            });

            const data = await response.json();

            console.log(data);

            setUser({
                name: data.name,
                login: data.login,
                money: data.money
            });
        } catch (e) {
            console.log(e);
        }
    }

    useEffect(() => {
        getUser();
    }, []);

    const activeLink = 'main-window active';
    const defaultLink = 'main-window';

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
                    {user.name}
                </h1>
                <ul className="main-window__window-profile__info">
                    <li>Логин: {user.login}</li>
                    <li>Кошелёк: {new Intl.NumberFormat('ru-RU', { style: 'currency', currency: 'RUB' }).format(user.money)}</li>
                </ul>
                <div className="main-window__window-profile__buttons">
                    <div className="main-window__window-profile__buttons__replenish">
                        <button
                            id="replenish"
                            onClick={topUpBalance}
                        >
                            Пополнить
                        </button>
                        <input
                            placeholder="Сумма"
                            value={money}
                            onChange={handleChange}
                        />
                    </div>
                    <button
                        id="logout"
                        onClick={onLogout}
                    >
                        Выйти
                    </button>
                </div>
            </div>
        </div>
    );
}
