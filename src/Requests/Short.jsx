import React, {Component} from "react";
import Product from "../pages/Product";

const url = "http://localhost:8091/products/short/v1";

export default class Short extends Component {
    constructor(props) {
        super(props);

        this.state = {
            items : [],
            isLoading: false,
            error: false
        }
    }

    componentDidMount() {
        fetch(url)
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoading: true,
                        items: result.shorts
                    });
                },
                (error) => {
                    this.setState({
                        isLoading: true,
                        error: true
                    });
                }
            );
    }

    render(){
        const {items, isLoading,error} = this.state;
        console.log(items);

        if(error){
            return(
                <h1>Ошибка</h1>
            )
        }

        if(!isLoading){
            return(
                <h1>Loading...</h1>
            )
        }

        if(JSON.stringify(items) === '[]'){
            return(
                <h1>Пусто</h1>
            )
        }

        return (
            items.map((thisProduct) =>{
                return <Product key={thisProduct.id} thisProduct={thisProduct}/>})
        )
    }

}