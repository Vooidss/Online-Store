import React, {
    useEffect,
    useState
} from 'react';


export default function Basket() {

    const [Product,setProduct] = useState([]);

    const token = localStorage.getItem("token");
    const url = `http://localhost:8050/basket?token=${token}`;


    useEffect(() => {
        fetchData();
    }, [fetchData, url]);
    
    async function fetchData() {
        try {
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            const data = await response.json();

            if (data.code === 200) {
                console.log(data);
            } else {
                console.log(
                    "Status: " + data.status + "\n Code: " + data.code + "\n Message: " + data.message
                );
            }
        } catch (e) {
            console.log(e);
        }
    }

    return (
        <div className="main_window_basket">
            <div className="main__selection">
            </div>
        </div>
    )
}