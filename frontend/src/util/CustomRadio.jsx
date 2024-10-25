import React from 'react'

export default function CustomRadio({name,checked,setValue,content,margin}){
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
                onChange={setValue}
            />
            <span className="custom-radio"></span>
            <span>{content}</span>
        </label>
    )
}