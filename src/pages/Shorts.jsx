import React from 'react';
import {useNavigate} from "react-router-dom";

export default function Shorts(props) {
    const navigate = useNavigate();
    const type = "short"

    const handleClick = () => {
        navigate(`/${type}/${props.short.id}`);
    };

        return (
            <div className="section__item">
                <div className="section__item__components" onClick={() => handleClick()}>
                    <div className="section__item__components__image">
                        <img className="section__item__components__image__self" src={props.short.img}
                             alt={"sad"}/>
                    </div>
                    <div className="section__item__components__items">
                        <div className="section__item__components__items__component">
                            Название: {props.short.name}
                        </div>
                        <div className="section__item__components__items__component">
                            Описание: {props.short.description}
                        </div>
                        <div className="section__item__components__items__component">
                            Размер: {props.short.size}
                        </div>
                    </div>
                </div>
            </div>
        )
}