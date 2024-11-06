import React from 'react'
export default function Pagination({productsPerPages, totalProducts, setCurrentPage, currentPage}){
    const pageNumbers = [];

    const activeLink = "pagination__numbers__page-item active";
    const defaultLink = "pagination__numbers__page-item";


    for(let i = 1; i <=  Math.ceil(totalProducts / productsPerPages); i++){
        pageNumbers.push(i);
    }

    return(
        <div className="pagination">
            <ul className="pagination__numbers">
                {
                    pageNumbers.map(number => (
                        <li className= {currentPage === number ? activeLink : defaultLink} key = {number} onClick={() => setCurrentPage(number)}>
                                {number}
                        </li>
                    ))
                }
            </ul>
        </div>
    )
}