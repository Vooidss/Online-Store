import React, { useState } from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import ProductRequest from '../Requests/ProductRequest'
import Sidebar from '../components/Sidebar'

export default function ShoppingList(){
        return (
            <div className="main-window-shopping-list">
                <Sidebar/>
                <div className="section">
                    <Routes>
                        <Route
                            path="/Sneakers"
                            element={<ProductRequest product={'sneakers'} />}
                        />
                        <Route
                            path="/Shorts"
                            element={<ProductRequest product={'shorts'} />}
                        />
                        <Route
                            path="/TShirts"
                            element={<ProductRequest product={'tshirts'} />}
                        />
                    </Routes>
                </div>
            </div>
        )
}
