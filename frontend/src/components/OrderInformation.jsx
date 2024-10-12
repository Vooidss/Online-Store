import React, { useEffect, useState } from 'react'

export default function OrderInformation({orderInformation,isOrder,setOrder}){

    const [fullOrder, setFullOrder] = useState({
        orderPrice: orderInformation.price,
        discountPrice: orderInformation.discountPrice,
        resultPrice: orderInformation.price - orderInformation.discountPrice // Вычисляем результат сразу
    });

    const token = localStorage.getItem("token");

    async function PlaceAnOrder(){
        await fetch('http://localhost:8020/order/arrange',{
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json',
                'Authorization' : `Bearer ${token}`
            },
            body: JSON.stringify(fullOrder)
        })
            .then(response => response.json())
            .then(data => console.log(data))
            .catch(error => console.error(error))
    }

    useEffect(() => {
        setFullOrder(order => ({
            ...order,
            resultPrice: orderInformation.price - orderInformation.discountPrice
        }));
    }, [orderInformation]); // Добавляем зависимость от orderInformation


    function addOrder(){
        if(isOrder === false){
            setOrder(true);
        }else{
            setOrder(false);
        }
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
                        <div className="main_window_basket__right-nav__information__description__result__price">
                            {fullOrder.resultPrice} ₽
                        </div>
                    </div>

                </div>
                <div className="main_window_basket__right-nav__information__button-chapter">
                    {isOrder
                        ?
                        <button className="main_window_basket__right-nav__information__button-chapter__button"
                                onClick={addOrder}>
                            Оформить заказ
                        </button>
                        :
                        <button className="main_window_basket__right-nav__information__button-chapter__button"
                                onClick={addOrder}>
                            Перейти к оформлению
                        </button>
                    }

                </div>
            </div>
        </div>
    )
}
