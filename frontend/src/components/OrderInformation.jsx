import React, { useEffect, useState } from 'react'
import LoadingComponent from './LoadingComponent'
import StatusComponent from './StatusComponent'

export default function OrderInformation({orderInformation,isOrder,addOrder,adress,recipient}){

    const [fullOrder, setFullOrder] = useState({
        orderPrice: orderInformation.price,
        discountPrice: orderInformation.discountPrice,
        resultPrice: orderInformation.price - orderInformation.discountPrice,
        adress: adress,
        recipient: recipient
    });
    const [isLoading, setLoading] = useState(false);
    const [status, setStatus] = useState(false);
    const [isLoaded, setLoaded] = useState(false);
    const [isFormValid, setFormValid] = useState(false);

    const defualtLik = "main_window_basket__right-nav__information__button-chapter__button";
    const disabletLik = "main_window_basket__right-nav__information__button-chapter__button disable";

    useEffect(() => {
        setFullOrder((prev) => ({
            ...prev,
            orderPrice: orderInformation.price,
            discountPrice: orderInformation.discountPrice,
            resultPrice: orderInformation.price - orderInformation.discountPrice,
            adress: adress,
            recipient: recipient,
        }));
    }, [orderInformation, adress, recipient]);

    useEffect(() => {
        console.log(fullOrder)
    }, [fullOrder,adress,recipient])

    useEffect(() => {
        if (isLoaded) {
            const timer = setTimeout(() => {
                setLoaded(false);
                window.location.reload();
            }, 1500);

            return () => clearTimeout(timer);
        }
    }, [isLoaded]);

    useEffect(() => {
        const isAddressComplete = adress.city && adress.street && adress.numberHouse && adress.numberApartment && adress.numberIntercom;
        const isRecipientComplete = recipient.name && recipient.secondName && recipient.phone;

        setFormValid(isAddressComplete && isRecipientComplete);
    }, [adress, recipient]);

    const token = localStorage.getItem("token");

    async function PlaceAnOrder(){
        setLoading(true)
        await fetch('http://localhost:8020/order/arrange',{
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json',
                'Authorization' : `Bearer ${token}`
            },
            body: JSON.stringify(fullOrder)
        })
            .then(response => response.json())
            .then(data => {
                setLoading(false)
                setLoaded(true)
                if(data.code === 200){
                    setStatus(true)
                }else{
                    setStatus(false)
                }
            })
            .catch(error => console.error(error))
    }

    return(
        <div className="main_window_basket__right-nav">
            <LoadingComponent isHidden={isLoading}/>
            <StatusComponent
                status={status}
                isLoading={isLoading}
                isLoaded={isLoaded}
                textAccepted="Заказ оформлен успешно"
                textRefused="Ошибка оформления заказа"
            />
            <div className="main_window_basket__head"></div>
            <div className="main_window_basket__right-nav__information">
                <div className="main_window_basket__right-nav__information__head">
                    <p className="main_window_basket__right-nav__information__head__name">Сумма заказа</p>
                </div>
                <div className="main_window_basket__right-nav__information__description">
                    <div className="main_window_basket__right-nav__information__description__count-price">
                        <div
                            className="main_window_basket__right-nav__information__description__count-price__count">{orderInformation.count} товара
                        </div>
                        <div
                            className="main_window_basket__right-nav__information__description__count-price__price">{orderInformation.price} ₽
                        </div>
                        <div
                            className="main_window_basket__right-nav__information__description__count-price__discount">Скидка
                        </div>
                        <div
                            className="main_window_basket__right-nav__information__description__count-price__discount-price">{orderInformation.discountPrice} ₽
                        </div>
                    </div>

                    <div className="main_window_basket__right-nav__information__description__result">
                        <div className="main_window_basket__right-nav__information__description__result__name">Итого
                        </div>
                        <div className="main_window_basket__right-nav__information__description__result__price">
                            {fullOrder.resultPrice} ₽
                        </div>
                    </div>
                </div>
                <div className="main_window_basket__right-nav__information__button-chapter">
                    {isOrder
                        ?
                        <button className={isFormValid ? defualtLik : disabletLik}
                                type="submit"
                                id="order_form"
                                onClick={PlaceAnOrder}
                        >
                            Оформить заказ
                        </button>
                        :
                        <button className="main_window_basket__right-nav__information__button-chapter__button"
                                onClick={addOrder}
                        >
                            Перейти к оформлению
                        </button>
                    }
                </div>
            </div>
        </div>
    )
}
