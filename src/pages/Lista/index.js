import ListaDeUsuarios from '../../components/ListaDeUsuarios'
import { useNavigate } from 'react-router-dom'
import './styles.css'

function PaginaListaUsuarios() {
    const navigate = useNavigate()

    return (
        <div className='pagina-lista-usuarios'>
            <div className='container'>
                <h2>Lista de usuários</h2>
                <ListaDeUsuarios />
                <button onClick={() => navigate('/cadastro')} className='link-voltar'>
                    Voltar
                </button>
            </div>
        </div>
    )
}

export default PaginaListaUsuarios
