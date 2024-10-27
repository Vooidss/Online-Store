import React, { useEffect, useState } from 'react';
import Product from './Product';

export default function SortedProduct({ typeProduct, typeSort, defaultSort, isClick, setClick}) {
    const [products, setProducts] = useState([]);

    async function sort() {
        const params = new URLSearchParams();

        for (const [key, value] of Object.entries(typeSort)) {
            params.append(key, value);
        }

        console.log(params);
        console.log(params.toString());

        await fetch(`http://localhost:8071/products/sort/${typeProduct}?defaultSort=${defaultSort}${params.toString() ? `&${params.toString()}` : '' }`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(response => response.json())
            .then(data => {
                if (data.products && Array.isArray(data.products)) {
                    setProducts(data.products);
                    setClick(false);
                } else {
                    console.error('No products found or data is not in the expected format');
                    setProducts([]);
                }
            })
            .catch(error => console.error(error));
    }

    useEffect(() => {
        if (isClick === true){
            sort()
        }
    }, [isClick]);

    return products.length > 0 ? products.map(thisProduct => {
        return (
            <Product
                key={thisProduct.id}
                product={thisProduct}
                productName={thisProduct.name}
            />
        );
    }) : <h1 id = "empty">Пусто </h1>;
}
