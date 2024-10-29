import { useNavigate } from 'react-router-dom'
import { BsBasket2Fill } from 'react-icons/bs'

export default function Product({product, productName }) {
    const navigate = useNavigate()

    const handleClick = () => {
        navigate(`/${product.type}/${product.id}`)
    }

    const handleClickBasket = async () => {
        const token = localStorage.getItem('token')
        const productId = product.id

        const credentials = { productId, token }

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
                onClick={() => handleClick()}
            >
                <div className="section__item__components__image">
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
                        Размер: {product.size}
                    </div>
                    <div className="section__item__components__items__component">
                        Цена: {
                        product.priceWithDiscount ?
                            <p id="discount"> {product.priceWithDiscount}</p> :
                            product.price}
                        {product.priceWithDiscount
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
