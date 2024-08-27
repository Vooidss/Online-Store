import React from "react";
import Header from "./Header";
import ShoppingList from "./ShoppingList";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ScrollToTop from "../util/ScrollToTop";
import ProductSelection from "../pages/ProductSelection";

class Main extends React.Component {
    render(){
        return(
            <div className="App">
                <Router>
                    <ScrollToTop />
                    <Header/>
                    <Routes>
                        <Route path="/ShopList/*" element={<ShoppingList/>} />
                        <Route path="/:products/:id" element={<ProductSelection />} />
                    </Routes>
                </Router>
            </div>
        )
    }
}

export default Main;