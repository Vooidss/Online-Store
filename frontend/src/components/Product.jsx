import { useNavigate } from 'react-router-dom'
import { BsBasket2Fill } from 'react-icons/bs'
import DropDownBlock from '../util/DropDownBlock'
import React, { useEffect, useState } from 'react'

export default function Product({product, productName }) {
    const navigate = useNavigate()
    const [sizeProduct,setSize] = useState(product.listSize[0]);

    useEffect(() => {
    }, [sizeProduct])

    const handleClick = () => {
        navigate(`/${product.type}/${product.id}`)
    }

    const handleClickBasket = async () => {
        const token = localStorage.getItem('token')
        const productId = product.id

        const credentials = { productId, token, sizeProduct }

        try {
            await fetch('http://localhost:8050/basket/save', {
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
            >
                <div className="section__item__components__image" onClick={() => handleClick()}>
                    <div className="section__item__components__image__discount-baner"
                    style = {{
                        display: product.discount > 0 ? 'flex' : 'none'
                    }}
                    >
                        <h1 className="section__item__components__image__discount-baner__baner">{product.discount}%</h1>
                    </div>
                    <img
                        className="section__item__components__image__self"
                        src={product.img}
                        alt={'sad'}
                    />
                </div>
                <div className="section__item__components__items">
                    <div className="section__item__components__items__component">
                        Бренд: {product.brand}
                    </div>
                    <div className="section__item__components__items__component">
                        Модель: {product.model}
                    </div>
                    <div className="section__item__components__items__component">
                        <div className="dropdown">
                            <label htmlFor = "size">Размер: </label>
                            <select
                                className="select"
                                value={sizeProduct}
                                onChange={(e) => setSize(e.target.value)}>
                                {product.listSize.map((size) =>{
                                    return(
                                        <DropDownBlock
                                            value={size}
                                        />
                                    )
                                })}
                            </select>
                        </div>
                    </div>
                    <div className="section__item__components__items__component">
                        Цена: {
                        product.priceDiscount || product.priceDiscount !== 0 ?
                            <p id="discount"> {product.priceWithDiscount}</p> :
                            product.price}
                        { product.priceDiscount || product.priceDiscount !== 0
                            ? <p id="oldPrice"> {product.price} </p>
                            : ''}
                    </div>
                </div>
            </div>
            <div className="section__item__components__basket">
                <BsBasket2Fill onClick={() => handleClickBasket()} />
            </div>
        </div>
    )
}
