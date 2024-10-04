import React, {useState} from 'react'

export default function OrderInformation({orderInformation}){

    console.log(orderInformation.count)

    function resultPrice(){
        return orderInformation.price - orderInformation.discountPrice;
    }

    return(
        <div className="main_window_basket__right-nav">
            <div className="main_window_basket__head"></div>
            <div className="main_window_basket__right-nav__information">
                <div className="main_window_basket__right-nav__information__head">
                    <p className="main_window_basket__right-nav__information__head__name">Сумма заказа</p>
                </div>
                <div className="main_window_basket__right-nav__information__description">
                    <div className="main_window_basket__right-nav__information__description__count-price">
                        <div className="main_window_basket__right-nav__information__description__count-price__count">{orderInformation.count} товара</div>
                        <div className="main_window_basket__right-nav__information__description__count-price__price">{orderInformation.price} ₽</div>
                        <div className="main_window_basket__right-nav__information__description__count-price__discount">Скидка</div>
                        <div className="main_window_basket__right-nav__information__description__count-price__discount-price">{orderInformation.discountPrice} ₽</div>
                    </div>

                    <div className="main_window_basket__right-nav__information__description__result">
                        <div className="main_window_basket__right-nav__information__description__result__name">Итого</div>
                        <div className="main_window_basket__right-nav__information__description__result__price"> {resultPrice()} ₽</div>
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