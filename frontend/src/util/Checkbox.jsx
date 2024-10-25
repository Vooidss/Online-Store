import React from 'react'

export default function Checkbox({text}){
    return (
        <label className="checkbox">
            <input className="checkbox-org" type="checkbox" />
            <span className="checkbox-custom"></span>
            <span className="checkbox-content"> {text} </span>
        </label>
    )
}