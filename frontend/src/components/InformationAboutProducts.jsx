import React from 'react'

export default function InformationAboutProducts(){

    return(
        <div className="main_window_basket__right-nav">
            <div className="main_window_basket__head"></div>
            <div className="main_window_basket__right-nav__information">
                <div className="main_window_basket__right-nav__information__head">
                    <p className="main_window_basket__right-nav__information__head__name">Сумма заказа</p>
                </div>
                <div className="main_window_basket__right-nav__information__description">
                    <div className="main_window_basket__right-nav__information__description__count-price">
                        <div className="main_window_basket__right-nav__information__description__count-price__count">2 товара</div>
                        <div className="main_window_basket__right-nav__information__description__count-price__price">150 000 ₽</div>
                        <div className="main_window_basket__right-nav__information__description__count-price__discount">Скидка</div>
                        <div className="main_window_basket__right-nav__information__description__count-price__discount-price">20 000 ₽</div>
                    </div>

                    <div className="main_window_basket__right-nav__information__description__result">
                        <div className="main_window_basket__right-nav__information__description__result__name">Итого</div>
                        <div className="main_window_basket__right-nav__information__description__result__price"> 130 000 ₽</div>
                    </div>

                </div>
                <div className="main_window_basket__right-nav__information__button-chapter">
                    <button className="main_window_basket__right-nav__information__button-chapter__button">
                        Оформить заказ
                    </button>
                </div>
            </div>
        </div>
    )
}