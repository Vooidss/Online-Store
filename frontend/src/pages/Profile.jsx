import React, {useEffect} from 'react';


export default function profile({active,setActive, onLogout}) {

    const activeLink = "main-window active";
    const defaultLink= "main-window";

    return (
        <div className={active ? activeLink : defaultLink } onClick={() => setActive(false)}>
            <div className="main-window__window-profile" onClick={e => e.stopPropagation()}>
                <h1 className="main-window__window-profile__head">Профиль</h1>
                <ul className="main-window__window-profile__info">
                    <li>Имя</li>
                    <li>Фамилия</li>
                    <li>Почта</li>
                </ul>
                <button className="main-window__window-profile__logout" onClick={onLogout}>Выйти</button>
            </div>
        </div>
    )
}