import React, { useEffect, useState } from 'react'
import { CSSTransition, TransitionGroup } from 'react-transition-group'
import ProductUser from './ProductUser'

export default function BasketList({products,handleDeleteProduct,updateProductCount,isAuthorization}){
    return (
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
    )
}