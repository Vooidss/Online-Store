import Product from './Product'
export default function AllProducts({products}){
    return(
        <div className="section">
            {products.map(thisProduct => (
                <Product
                    key={thisProduct.id}
                    product={thisProduct}
                />
            ))}
        </div>
    )
}

