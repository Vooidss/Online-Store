import React from 'react'

import { NavLink } from 'react-router-dom';


export default function Header() {
    const activeLink = "header__mainHeader__items__link--active";
    const defaultLink= "header__mainHeader__items__link";
        return (
            <header className="header">
                <div className = "header__mainHeader">
                    <h1 className = "header__mainHeader__logo">
                        Shop
                    </h1>
                    <ul className="header__mainHeader__items">
                        <li className="header__mainHeader__items">
                            <NavLink to="ShopList/TShirts" className = {({isActive}) => isActive ? activeLink : defaultLink}>
                                Футболки
                            </NavLink>
                        </li>
                        <li className="header__mainHeader__items">
                            <NavLink to="ShopList/Shorts" className = {({isActive}) => isActive ? activeLink : defaultLink}>
                                Шорты
                            </NavLink>
                        </li>
                        <li className="header__mainHeader__items">
                            <NavLink to="ShopList/Sneakers" className = {({isActive}) => isActive ? activeLink : defaultLink}>
                                Кроссовки
                            </NavLink>
                        </li>
                    </ul>
                    <div className="header__mainHeader__side">
                        <p className="header__mainHeader__side__basket">Корзина</p>
                        <p className="header__mainHeader__side__account">Аккаунт</p>
                    </div>
                </div>
            </header>
        )
}