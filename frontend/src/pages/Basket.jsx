import React, { useEffect, useState } from 'react'
import ProductUser from '../components/ProductUser'

export default function Basket() {
    const [products, setProduct] = useState([])

    const token = localStorage.getItem('token')
    const url = `http://localhost:8050/basket`

    async function fetchData() {
        try {
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
            })

            const data = await response.json()

            if (data.code === 200) {
                setProduct(product => data.products)
                console.log(
                    'Status: ' +
                        data.status +
                        '\n Code: ' +
                        data.code +
                        '\n Message: ' +
                        data.message,
                )
            } else {
                console.log(
                    'Status: ' +
                        data.status +
                        '\n Code: ' +
                        data.code +
                        '\n Message: ' +
                        data.message,
                )
            }
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        fetchData()
    }, [])

    return (
        <div className="main_window_basket">
            <nav></nav>
            <div className="main_window_basket__basket">
                <div className="main_window_basket__basket__head">
                    <h1 className="main_window_basket__basket__head__name">
                        Корзина
                    </h1>
                </div>
                <div className="main_window_basket__basket__items">
                    {products.length > 0 ? (
                        products.map(product => (
                            <ProductUser key={product.id} product={product} />
                        ))
                    ) : (
                        <p>No products in the basket.</p>
                    )}
                </div>
            </div>
            <nav></nav>
        </div>
    )
}
