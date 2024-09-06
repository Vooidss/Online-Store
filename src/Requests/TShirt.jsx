import React, {Component} from "react";
import TShirts from "../pages/TShirts";

const url = "http://localhost:8070/tshirt/v1";

export default class TShirt extends Component {
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
                        items: result.tshirts,
                        isEmpty : result.isEmpty
                    });
                });
    }


    render() {
        const {items, isLoading, isEmpty} = this.state;

        console.log(items);

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
            items.map((thshirt) => {
                return <TShirts key={thshirt.id} tshirt={thshirt}/>;})
        )
    }
}