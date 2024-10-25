import React, { useEffect, useState } from 'react'
import Checkbox from '../util/Checkbox'
import CustomRadio from '../util/CustomRadio'
import SortComponent from './SortComponent'

export default function Sidebar({products}){
    const [colors, setColors] = useState();
    const [brands, setBrands] = useState();
    const [materials, setMaterials] = useState();
    const [sizes, setSizes] = useState();

    async function findSpecificationsProducts(){
        await fetch(`http://localhost:8071/products/${products}/specifications`,{
            method: 'GET',
            headers: {
                'Content-Type' : 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {

                console.log(data);

                if(data.code === 200) {
                    setColors(data.colors);
                    setBrands(data.brands);
                    setMaterials(data.materials)
                    setSizes(data.sizes)
                }else{
                    console.error(data.message)
                }

            })
            .catch(error => console.error(error))
    }

    useEffect(() => {
        findSpecificationsProducts()
    }, [])

    return(
        <div className="Sidebar-main">
            <SortComponent name = "Сортировать">
                <div className="Sidebar-main__sorting__outstanding-window__chooses">
                    <CustomRadio
                        name="sort"
                        content="Новинки"
                    />
                    <CustomRadio
                        name="sort"
                        content="Сначало дорогие"
                    />
                    <CustomRadio
                        name="sort"
                        content="Сначало дешёвые"
                    />
                    <CustomRadio
                        name="sort"
                        content="Со скидкой"
                    />
                </div>
            </SortComponent>
            <SortComponent name = "Цена">
                <div className="Sidebar-main__sorting__outstanding-window__gap">
                    <input className="Sidebar-main__sorting__outstanding-window__gap__input"
                           placeholder="от 2000" />
                    <input className="Sidebar-main__sorting__outstanding-window__gap__input"
                           placeholder="до 999.999" />
                </div>
                <div className="Sidebar-main__sorting__outstanding-window__chooses">
                    <Checkbox text="от 2000" />
                    <Checkbox text="от 5000" />
                    <Checkbox text="от 10000" />
                    <Checkbox text="от 100000" />
                </div>
            </SortComponent>
            <SortComponent name = "Материал"></SortComponent>
            <SortComponent name = "Размер"></SortComponent>
            <SortComponent name = "Бренд"></SortComponent>
        </div>
    )
}