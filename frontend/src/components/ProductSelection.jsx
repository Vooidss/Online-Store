import { useParams} from 'react-router-dom'
import { useEffect, useState } from 'react'

export default function ProductSelection() {
    const { type,id } = useParams()
    const [info, setProducts] = useState({
        img: '',
        brand: '',
        description: '',
        price: 0,
        priceWithDiscount: 0,
        color: '',
        size: ''
    })
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        setLoading(true)
        fetch(`http://localhost:8071/products/id/${id}`)
            .then(res => res.json())
            .then(data => {
                setProducts({
                    img: data.product.img,
                    brand: data.product.brand,
                    description: data.product.description,
                    price: data.product.price,
                    priceWithDiscount: data.product.priceWithDiscount,
                    color: data.product.color,
                    size: data.product.size
                })
                setLoading(false)
            })
            .catch(error => {
                console.log(error)
                setLoading(false)
            });
    }, [id])

    if (loading) {
        return <div>Loading...</div>
    }

    return (
        <div className="main-selection-product">
            <div className="main-selection-product__left">
                <div className="main-selection-product__left__image-block">
                    <div className="main-selection-product__left__image-block__image">
                        <img src={info.img} alt="Product" />
                    </div>
                </div>
            </div>
            <div className="main-selection-product__info">
                <h3 className="main-selection-product__info__brand">{info.brand}</h3>
                <h2 className="main-selection-product__info__description">{info.description}</h2>
                <h1 className="main-selection-product__info__price">
                    {info.priceWithDiscount ? <p id = "discount">{info.priceWithDiscount} ₽</p> : ''}
                    {info.priceWithDiscount ? <p id = "oldPrice">{info.price} ₽</p> : `${info.price} ₽`}
                </h1>
                <p className="main-selection-product__info__color">Цвет: <p>{info.color}</p></p>
                <div className="main-selection-product__info__sizes">
                    <p className="main-selection-product__info__sizes__label">
                        Выберите размер:
                    </p>
                    <div className="main-selection-product__info__sizes__components">
                        <div className="main-selection-product__info__sizes__components__size">
                            <p>{info.size}</p>
                        </div>
                        <div className="main-selection-product__info__sizes__components__size">
                            <p>{info.size}</p>
                        </div>
                        <div className="main-selection-product__info__sizes__components__size">
                            <p>{info.size}</p>
                        </div>
                        <div className="main-selection-product__info__sizes__components__size">
                            <p>{info.size}</p>
                        </div>
                        <div className="main-selection-product__info__sizes__components__size">
                            <p>{info.size}</p>
                        </div>
                        <div className="main-selection-product__info__sizes__components__size">
                            <p>{info.size}</p>
                        </div>
                        <div className="main-selection-product__info__sizes__components__size">
                            <p>{info.size}</p>
                        </div>
                        <div className="main-selection-product__info__sizes__components__size">
                            <p>{info.size}</p>
                        </div>
                        <div className="main-selection-product__info__sizes__components__size">
                            <p>{info.size}</p>
                        </div>
                        <div className="main-selection-product__info__sizes__components__size">
                            <p>{info.size}</p>
                        </div>
                    </div>
                </div>
                <button>Добавить в корзину</button>
            </div>
        </div>
    )
}
