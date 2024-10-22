import React, { useEffect, useState } from 'react'

export default function OrderExecutionComponent({text,value,setValue,isDigit,color,width}) {

    const [inputValue, setInputValue] = useState(value || '');
    const [isVision, setIsVision] = useState(false);

    useEffect(() => {
        setValue(inputValue);
        setIsVision(inputValue !== '');
    }, [inputValue])
    
    const handleChange = (event) => {
        let value = event.target.value;

        if(isDigit) {
            value = value.replace(/\D/g, '');
        }

        setInputValue(value);
        setIsVision(value !== '');
    };

    return (
        <div className="component">
           <p style={{
               transform: isVision ? 'scale(1)' : 'scale(0)'
           }}>{text}</p>
            <input
                required
                type="text"
                className="component__input"
                placeholder={`${text}`}
                onChange={handleChange}
                value={value ? value : setValue ? inputValue : ''}
                style={{
                    maxWidth: width ? width : ''
                }}
            />
        </div>
    )

}