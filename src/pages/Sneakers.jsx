import React from 'react';
import {useNavigate} from "react-router-dom";

export default function Sneakers(props) {
    const navigate = useNavigate();
    const type = "sneaker"

    const handleClick = () => {
        navigate(`/${type}/${props.sneaker.id}`);
    };


        return (
                <div className="section__item">
                    <div className="section__item__components" onClick={() => handleClick()}>
                        <div className="section__item__components__image">
                            <img className="section__item__components__image__self"  src={props.sneaker.img} alt={"sad"}/>
                        </div>
                        <div className="section__item__components__items">
                            <div className="section__item__components__items__component">
                                Название: {props.sneaker.name}
                            </div>
                            <div className="section__item__components__items__component">
                                Описание: {props.sneaker.description}
                            </div>
                            <div className="section__item__components__items__component">
                                Размер: {props.sneaker.size}
                            </div>
                        </div>
                    </div>
                </div>
        )
}