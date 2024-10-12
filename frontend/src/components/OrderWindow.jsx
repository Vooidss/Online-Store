import React, { useEffect, useState } from 'react'
import { CSSTransition, TransitionGroup } from 'react-transition-group'
import ProductUser from './ProductUser'
import { IoIosArrowRoundBack } from "react-icons/io";


export default function OrderWindow({}){

    return (
        <div className="main_window_basket__order">
            <div className="main_window_basket__order__head">
                <div className="main_window_basket__order__head__exit">
                    <IoIosArrowRoundBack className="main_window_basket__order__head__exit__arrow"/>
                    <p className="main_window_basket__order__head__exit__name">Вернуться</p>
                </div>
                <h1 className="main_window_basket__order__head__name">Оформление заказа</h1>
            </div>
            <div className="main_window_basket__order__execution">
            </div>
        </div>
    )
}