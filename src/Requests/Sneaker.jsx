import React, {Component} from "react";
import Sneakers from "../pages/Sneakers";


const url = "http://localhost:8070/products/sneaker/v1";

export default class Sneaker extends Component {
    constructor(props) {
        super(props);

        this.state = {
            items : [],
            isLoading: false,
            isEmpty: false
        }
    }

    componentDidMount() {
        fetch(url)
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoading: true,
                        items: result.sneakers,
                        isEmpty: result.isEmpty
                    });
                });
    }

    render(){
        const {items, isLoading,isEmpty} = this.state;

        if(isEmpty){
            return(
                <h1>Товара пока нет</h1>
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
            items.map((sneaker) =>{
                return <Sneakers key={sneaker.id} sneaker={sneaker}/>;})
        )
    }
}