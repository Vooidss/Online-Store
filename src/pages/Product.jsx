import React from 'react';
import {useNavigate} from "react-router-dom";
import { BsBasket2Fill } from "react-icons/bs";


export default function Product(props) {

    const navigate = useNavigate();
    const type = "product"

    const handleClick = () => {
        navigate(`/${type}/${props.thisProduct.id}`);
    };

    return (
        <div className="section__item">
            <div className="section__item__components" onClick={() => handleClick()}>
                <div className="section__item__components__image">
                    <img className="section__item__components__image__self" src={props.thisProduct.img} alt={"sad"}/>
                </div>
                <div className="section__item__components__items">
                    <div className="section__item__components__items__component">
                        Название: {props.thisProduct.name}
                    </div>
                    <div className="section__item__components__items__component">
                        Описание: {props.thisProduct.description}
                    </div>
                    <div className="section__item__components__items__component">
                        Размер: {props.thisProduct.size}
                    </div>
                </div>
            </div>
            <div className="section__item__components__basket"><BsBasket2Fill/></div>
        </div>
    )
}