import React, { useEffect, useState } from 'react';
import Checkbox from '../util/Checkbox';
import CustomRadio from '../util/CustomRadio';
import SortComponent from './SortComponent';

export default function Sidebar({ products }) {
    const [colors, setColors] = useState([]);
    const [brands, setBrands] = useState([]);
    const [materials, setMaterials] = useState([]);
    const [sizes, setSizes] = useState([]);

    async function findSpecificationsProducts() {
        await fetch(`http://localhost:8071/products/${products}/specifications`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);

                if (data.code === 200) {
                    setColors(data.colors || []);
                    setBrands(data.brands || []);
                    setMaterials(data.materials || []);
                    setSizes(data.sizes || []);
                } else {
                    console.error(data.message);
                }
            })
            .catch((error) => console.error(error));
    }

    useEffect(() => {
        findSpecificationsProducts();
    }, [products]);

    return (
        <div className="Sidebar-main">
            <SortComponent name="Сортировать">
                <div className="Sidebar-main__sorting__outstanding-window__chooses">
                    <CustomRadio name="sort" content="Новинки" />
                    <CustomRadio name="sort" content="Сначала дорогие" />
                    <CustomRadio name="sort" content="Сначала дешёвые" />
                    <CustomRadio name="sort" content="Со скидкой" />
                </div>
            </SortComponent>
            <SortComponent name="Цена">
                <div className="Sidebar-main__sorting__outstanding-window__gap">
                    <input
                        className="Sidebar-main__sorting__outstanding-window__gap__input"
                        placeholder="от 2000"
                    />
                    <input
                        className="Sidebar-main__sorting__outstanding-window__gap__input"
                        placeholder="до 999.999"
                    />
                </div>
                <div className="Sidebar-main__sorting__outstanding-window__chooses">
                    <Checkbox text="от 2000" />
                    <Checkbox text="от 5000" />
                    <Checkbox text="от 10000" />
                    <Checkbox text="от 100000" />
                </div>
            </SortComponent>
            <SortComponent name="Материал">
                <div className="Sidebar-main__sorting__outstanding-window__chooses">
                    {
                        materials.map(material => {
                            return(
                                <Checkbox
                                    text = {material.name}
                                    count = {material.count}
                                />
                            )
                        })
                    }
                </div>
            </SortComponent>
            <SortComponent name="Размер">
                {
                    sizes.map(size => {
                        return(
                            <Checkbox
                                text = {size.name}
                                count = {size.count}
                            />
                        )
                    })
                }
            </SortComponent>
            <SortComponent name="Бренд">
                {
                    brands.map(brand => {
                        return(
                            <Checkbox
                                text = {brand.name}
                                count = {brand.count}
                            />
                        )
                    })
                }
            </SortComponent>
            <SortComponent name="Цвет">
                {
                    colors.map(color => {
                        return(
                            <Checkbox
                                text = {color.name}
                                count = {color.count}
                            />
                        )
                    })
                }
            </SortComponent>
        </div>
    );
}
