import Sidebar from './Sidebar'
import ProductRequest from '../Requests/ProductRequest'

export default function Products({products}){
    return(
        <div className="main-window-products">
            <Sidebar products = {products}/>
            <div className="section">
                <ProductRequest products={products}/>
            </div>
        </div>
    )
}