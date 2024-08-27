import React from "react";
import Sneakers from "../Requests/Sneaker";
import {BrowserRouter as Router,Routes, Route } from "react-router-dom";
import TShirts from "../Requests/TShirt";
import Shorts from "../Requests/Short";

class ShoppingList extends React.Component {
    render(){
        return(
                <div className = "main-window-shopping-list">
                        <div className = "nav">
                            БОКОВАЯ ПАНЕЛЬ
                        </div>
                        <div className = "section">
                            <Routes>
                                <Route path="/Sneakers" element={<Sneakers/>}/>
                                <Route path="/Shorts" element={<Shorts/>}/>
                                <Route path="/TShirts" element={<TShirts/>}/>
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