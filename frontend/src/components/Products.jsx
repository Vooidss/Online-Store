import Sidebar from './Sidebar'
import ProductRequest from '../Requests/ProductRequest'
import { useEffect, useState } from 'react'
import SortedProduct from './SortedProduct'

export default function Products({products}){
    const [isSorted, setIsSorted] = useState(true);
    const [sort, setSort] = useState('none');

    useEffect(() => {
        if(sort === 'none'){
            setIsSorted(true);
        }else{
            setIsSorted(false);
        }
    }, [sort])



    return(
        <div className="main-window-products">
            <Sidebar
                products = {products}
                sort = {sort}
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