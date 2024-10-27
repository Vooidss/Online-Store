import React, { useEffect, useState } from 'react';
import Checkbox from '../util/Checkbox';
import CustomRadio from '../util/CustomRadio';
import SortComponent from './SortComponent';
import DefualtSort from './DefualtSort';

export default function Sidebar({ products, sort, setSort, defaultSort, setDefaultSort, handleClick}) {
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

    const handleCheckboxChange = (itemKey, value, isChecked) => {
        setSort((prevSort) => {

            const newSort = { ...prevSort };

            if (!newSort[itemKey]) {
                newSort[itemKey] = [];
            }

            if (isChecked) {
                if (!newSort[itemKey].includes(value)) {
                    newSort[itemKey].push(value);
                }
            } else {
                newSort[itemKey] = newSort[itemKey].filter((v) => v !== value);
                if (newSort[itemKey].length === 0) {
                    delete newSort[itemKey];
                }
            }

            return newSort;
        });
    };


    return (
        <div className="Sidebar-main">
            <SortComponent name="Сортировать">
                <DefualtSort
                    sort={defaultSort}
                    setSort={setDefaultSort}/>
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
                    <Checkbox text="от 2000" value="2000" onChange={handleCheckboxChange} />
                    <Checkbox text="от 5000" value="5000" onChange={handleCheckboxChange} />
                    <Checkbox text="от 10000" value="10000" onChange={handleCheckboxChange} />
                    <Checkbox text="от 100000" value="100000" onChange={handleCheckboxChange} />
                </div>
            </SortComponent>
            <SortComponent name="Материал">
                <div className="Sidebar-main__sorting__outstanding-window__chooses">
                    {materials.map((material) => (
                        <Checkbox
                            key={material.name}
                            text={material.name}
                            count={material.count}
                            value={material.name}
                            onChange={handleCheckboxChange}
                            name = "material"
                        />
                    ))}
                </div>
            </SortComponent>
            <SortComponent name="Размер">
                {sizes.map((size) => (
                    <Checkbox
                        key={size.name}
                        text={size.name}
                        count={size.count}
                        value={size.name}
                        onChange={handleCheckboxChange}
                        name = "size"
                    />
                ))}
            </SortComponent>
            <SortComponent name="Бренд">
                {brands.map((brand) => (
                    <Checkbox
                        key={brand.name}
                        text={brand.name}
                        count={brand.count}
                        value={brand.name}
                        onChange={handleCheckboxChange}
                        name = "brand"
                    />
                ))}
            </SortComponent>
            <SortComponent name="Цвет">
                {colors.map((color) => (
                    <Checkbox
                        key={color.name}
                        text={color.name}
                        count={color.count}
                        value={color.name}
                        onChange={handleCheckboxChange}
                        name = "color"
                    />
                ))}
            </SortComponent>
            <div className="button-sort">
                <button
                    onClick={handleClick}
                >
                    Принять
                </button>
            </div>
        </div>
    );
}
