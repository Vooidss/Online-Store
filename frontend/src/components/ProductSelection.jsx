import { useParams} from 'react-router-dom'
import { useEffect, useState } from 'react'
import SizeBlock from '../util/SizeBlock'

export default function ProductSelection() {
    const { type,id } = useParams();
    const [info, setInfo] = useState({
        img: '',
        brand: '',
        description: '',
        price: 0,
        priceWithDiscount: 0,
        color: '',
        discount: 0,
        sizes: []
    })
    const [loading, setLoading] = useState(true)
    const [sizeProduct, setTakingSize] = useState();

    useEffect(() => {
        console.log(info)
    }, [info])

    const handleClickBasket = async () => {
        const token = localStorage.getItem('token')
        const productId = id

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

    useEffect(() => {
        setLoading(true)
        fetch(`http://localhost:8071/products/id/${id}`)
            .then(res => res.json())
            .then(data => {
                console.log(data)
                setInfo({
                    img: data.product.img || '',
                    brand: data.product.brand || '',
                    description: data.product.description || '',
                    price: data.product.price || 0,
                    priceWithDiscount: data.product.priceWithDiscount || '',
                    color: data.product.color || '',
                    sizes: data.product.listSize || [],
                    discount: data.product.discount || 0
                })
                setTakingSize(info.sizes[0])
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
                    {info.discount || info.discount !== 0 ? <p id = "discount">{info.priceWithDiscount} ₽</p> : ''}
                    {info.discount || info.discount !== 0 ? <p id = "oldPrice">{info.price} ₽</p> : `${info.price} ₽`}
                </h1>
                <p className="main-selection-product__info__color">Цвет: <p>{info.color}</p></p>
                <div className="main-selection-product__info__sizes">
                    <p className="main-selection-product__info__sizes__label">
                        Выберите размер:
                    </p>
                    <div className="main-selection-product__info__sizes__components">
                            {info.sizes.map((size) =>{
                                return(
                                    <SizeBlock
                                        size={size}
                                        sizeProduct = {sizeProduct}
                                        setTakingSize = {setTakingSize}
                                    />
                                )
                            })}
                    </div>
                </div>
                <div className="main-selection-product__info__button-window" >
                    <button className="main-selection-product__info__button-window__button" onClick={() => handleClickBasket()}>Добавить в корзину</button>
                </div>
            </div>
        </div>
    )
}
