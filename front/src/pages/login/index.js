// src\pages\Cadastro\index.js

import { LogIn } from 'lucide-react'    // ÍCONE
import Login from '../../components/Login' // Seu componente
import './styles.css'


function PaginaLogin() {
    return (
        <div className='pagina-login'>
            <LogIn/>
        </div>
    )
}

export default PaginaLogin;