import React from 'react';
import { IoIosArrowRoundBack } from "react-icons/io";
import CustomInput from '../util/CustomInput';

export default function OrderWindow({ addOrder, setAdress, setRecipient }) {
    return (
        <div className="main_window_basket__order">
            <div className="main_window_basket__order__head">
                <div className="main_window_basket__order__head__exit" onClick={addOrder}>
                    <IoIosArrowRoundBack className="main_window_basket__order__head__exit__arrow" />
                    <p className="main_window_basket__order__head__exit__name">Вернуться</p>
                </div>
                <h1 className="main_window_basket__order__head__name">Оформление заказа</h1>
            </div>
            <div className="main_window_basket__order__execution">
                <div className="main_window_basket__order__execution__info-customer">
                    <h1>Куда доставить заказ?</h1>
                    <form className="main_window_basket__order__execution__components" id="order_form">
                        <CustomInput
                            text={'Город'}
                            type={'text'}
                            setValue={(value) => setAdress((prev) => ({ ...prev, city: value }))} />
                        <CustomInput
                            text={'Улица'}
                            type={'text'}
                            setValue={(value) => setAdress((prev) => ({ ...prev, street: value }))} />
                        <CustomInput
                            text={'Дом'}
                            type={'text'}
                            setValue={(value) => setAdress((prev) => ({ ...prev, numberHouse: value }))}
                            isDigit={true}
                        />
                        <CustomInput
                            text={'Квартира'}
                            type={'text'}
                            setValue={(value) => setAdress((prev) => ({ ...prev, numberApartment: value }))}
                            isDigit={true}
                        />
                        <CustomInput
                            text={'Домофон'}
                            type={'text'}
                            setValue={(value) => setAdress((prev) => ({ ...prev, numberIntercom: value }))}
                            isDigit={true}
                        />
                    </form>
                </div>
                <div className="main_window_basket__order__execution__info-customer">
                    <h1>Кто заберёт заказ?</h1>
                    <form className="main_window_basket__order__execution__info-customer__info"
                          id="order_form"
                    >
                        <CustomInput
                            text={"Имя"}
                            type={'text'}
                            setValue={(value) => setRecipient((prev) => ({ ...prev, name: value }))}
                        />
                        <CustomInput
                            text={"Фамилия"}
                            type={'text'}
                            setValue={(value) => setRecipient((prev) => ({ ...prev, secondName: value }))} />
                        <CustomInput
                            text={"Телефон"}
                            type={'text'}
                            setValue={(value) => setRecipient((prev) => ({ ...prev, phone: value }))}
                            isDigit={true}
                        />
                    </form>
                </div>
            </div>
        </div>
    );
}
