import React, { useEffect, useState } from 'react';
import OrderInformation from '../components/OrderInformation';
import BasketList from '../components/BasketList'
import OrderWindow from '../components/OrderWindow'

export default function Basket({ isAuthorization }) {
    const [products, setProducts] = useState([]);
    const [orderInformation, setOrderInformation] = useState({
        count: 0,
        price: 0,
        discountPrice: 0
    });
    const [isOrder, setOrder] = useState(false);

    const [adress, setAdress] = useState({
        city: '',
        street: '',
        numberHouse : 0,
        numberApartment: 0,
        numberIntercom: 0
    });

    const [recipient,setRecipient] = useState({
        name: '',
        secondName: '',
        phone: ''
    })

    const token = localStorage.getItem('token');
    const url = `http://localhost:8050/basket`;

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

    function addOrder(){
        if(isOrder === false){
            setOrder(true);
        }else{
            setOrder(false);
        }
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
                    count: product.count
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
            <nav style={{
                width: products.length > 0 ? 'auto' : '0px'
            }}> </nav>
            {isOrder ?
                <OrderWindow
                    addOrder = {addOrder}
                    setAdress = {setAdress}
                    setRecipient = {setRecipient}
                />
                :
                <BasketList
                    products={products}
                    handleDeleteProduct={handleDeleteProduct}
                    isAuthorization={isAuthorization}
                    updateProductCount={updateProductCount}

                />
            }
            {products.length > 0 && (
                <OrderInformation
                    orderInformation={orderInformation}
                    addOrder = {addOrder}
                    isOrder = {isOrder}
                    adress={adress}
                    recipient={recipient}
                />
            )}
        </div>
    );
}
