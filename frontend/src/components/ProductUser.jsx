import React, { useEffect, useState } from 'react';
import { RiDeleteBin6Line } from 'react-icons/ri';

export default function ProductUser({ product, onDelete, updateProductCount }) {
    const [count, setCount] = useState(product.count);
    const [isBlack, setBlack] = useState(false);
    const [isActive, setActive] = useState(false);

    const totalPrice =  product.price * count;

    useEffect(() => {
        updateProductCount(product.id, count);
        updateCountProduct();
    }, [count, product.id]);

    async function updateCountProduct() {
        try {
            const response = await fetch(`http://localhost:8050/basket/update/${product.basketId}/${count}`, {
                method: 'PATCH',
                headers: { 'Content-Type': 'application/json' },
            });
            const data = await response.json();
            console.log(data);
        } catch (error) {
            console.error(error);
        }
    }

    const addCount = () => setCount((prevCount) => prevCount + 1);

    const subCount = () => {
        if (count > 1) setCount((prevCount) => prevCount - 1);
    };

    const deleteItem = async () => {
        const url = `http://localhost:8050/basket/delete/${product.id}`;
        const token = localStorage.getItem('token');

        try {
            await fetch(url, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
            });
            onDelete(product.id);
        } catch (e) {
            console.log(e);
        }
    };

    const deleteStyle = {
        color: isBlack ? 'black' : 'rgba(0, 0, 0, 0.32)',
        transform: isActive ? 'scale(1)' : 'scale(0)',
    };

    return (
        <div className="main_window_basket__basket__items__item"
             onMouseEnter={() => setActive(true)}
             onMouseLeave={() => setActive(false)}>
            <img className="main_window_basket__basket__items__item__image" src={product.img} alt="product" />
            <div className="main_window_basket__basket__items__item__info">
                <p className="main_window_basket__basket__items__item__info__brand">{product.description}</p>
                <div className="main_window_basket__basket__items__item__info__price_count">
                    <p className="main_window_basket__basket__items__item__info__price_count__price">{totalPrice} ₽</p>
                    <div className="main_window_basket__basket__items__item__info__price_count__count">
                        <div className="main_window_basket__basket__items__item__info__price_count__count__minus" onClick={subCount}>-</div>
                        <div>{count}</div>
                        <div className="main_window_basket__basket__items__item__info__price_count__count__plus" onClick={addCount}>+</div>
                    </div>
                </div>
                <p className="main_window_basket__basket__items__item__info__size">Размер: {product.size}</p>
            </div>
            <div className="main_window_basket__basket__items__item__info__delete"
                 onMouseEnter={() => setBlack(true)}
                 onMouseLeave={() => setBlack(false)}
                 onClick={deleteItem}>
                <p className="main_window_basket__basket__items__item__info__delete__name" style={deleteStyle}>Удалить</p>
                <RiDeleteBin6Line style={deleteStyle} />
            </div>
        </div>
    );
}
