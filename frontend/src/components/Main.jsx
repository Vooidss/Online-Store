import React, {useEffect, useState} from "react";
import Header from "./Header";
import ShoppingList from "./ShoppingList";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ScrollToTop from "../util/ScrollToTop";
import ProductSelection from "../pages/ProductSelection";
import BasketPage from "../pages/Basket";
import ProfilePage from "../pages/Profile";

export default  function Main(){

    const[isModelOpen,setModelOpen] = useState(false);

    const token = localStorage.getItem("token");

    const toggleModal = (isActive) => {
        setModelOpen(isActive);
    }

        return(
            <div className="App" style={{ pointerEvents: isModelOpen ? 'none' : 'auto' }}>
                <Router>
                    <ScrollToTop />
                    <Header
                        modalActive={isModelOpen}
                        setModalActive={toggleModal} />
                    <Routes>
                        <Route path="/ShopList/*" element={<ShoppingList/>} />
                        <Route path="/:products/:id" element={<ProductSelection />} />
                        <Route path="/Basket" element={<BasketPage />} />
                        <Route path="/Profile" element={<ProfilePage />} />
                    </Routes>
                </Router>
            </div>)
}
