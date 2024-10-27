import Sidebar from './Sidebar'
import ProductRequest from '../Requests/ProductRequest'
import { useEffect, useRef, useState } from 'react'
import SortedProduct from './SortedProduct'

export default function Products({products}){
    const [isSorted, setIsSorted] = useState(false);
    const [sort, setSort] = useState([]);
    const [defaultSort, setDefaultSort] = useState("none");
    const [isClick, setClick] = useState(false);

    const previousProductsRef = useRef(products);

    function handleClick(){
        setClick(true)
        setIsSorted(!(defaultSort==='none' && Object.values(sort).length === 0))
    }

    useEffect(() => {
        if (previousProductsRef.current !== products) {
            console.log('Products обновились!');
            window.location.reload();
        }
        previousProductsRef.current = products;
    }, [products]);


    return(
        <div className="main-window-products">
            <Sidebar
                products = {products}
                sort = {sort}
                defaultSort = {defaultSort}
                setDefaultSort = {setDefaultSort}
                setSort = {setSort}
                setIsSorted = {setIsSorted}
                handleClick = {handleClick}
            />
            <div className="section">
                {!isSorted
                ?
                    <ProductRequest products={products}/>
                :
                    <SortedProduct
                    typeProduct={products}
                    typeSort={sort}
                    isSorted = {isSorted}
                    setIsSorted = {setIsSorted}
                    defaultSort={defaultSort}
                    isClick={isClick}
                    setClick = {setClick}
                    />
                }
            </div>
        </div>
    )
}