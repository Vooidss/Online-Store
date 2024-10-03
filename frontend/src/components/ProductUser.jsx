import React, {useEffect, useState} from 'react'
import {RiDeleteBin6Line} from "react-icons/ri";


export default function ProductUser({key,product }) {
    const [count, setCount] = useState(1);
    const [price, setPrice] = useState(product.price);
    const [totalPrice, setTotalPrice] = useState(product.price);

    const[isBlack, setBlack] = useState(false);
    const[isActive, setActive] = useState(false);

    const blockScroll = () => {
        document.body.style.overflow = 'hidden';
    };

    const allowScroll = () => {
        document.body.style.overflow = '';
    };

    useEffect(() => {
        blockScroll();
        return () => {
            allowScroll();
        };
    }, []);


    useEffect(() => {

        setTotalPrice(product.price * count);
    }, [count, product.price]);

    function addCount(){
        setCount(prevCount => prevCount + 1);
    }

    function subCount(){
        if(count > 1) {
            setCount(prevCount => prevCount - 1);
        }
    }

    function isMouseEnter(){
        setActive(true);
    }
    function isMouseLeave(){
        setActive(false);
    }

    function MouseEnter(){
        setBlack(true);
    }

    function MouseLeave(){
        setBlack(false);
    }

    async function deleteItem(){
        const url = `http://localhost:8050/basket/delete/${key}}`

        try {

            const response = await fetch(url, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            })

            const data = response.json();

            console.log(data)
        }catch (e){
            console.log(e);
        }

    }


    return (
        <div className="main_window_basket__basket__items__item" onMouseEnter={isMouseEnter} onMouseLeave={isMouseLeave}>
                <img className="main_window_basket__basket__items__item__image" src = {product.img} alt = "product"></img>
            <div className="main_window_basket__basket__items__item__info">
                <p className="main_window_basket__basket__items__item__info__brand">
                    {product.description}
                </p>
                <div className="main_window_basket__basket__items__item__info__price_count">
                    <p className="main_window_basket__basket__items__item__info__price_count__price">{totalPrice} ₽</p>

                    <div className="main_window_basket__basket__items__item__info__price_count__count">
                        <div className="main_window_basket__basket__items__item__info__price_count__count__minus" onClick={subCount}>-</div>
                        <div className="main_window_basket__basket__items__item__info__price_count__count__number">{count}</div>
                        <div className="main_window_basket__basket__items__item__info__price_count__count__plus" onClick={addCount}>+</div>
                    </div>
                </div>
                <p className="main_window_basket__basket__items__item__info__size">
                    Размер: {product.size}
                </p>
            </div>
            <div className="main_window_basket__basket__items__item__info__delete" onMouseEnter={MouseEnter} onMouseLeave={MouseLeave}>
                <p className="main_window_basket__basket__items__item__info__delete__name" style={{
                    color : isBlack ? 'black' : 'rgba(0, 0, 0, 0.32)',
                    transform : isActive ? 'scale(1)' : 'scale(0)'
                    }} onClick={deleteItem}>Удалить</p>
                <RiDeleteBin6Line className="main_window_basket__basket__items__item__info__delete__icon" style={{
                    color : isBlack ? 'black' : 'rgba(0, 0, 0, 0.32)',
                    transform : isActive ? 'scale(1)' : 'scale(0)'
                }}/>
            </div>
        </div>
    )
}
