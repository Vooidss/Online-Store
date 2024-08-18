import React from 'react';

export default function TShirts(props) {
        return (
            <div className="section__item">
                <div className="section__item__components">
                    <div className="section__item__components__image">
                        <img className="section__item__components__image__self" src={props.tshirt.img}
                             alt={"sad"}/>
                    </div>
                    <div className="section__item__components__items">
                        <div className="section__item__components__items__component">
                            Название: {props.tshirt.name}
                        </div>
                        <div className="section__item__components__items__component">
                            Описание: {props.tshirt.description}
                        </div>
                        <div className="section__item__components__items__component">
                            Размер: {props.tshirt.size}
                        </div>
                    </div>
                </div>
            </div>
        )
}