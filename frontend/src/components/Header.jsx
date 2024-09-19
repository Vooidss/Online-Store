import React, {useState} from 'react'

import { NavLink } from 'react-router-dom';
import Authentication from "../pages/Authorization";

export default function Header({modalActive, setModalActive}) {
    const activeLink = "header__mainHeader__items__link--active";
    const defaultLink= "header__mainHeader__items__link";

        return (
            <header className="header">
                <div className = "header__mainHeader">
                    <h1 className = "header__mainHeader__logo">
                        Hukising
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
                        <p className="header__mainHeader__side__basket">
                            <NavLink to="/Basket">
                                Корзина
                            </NavLink>
                        </p>
                        <p className="header__mainHeader__side__account" onClick={() => setModalActive(true)}>Профиль</p>
                    </div>
                </div>
                <Authentication active={modalActive} setActive={setModalActive}/>
            </header>
        )
}