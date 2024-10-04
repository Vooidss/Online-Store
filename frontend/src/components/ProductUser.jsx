import React, { useEffect, useState } from 'react';
import { RiDeleteBin6Line } from 'react-icons/ri';

export default function ProductUser({ product, onDelete, updateProductCount }) {
    const [count, setCount] = useState(product.count);
    const [totalPrice, setTotalPrice] = useState(product.price);

    const [isBlack, setBlack] = useState(false);
    const [isActive, setActive] = useState(false);

    useEffect(() => {
        setTotalPrice(product.price * count); // Обновляем цену на основе количества
        updateProductCount(product.id, count); // Обновляем общее количество продуктов в корзине
    }, [count, product.price]);

    const addCount = () => {
        setCount((prevCount) => prevCount + 1);
    };

    const subCount = () => {
        if (count > 1) {
            setCount((prevCount) => prevCount - 1);
        }
    };

    const isMouseEnter = () => {
        setActive(true);
    };

    const isMouseLeave = () => {
        setActive(false);
    };

    const MouseEnter = () => {
        setBlack(true);
    };

    const MouseLeave = () => {
        setBlack(false);
    };

    async function deleteItem() {
        const id = product.id;
        const url = `http://localhost:8050/basket/delete/${id}`;
        const token = localStorage.getItem('token');

        try {
            await fetch(url, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
            });

            onDelete(id); // Удаление товара после ответа
        } catch (e) {
            console.log(e);
        }
    }

    return (
        <div className="main_window_basket__basket__items__item" onMouseEnter={isMouseEnter} onMouseLeave={isMouseLeave}>
            <img className="main_window_basket__basket__items__item__image" src={product.img} alt="product" />
            <div className="main_window_basket__basket__items__item__info">
                <p className="main_window_basket__basket__items__item__info__brand">{product.description}</p>
                <div className="main_window_basket__basket__items__item__info__price_count">
                    <p className="main_window_basket__basket__items__item__info__price_count__price">{totalPrice} ₽</p>

                    <div className="main_window_basket__basket__items__item__info__price_count__count">
                        <div className="main_window_basket__basket__items__item__info__price_count__count__minus" onClick={subCount}>-</div>
                        <div className="main_window_basket__basket__items__item__info__price_count__count__number">{count}</div>
                        <div className="main_window_basket__basket__items__item__info__price_count__count__plus" onClick={addCount}>+</div>
                    </div>
                </div>
                <p className="main_window_basket__basket__items__item__info__size">Размер: {product.size}</p>
            </div>
            <div className="main_window_basket__basket__items__item__info__delete" onMouseEnter={MouseEnter} onMouseLeave={MouseLeave} onClick={deleteItem}>
                <p className="main_window_basket__basket__items__item__info__delete__name" style={{
                    color: isBlack ? 'black' : 'rgba(0, 0, 0, 0.32)',
                    transform: isActive ? 'scale(1)' : 'scale(0)',
                }}>Удалить</p>
                <RiDeleteBin6Line className="main_window_basket__basket__items__item__info__delete__icon" style={{
                    color: isBlack ? 'black' : 'rgba(0, 0, 0, 0.32)',
                    transform: isActive ? 'scale(1)' : 'scale(0)',
                }} />
            </div>
        </div>
    );
}
