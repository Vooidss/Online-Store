import React, {Component} from "react";
import Product from "../pages/Product";

export default class ProductRequest extends Component {
    constructor(props) {
        super(props);

        this.state = {
            items : [],
            isLoading: false,
            error: false
        }
    }

    componentDidMount() {
        this.fetchData();
    }

    componentDidUpdate(prevProps) {
        if (prevProps.product !== this.props.product) {
            this.fetchData();
        }
    }

    fetchData() {
        const productName = this.props.product;

        let url = `http://localhost:8091/products/${productName}/v1`;
        fetch(url)
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoading: true,
                        items: result[productName]
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


    render() {
        const {items, isLoading, error} = this.state;
        
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
                return <Product key={thisProduct.id} thisProduct={thisProduct} productName={this.props.product}/>})
        )
    }
}