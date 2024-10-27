import Sidebar from './Sidebar'
import ProductRequest from '../Requests/ProductRequest'
import { useEffect, useState } from 'react'
import SortedProduct from './SortedProduct'

export default function Products({products}){
    const [isSorted, setIsSorted] = useState(false);
    const [sort, setSort] = useState([]);
    const [defaultSort, setDefaultSort] = useState("none");
    const [isClick, setClick] = useState(false);

    function handleClick(){
        setClick(true)
        setIsSorted(!(defaultSort==='none' && Object.values(sort).length === 0))
    }

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