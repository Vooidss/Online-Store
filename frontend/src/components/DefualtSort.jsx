import CustomRadio from '../util/CustomRadio'
import React, { useEffect, useState } from 'react'

export default function DefualtSort({sort,setSort}){

    return (
        <div className="Sidebar-main__sorting__outstanding-window__chooses">
            <CustomRadio
                name="sort"
                content="Без сортировки"
                checked={sort === 'none'}
                setValue={() => setSort('none')}
            />
            <CustomRadio
                name="sort"
                content="Новинки"
                checked={sort === 'novelty'}
                setValue={() => setSort('novelty')}
            />
            <CustomRadio
                name="sort"
                content="Сначала дорогие"
                checked={sort === 'expensive'}
                setValue={() => setSort('expensive')}
            />
            <CustomRadio
                name="sort"
                content="Сначала дешёвые"
                checked={sort === 'cheap'}
                setValue={() => setSort('cheap')}
            />
            <CustomRadio
                name="sort"
                content="Со скидкой"
                checked={sort === 'discount'}
                setValue={() => setSort('discount')}
            />
        </div>
    )
}