import React from 'react'
import { useNavigate } from 'react-router-dom'
import { BsBasket2Fill } from 'react-icons/bs'
import { jwtDecode } from 'jwt-decode'

export default function Product({ product, productName }) {
    const navigate = useNavigate()
    const type = productName

    const handleClick = () => {
        navigate(`/${type}/${product.product.id}`)
    }

    const handleClickBasket = async () => {
        const token = localStorage.getItem('token')
        const productId = product.id
        const productCount = 1;

        const credentials = { productId, token }

        try {
            const res = await fetch('http://localhost:8050/basket/save', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(credentials),
            })
        } catch (e) {
            console.log(e)
        }
    }

    return (
        <div className="section__item">
            <div
                className="section__item__components"
                onClick={() => handleClick()}
            >
                <div className="section__item__components__image">
                    <img
                        className="section__item__components__image__self"
                        src={product.img}
                        alt={'sad'}
                    />
                </div>
                <div className="section__item__components__items">
                    <div className="section__item__components__items__component">
                        Название: {product.brand}
                    </div>
                    <div className="section__item__components__items__component">
                        Модель: {product.model}
                    </div>
                    <div className="section__item__components__items__component">
                        Размер: {product.size}
                    </div>
                    <div className="section__item__components__items__component">
                        Цена: {product.price}
                    </div>
                </div>
            </div>
            <div className="section__item__components__basket">
                <BsBasket2Fill onClick={() => handleClickBasket()} />
            </div>
        </div>
    )
}
