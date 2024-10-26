import React from 'react';

export default function Checkbox({ text, count, onChange, value,name }) {
    const handleChange = (event) => {
        onChange(name,value, event.target.checked);
    };

    return (
        <label className="checkbox">
            <input
                className="checkbox-org"
                type="checkbox"
                onChange={handleChange}
            />
            <span className="checkbox-custom"></span>
            <span className="checkbox-content">
                {text} {count ? <p>({count})</p> : ''}
            </span>
        </label>
    );
}
