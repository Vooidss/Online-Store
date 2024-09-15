import React from "react";
import {BrowserRouter as Router,Routes, Route } from "react-router-dom";
import ProductRequest from "../Requests/ProductRequest";

class ShoppingList extends React.Component {
    render(){
        return(
                <div className = "main-window-shopping-list">
                        <div className = "nav">
                            БОКОВАЯ ПАНЕЛЬ
                        </div>
                        <div className = "section">
                            <Routes>
                                <Route path="/Sneakers" element={<ProductRequest  product={"sneakers"}/>}/>
                                <Route path="/Shorts" element={<ProductRequest  product={"shorts"}/>}/>
                                <Route path="/TShirts" element={<ProductRequest product={"tshirts"}/>}/>
                            </Routes>
                        </div>
                        <aside className="asside__main">
                            БОКОВАЯ ПАНЕЛЬ
                        </aside>
                </div>
        )
    }
}

export default ShoppingList;