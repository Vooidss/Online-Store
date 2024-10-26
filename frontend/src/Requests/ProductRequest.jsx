import React, { Component } from 'react'
import Product from '../components/Product'

export default class ProductRequest extends Component {
    constructor(props) {
        super(props)

        this.state = {
            items: [],
            isLoading: false,
            error: false,
        }
    }

    componentDidMount() {
        this.fetchData()
    }

    componentDidUpdate(prevProps) {
        if (prevProps.products !== this.props.products) {
            this.fetchData()
        }
    }

    fetchData() {
        const productName = this.props.products

        let url = `http://localhost:8071/products/${productName}`
        fetch(url)
            .then(res => res.json())
            .then(
                result => {
                    this.setState({
                        isLoading: true,
                        items: result.products,
                    })
                },
                error => {
                    this.setState({
                        isLoading: true,
                        error: true,
                    })
                },
            )
    }

    render() {
        const { items, isLoading, error } = this.state

        console.log(items)

        if (error) {
            return <h1 id="empty">Ошибка</h1>
        }

        if (!isLoading) {
            return <h1 id="empty">Loading...</h1>
        }

        if (JSON.stringify(items) === '[]') {
            return <h1 id="empty">Пусто</h1>
        }

        return items.map(thisProduct => {
            return (
                <Product
                    key={thisProduct.id}
                    product={thisProduct}
                    productName={this.props.products}
                />
            )
        })
    }
}
