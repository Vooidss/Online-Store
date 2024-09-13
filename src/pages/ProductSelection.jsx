import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";

export default function ProductSelection(){
    const {products,id} = useParams();
    const [info, setProducts] = useState([]);

    console.log(products);
    console.log(id);

    useEffect(() => {
        fetch(`http://localhost:8070/products/${products}/v1/${id}`)
            .then(res => res.json())
            .then((response) => setProducts(response.products))
    }, [id, products]);


    return(
        <div className="main">
            <div className="main__image">
                <img className="main_image__image" src={info.img} alt="img"/>
            </div>
            <div className="main__info">
                <h1 className="main__info__name">{info.name}</h1>
                <h2 className="main__info__description">{info.description}</h2>
            </div>
        </div>
    )
}