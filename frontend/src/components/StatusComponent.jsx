import React, {} from 'react'
import { FcCheckmark } from "react-icons/fc";
import { ImCross } from "react-icons/im";



export default function StatusComponent({status,isLoading,isLoaded,textAccepted,textRefused}){

    const activeLink = 'Status-main-window active'
    const defaultLink = 'Status-main-window'

    return(
        <div className={!isLoading && isLoaded ? activeLink : defaultLink}>
            <div className="Status-main-window__window" style={{
                border: status ? '2px green solid' : '2px red solid'
            }}>
                <p>{status ? textAccepted : textRefused}</p>
                {status ? <FcCheckmark style={{
                    color : 'green',
                    fontSize: '20px',
                    marginLeft: '5px'
                }}/> : <ImCross style={{
                    color : 'red',
                    fontSize: '20px',
                    marginLeft: '5px'
                }}/>}
            </div>
        </div>
    )
}