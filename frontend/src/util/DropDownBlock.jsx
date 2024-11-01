export default function DropDownBlock({value}){
    return (
        <option key={value} value={value}>
            {value}
        </option>
    )
}