import { useEffect } from 'react'

export default function SizeBlock({size,sizeProduct,setTakingSize}) {
    const activeLink = "main-selection-product__info__sizes__components__size active";
    const defaultLink = "main-selection-product__info__sizes__components__size";
    return(
        <div className={size === sizeProduct ? activeLink : defaultLink} onClick={() => setTakingSize(size)}>
            <p>{size}</p>
        </div>
    )
}