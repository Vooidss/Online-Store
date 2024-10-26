import CustomRadio from '../util/CustomRadio'

export default function DefualtSort({sort,setSort}){
    return (
        <div className="Sidebar-main__sorting__outstanding-window__chooses">
            <CustomRadio
                name="sort"
                content="Без сортировки"
                setValue={setSort}
                value="none"
                checked={sort === "none"}
            />
            <CustomRadio
                name="sort"
                content="Новинки"
                setValue={setSort}
                value="novelty"
                checked={sort === "novelty"}
            />
            <CustomRadio
                name="sort"
                content="Сначала дорогие"
                setValue={setSort}
                value="expensive"
                checked={sort === "expensive"}
            />
            <CustomRadio
                name="sort"
                content="Сначала дешёвые"
                setValue={setSort}
                value="cheap"
                checked={sort === "cheap"}
            />
            <CustomRadio
                name="sort"
                content="Со скидкой"
                setValue={setSort}
                value="discount"
                checked={sort === "discount"}
            />
        </div>
    )
}