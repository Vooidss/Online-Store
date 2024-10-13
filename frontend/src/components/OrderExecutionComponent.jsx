import React, { useEffect, useState } from 'react'

export default function OrderExecutionComponent({text,setValue}) {

    const [inputValue, setInputValue] = useState('');
    const [isVision, setIsVision] = useState(false);

    useEffect(() => {
        setValue(inputValue);
    }, [inputValue])
    
    const handleChange = (event) => {
        setInputValue(event.target.value);
        setIsVision(event.target.value !== '');
    };

    return (
        <div className="main_window_basket__order__execution__components__component">
           <p style={{
               transform: isVision ? 'scale(1)' : 'scale(0)'
           }}>{text}</p>
            <input
                type="text"
                className="main_window_basket__order__execution__components__input"
                placeholder={`${text}`}
                onChange={handleChange}
                value={inputValue}
            />
        </div>
    )

}