// src\components\ListaDeUsuarios\index.js

import { useState, useEffect } from "react";
import axios from "axios";
import './styles.css'

function ListaDeUsuarios() {
    const [usuarios, setUsuarios] = useState([])
    useEffect(() => {
        const carregarUsuarios = async () => {
            try {
                const response = await axios.get('http://localhost:8080/usuarios')
                setUsuarios(response.data)
            } catch (error) {
                alert('Erro ao buscar usuários: ', error)
                setUsuarios([])
            }
        }
        carregarUsuarios()
    }, [])
    function PaginaListaUsuarios() {
        return (
          <div className="pagina-lista"></div>
        )
          }
          const deletarUsuario = async (id) => {
            try {
                await axios.delete(`http://localhost:8080/usuarios/${id}`);
                setUsuarios(usuarios.filter(usuario => usuario.id !== id)); // Atualiza a lista localmente
            } catch (error) {
                alert('Erro ao deletar usuário: ' + error);
            }
        };

    return (
        <div className="card-usuarios">
            <video autoPlay loop muted className="video-fundo-lista">
                <source src="/videos/video-fundo.mp4" type="video/mp4" />
            </video>
                <ul className="lssta-usuarios">
                    {usuarios.map(usuario => (
                        <li key={usuario.id}>
                            <strong>Nome:</strong> {usuario.nome}<br />
                            <strong>Email:</strong> {usuario.email}<br />
                            <strong>Telefone:</strong> {usuario.telefone}<br />
                            <button className="botao-deletar" onClick={() => deletarUsuario(usuario.id)}>Deletar</button>
                        </li>
                    ))}
                </ul>
            </div>
    )
    
}

export default ListaDeUsuarios