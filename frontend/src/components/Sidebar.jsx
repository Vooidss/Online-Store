import React, { useEffect, useState } from 'react';
import Checkbox from '../util/Checkbox';
import SortComponent from './SortComponent';
import DefualtSort from './DefualtSort';

export default function Sidebar({ products, setSort, defaultSort, setDefaultSort, handleClick}) {
    const [specifications, setSpecifications] = useState({
        colors: [],
        brands: [],
        materials: [],
        sizes: [],
        minPrice: 2000,
        maxPrice: 999999
    })

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
                    setSpecifications(
                        {
                            colors: data.colors,
                            brands: data.brands,
                            materials: data.materials,
                            sizes: data.sizes,
                            minPrice: data.minPrice,
                            maxPrice: data.maxPrice
                        }
                    )
                    console.error(specifications);
                } else {
                    console.error(data.message);
                }
            })
            .catch((error) => console.error(error));
    }

    useEffect(() => {
        findSpecificationsProducts();
    }, [products]);

    const handelInput = (key,value ) => {
        setSort((prevStore) => {
            const newSort= {...prevStore}

            console.log(value)

            if (!newSort[key]) {
                newSort[key] = [];
            }

            if(!(newSort[key].length > 0)){
                delete newSort[key]
            }

            newSort[key] = value

            return newSort
        })
    }

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
                <label className="Sidebar-main__sorting__outstanding-window__gap">
                    <input
                        className="Sidebar-main__sorting__outstanding-window__gap__input"
                        placeholder={`от ${specifications.minPrice}`}
                        onBlur ={(event) => handelInput("minPrice", event.target.value)}
                    />
                    <input
                        className="Sidebar-main__sorting__outstanding-window__gap__input"
                        placeholder={`до ${specifications.maxPrice}`}
                        onBlur ={(event) => handelInput("maxPrice", event.target.value)}
                    />
                </label>
                <div className="Sidebar-main__sorting__outstanding-window__chooses">
                    <Checkbox
                        name = "minPrice"
                        text={`от ${(((specifications.minPrice + specifications.maxPrice) / 2) + specifications.minPrice) / 2}`}
                        value={(((specifications.minPrice + specifications.maxPrice) / 2) + specifications.minPrice) / 2}
                        onChange={handleCheckboxChange} />
                    <Checkbox
                        name = "minPrice"
                        text={`от ${(specifications.minPrice + specifications.maxPrice) / 2}`}
                        value={(specifications.minPrice + specifications.maxPrice) / 2}
                        onChange={handleCheckboxChange} />
                    <Checkbox
                        name = "minPrice"
                        text={`от ${(((specifications.minPrice + specifications.maxPrice) / 2) + specifications.maxPrice) / 2}`}
                        value={(((specifications.minPrice + specifications.maxPrice) / 2) + specifications.maxPrice) / 2}
                        onChange={handleCheckboxChange} />
                </div>
            </SortComponent>
            <SortComponent name="Материал">
                <div className="Sidebar-main__sorting__outstanding-window__chooses">
                    {specifications.materials.map((material) => (
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
                {specifications.sizes ? specifications.sizes.map((size) => (
                    <Checkbox
                        key={size.name}
                        text={size.name}
                        count={size.count}
                        value={size.name}
                        onChange={handleCheckboxChange}
                        name = "size"
                    />
                )) : ''}
            </SortComponent>
            <SortComponent name="Бренд">
                {specifications.brands.map((brand) => (
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
                {specifications.colors.map((color) => (
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
                    Применить
                </button>
            </div>
        </div>
    );
}
