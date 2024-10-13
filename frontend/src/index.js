import * as ReactDOMClient from 'react-dom/client'
import Main from './pages/Main'
import './css/main.css'

const element = document.getElementById('main')
const app = ReactDOMClient.createRoot(element)

app.render(<Main />)
