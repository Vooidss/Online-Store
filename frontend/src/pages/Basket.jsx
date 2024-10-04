import React, { useEffect, useState } from 'react';
import ProductUser from '../components/ProductUser';
import OrderInformation from '../components/OrderInformation';
import { CSSTransition, TransitionGroup } from 'react-transition-group';

export default function Basket({ isAuthorization }) {
    const [products, setProducts] = useState([]);
    const [orderInformation, setOrderInformation] = useState({
        count: 0,
        price: 0,
        discountPrice: 0
    });

    const token = localStorage.getItem('token');
    const url = `http://localhost:8050/basket`;

    function calculateOrderInfo(products) {
        const totalOrder = products.reduce(
            (acc, product) => {
                acc.count += product.count;
                acc.price += product.count * product.price;
                return acc;
            },
            { count: 0, price: 0 }
        );

        setOrderInformation({
            count: totalOrder.count,
            price: totalOrder.price,
            discountPrice: 0
        });
    }

    function updateProductCount(productId, newCount) {
        setProducts((prevProducts) => {
            const updatedProducts = prevProducts.map((product) => {
                if (product.id === productId) {
                    return { ...product, count: newCount };
                }
                return product;
            });
            calculateOrderInfo(updatedProducts);
            return updatedProducts;
        });
    }

    async function fetchData() {
        try {
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            const data = await response.json();

            if (data.code === 200) {
                const productsWithCount = data.products.map((product) => ({
                    ...product,
                    count: 1
                }));
                setProducts(productsWithCount);
                calculateOrderInfo(productsWithCount);
            } else {
                console.log(`Status: ${data.status}\nCode: ${data.code}\nMessage: ${data.message}`);
            }
        } catch (e) {
            console.log(e);
        }
    }

    useEffect(() => {
        fetchData();
    }, []);

    const handleDeleteProduct = (id) => {
        setProducts((prevProducts) =>
            prevProducts.filter((product) => product.id !== id)
        );
        calculateOrderInfo(products.filter((product) => product.id !== id));
    };

    return (
        <div className="main_window_basket" style={{
            gridTemplateColumns: products.length > 0 ? '10% 60% 30%' : '100%'
        }}>
            <nav></nav>
            <div className="main_window_basket__basket" style={{
                width: products.length > 0 ? '900px' : 'auto'
            }}>
                <div className="main_window_basket__basket__head">
                    <h1 className="main_window_basket__basket__head__name">Корзина</h1>
                </div>
                <div className="main_window_basket__basket__items">
                    {isAuthorization ? (
                        products.length > 0 ? (
                            <TransitionGroup>
                                {products.map((product) => (
                                    <CSSTransition
                                        key={product.id}
                                        timeout={1000}
                                        classNames="fade"
                                        exit={true}
                                    >
                                        <ProductUser
                                            product={product}
                                            onDelete={handleDeleteProduct}
                                            updateProductCount={updateProductCount}
                                        />
                                    </CSSTransition>
                                ))}
                            </TransitionGroup>
                        ) : (
                            <p className="_message_">Корзина пуста. Пожалуйста выберите товар.</p>
                        )
                    ) : (
                        <p className="_message_">Пожалуйста войдите в учетную запись.</p>
                    )}
                </div>
            </div>
            {products.length > 0 && (
                <OrderInformation orderInformation={orderInformation} />
            )}
        </div>
    );
}
