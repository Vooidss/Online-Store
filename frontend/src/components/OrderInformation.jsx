import React, { useEffect, useState } from 'react'

export default function OrderInformation({orderInformation}){

    const [resultPrice,setResultPrice] = useState(0);
    const [fullOrder, setFullOrder] = useState({
        price: orderInformation.price,
        discount: orderInformation.discountPrice,
        result_price: 0
    })

    useEffect(() => {
        addResultPrice()
    }, [])

    function addResultPrice(){
        setResultPrice(orderInformation.price - orderInformation.discountPrice);
        setFullOrder(order =>({
            ...order,
            result_price: resultPrice
        }));
        console.log(fullOrder)
    }

    async function PlaceAnOrder(){
        await fetch('http://localhost:8020/order/arrange',{
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json'
            },
            body: fullOrder
        })
            .then(response => response.json())
            .then(data => console.log(data))
            .catch(error => console.error(error))

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
                        <div className="main_window_basket__right-nav__information__description__result__price"> {resultPrice} ₽</div>
                    </div>

                </div>
                <div className="main_window_basket__right-nav__information__button-chapter">
                    <button className="main_window_basket__right-nav__information__button-chapter__button" onClick={PlaceAnOrder}>
                        Оформить заказ
                    </button>
                </div>
            </div>
        </div>
    )
}