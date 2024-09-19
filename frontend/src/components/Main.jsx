import React from "react";
import Header from "./Header";
import ShoppingList from "./ShoppingList";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ScrollToTop from "../util/ScrollToTop";
import ProductSelection from "../pages/ProductSelection";
import BasketPage from "../pages/Basket";
import ProfilePage from "../pages/Profile";

class Main extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isModalOpen: false,
        };
    }

    toggleModal = (isActive) => {
        this.setState({ isModalOpen: isActive });
    }

    render(){
        return(
            <div className="App" style={{ pointerEvents: this.state.isModalOpen ? 'none' : 'auto' }}>
                <Router>
                    <ScrollToTop />
                    <Header
                        modalActive={this.state.isModalOpen}
                        setModalActive={this.toggleModal} />
                    <Routes>
                        <Route path="/ShopList/*" element={<ShoppingList/>} />
                        <Route path="/:products/:id" element={<ProductSelection />} />
                        <Route path="/Basket" element={<BasketPage />} />
                        <Route path="/Profile" element={<ProfilePage />} />
                    </Routes>
                </Router>
            </div>
        )
    }
}

export default Main;