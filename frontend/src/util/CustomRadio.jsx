import React from 'react'

export default function CustomRadio({name,checked,setValue,content,margin,value}){
    return (
        <label
            className="radio"
            style={{
                margin: margin
            }}
        >
            <input
                className="real-radio"
                type="radio"
                name={name}
                checked={checked}
                onChange={() => setValue(value)}
            />
            <span className="custom-radio"></span>
            <span>{content}</span>
        </label>
    )
}