import React from 'react'

export default function Checkbox({text, count}){
    return (
        <label className="checkbox">
            <input className="checkbox-org" type="checkbox" />
            <span className="checkbox-custom"></span>
            <span className="checkbox-content"> {text} {count ? <p>({count})</p> : '' } </span>
        </label>
    )
}