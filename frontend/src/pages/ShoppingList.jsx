import React, { useEffect, useState } from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Products from '../components/Products'

export default function ShoppingList(){

        return (
            <div className="main-window-shopping-list">
                    <Routes>
                        <Route
                            path="/Sneakers"
                            element={<Products products={'sneakers'}/>}
                        />
                        <Route
                            path="/Shorts"
                            element={<Products products={'shorts'} />}
                        />
                        <Route
                            path="/TShirts"
                            element={<Products products={'tshirts'} />}
                        />
                    </Routes>
            </div>
        )
}
