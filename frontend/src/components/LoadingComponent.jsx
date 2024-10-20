import React, {} from 'react'

export default function LoadingComponent({isHidden}){

    const activeLink = 'Loading active'
    const defaultLink = 'Loading'

    return(
        <div className={isHidden ? activeLink : defaultLink}>
            <img src={require('../resources/GIF/output-onlinegiftools.gif')}
                    className="Loading__gif" alt="Load"/>
        </div>
    )
}
