import React from 'react'

export default function SortComponent({children,name}){
    return(
            <details className="Sidebar-main__sorting">
                <summary className="Sidebar-main__sorting__name">
                    {name}
                </summary>
                <div className="Sidebar-main__sorting__outstanding-window">
                        {children}
                </div>
            </details>
    )
}