import React, {Component} from "react";
import Shorts from "../pages/Shorts";

const url = "http://localhost:8070/short/v1";

export default class Short extends Component {
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
                        items: result.shorts,
                        isEmpty: result.isEmpty
                    });
                });
    }

    render(){
        const {items, isLoading,isEmpty} = this.state;
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
        console.log(items);

        return (
            items.map((short) =>{
                return <Shorts key={short.id} short={short}/>})
        )
    }

}