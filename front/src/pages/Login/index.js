// src\pages\Cadastro\index.js

// import { LogIn } from 'lucide-react'    // ÍCONE
import FormularioLogin from '../../components/FormularioLogin' // Seu componente
import './styles.css'


function PaginaLogin() {
    return (
        <div className='pagina-login'>
            <FormularioLogin/>
        </div>
    )
}

export default PaginaLogin;