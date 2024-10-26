import Sidebar from './Sidebar'
import ProductRequest from '../Requests/ProductRequest'
import { useEffect, useState } from 'react'
import SortedProduct from './SortedProduct'

export default function Products({products}){
    const [isSorted, setIsSorted] = useState(true);
    const [sort, setSort] = useState('');
    const [defaultSort, setDefaultSort] = useState("none");

    useEffect(() => {
        setIsSorted(defaultSort === "none");
        console.log(defaultSort)
    }, [defaultSort])

    return(
        <div className="main-window-products">
            <Sidebar
                products = {products}
                sort = {sort}
                defaultSort = {defaultSort}
                setDefaultSort = {setDefaultSort}
                setSort = {setSort}
            />
            <div className="section">
                {isSorted
                ?
                    <ProductRequest products={products}/>
                :
                    <SortedProduct
                    typeProduct={products}
                    typeSort={sort}
                    />
                }
            </div>
        </div>
    )
}