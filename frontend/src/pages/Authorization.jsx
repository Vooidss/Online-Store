import React, {useState} from 'react';


export default function Authorization({active,setActive}) {

    const activeLink = "mainWindow active";
    const defaultLink= "mainWindow";

    return (
        <div className={active ? activeLink : defaultLink } onClick={() => setActive(false)}>
            <div className="mainWindow__authenticationWindow" onClick={e => e.stopPropagation()}>
                <h1 className="mainWindow__authenticationWindow__head">Авторизация</h1>
                <div className="mainWindow__authenticationWindow__inputs">
                        <input className="mainWindow__authenticationWindow__input" placeholder="Логин"/>
                        <input className="mainWindow__authenticationWindow__input" placeholder="Пароль"/>
                </div>
                <button className="mainWindow__authenticationWindow__button">Войти</button>
            </div>
        </div>
    )
}