import React, { useEffect, useState } from 'react'
import Product from './Product'

export default function SortedProduct({typeProduct, typeSort}){

    const [products, setProducts] = useState([]);

    async function sort(){
        await fetch(`http://localhost:8071/products/sort/${typeProduct}/default/${typeSort}`,{
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(response => response.json())
            .then(data => setProducts(data.products))
            .catch(error => console.error(error))
    }

    useEffect(() => {
        sort()
    }, [typeProduct, typeSort])

    return products.map(thisProduct => {
        return (
            <Product
                key={thisProduct.id}
                product={thisProduct}
                productName={products}
            />
        )
    })

}