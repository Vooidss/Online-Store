import React, { useEffect, useState } from 'react'

import { NavLink } from 'react-router-dom'
import Authentication from '../pages/Authorization'
import Profile from './ProfileWindow'
import { GiBasketballBasket } from "react-icons/gi";


export default function Header({ modalActive,setModalActive, setAuthorization }) {
    const [isAuthentication, setAuthentication] = useState(false)

    const token = localStorage.getItem('token')

    async function isTokenExpired() {
        try {
            const response = await fetch(
                'http://localhost:8060/auth/authentication/isTokenExpired',
                {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${token}`,
                    },
                },
            )

            const data = await response.json()

            console.log(data)

            setAuthentication(!data.tokenExpired)
        } catch (error) {
            console.error('Network error:', error)
        }
    }

    useEffect(() => {
        setAuthentication(!!token)
        isTokenExpired()
    }, [])

    useEffect(() => {
        setAuthorization(isAuthentication)
    }, [isAuthentication]);


    const handleLogout = async () => {
        localStorage.removeItem('token')
        localStorage.removeItem('user')
        setAuthentication(false)
        setModalActive(false)

        const url = 'http://localhost:8060/auth/authentication/logout'

        try {
            await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
            })
        } catch (e) {
            console.log(e)
        }
    }

    const activeLink = 'header__mainHeader__items__link--active'
    const defaultLink = 'header__mainHeader__items__link'

    return (
        <header className="header">
            <div className="header__all-components">

            </div>
            <div className="header__mainHeader">
                <div className="header__mainHeader__logo"><p className="header__mainHeader__logo__name">Hukising</p></div>
                <ul className="header__mainHeader__items">
                    <li className="header__mainHeader__items">
                        <NavLink
                            to="ShopList/TShirts"
                            className={({ isActive }) =>
                                isActive ? activeLink : defaultLink
                            }
                        >
                            Футболки
                        </NavLink>
                    </li>
                    <li className="header__mainHeader__items">
                        <NavLink
                            to="ShopList/Shorts"
                            className={({ isActive }) =>
                                isActive ? activeLink : defaultLink
                            }
                        >
                            Шорты
                        </NavLink>
                    </li>
                    <li className="header__mainHeader__items">
                        <NavLink
                            to="ShopList/Sneakers"
                            className={({ isActive }) =>
                                isActive ? activeLink : defaultLink
                            }
                        >
                            Кроссовки
                        </NavLink>
                    </li>
                </ul>
                <div className="header__mainHeader__side">
                    <div className="header__mainHeader__side__components">
                        <NavLink to="/Basket" className="header__mainHeader__side__components__basket">
                            <p className="header__mainHeader__side__components__basket__name">
                                Корзина
                            </p>
                            <GiBasketballBasket className="header__mainHeader__side__components__basket__logo" />
                        </NavLink>
                        <div
                            className="header__mainHeader__side__components__account"
                            onMouseEnter={() => setModalActive(true)}
                        >
                            {isAuthentication ? 'Профиль' : 'Войти'}
                        </div>
                    </div>
                </div>
            </div>
            {isAuthentication ? (
                <Profile
                    active={modalActive}
                    setActive={setModalActive}
                    onLogout={handleLogout}
                />
            ) : (
                <Authentication
                    active={modalActive}
                    setActive={setModalActive}
                    setAuthentication={setAuthentication}
                />
            )}
        </header>
    )
}
