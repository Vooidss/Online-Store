import React from 'react'

export default function ProductUser({ key, product }) {
    console.log(product)

    return (
        <div className="main_window_basket__basket__items__item">
            <div className="main_window_basket__basket__items__item__image"></div>
            <div className="main_window_basket__basket__items__item__info">
                <p className="main_window_basket__basket__items__item__info__brand">
                    {product.type} {product.brand} {product.model}
                </p>
                <p className="main_window_basket__basket__items__item__info__price">
                    {product.price}
                </p>
                <p className="main_window_basket__basket__items__item__info__size">
                    {product.size}
                </p>
                <p className="main_window_basket__basket__items__item__info__count">
                    {1}
                </p>
            </div>
        </div>
    )
}
