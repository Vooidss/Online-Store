import React, {
    useEffect,
    useState
} from 'react';


export default function Basket() {

    const [Product,setProduct] = useState({
        brand: '',
        type: '',
        size: '',
        img: '',
        model: '',
        description: ''
    });

    const token = localStorage.getItem("token");
    const url = `http://localhost:8050/basket?token=${token}`;

    useEffect(() =>  {
        try{
            const response = fetch(url, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    }
                });
        }catch (e){
            console.log(e);
        }
    }, []);

    return (
        <div className="main_window_basket">
            <div className="main__selection">
            </div>
        </div>
    )
}