import React, { useState, useEffect } from 'react'
import Product from '../components/Product'
import Pagination from '../components/Pagination'
import AllProducts from '../components/AllProducts'

const ProductRequest = ({ products }) => {
    const [items, setItems] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(false);
    const [currentProduct, setCurrentProduct] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const productsPerPages = 12;

    useEffect(() => {
        const lastProductIndex = currentPage * productsPerPages;
        const firstProductIndex = lastProductIndex - productsPerPages;

        const fetchData = async () => {
            try {
                const response = await fetch(`http://localhost:8071/products/${products}`);
                const result = await response.json();
                setItems(result.products);
                setCurrentProduct(result.products.slice(firstProductIndex, lastProductIndex));
                setIsLoading(true);
            } catch (error) {
                setIsLoading(true);
                setError(true);
            }
        };

        fetchData();
    }, [products, currentPage]);

    if (error) {
        return <h1 id="empty">Ошибка</h1>;
    }

    if (!isLoading) {
        return <h1 id="empty">Loading...</h1>;
    }

    if (items.length === 0) {
        return <h1 id="empty">Пусто</h1>;
    }

    return (
        <div className="section-pagination">
            <AllProducts products={currentProduct} />
            <Pagination
                productsPerPages={productsPerPages}
                totalProducts={items.length}
                setCurrentPage={setCurrentPage}
                currentPage = {currentPage}
            />
        </div>
    );
};

export default ProductRequest;
