import React, { useState } from "react";
import "./styles.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function PaginaLogin() {
    const navigate = useNavigate();
    
    // Estados para guardar os dados
    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");
    const [mensagem, setMensagem] = useState({ texto: "", tipo: "" }); // tipo: 'erro' ou 'sucesso'
    const [carregando, setCarregando] = useState(false);

    // URL da sua API no Render
    const API_URL = "https://deploy-levelup.onrender.com/auth/login";

    const validarEmail = (email) => {
        return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    };

    const handleLogin = async (e) => {
        e.preventDefault(); // Evita recarregar a página
        setMensagem({ texto: "", tipo: "" });

        // Validações locais
        if (!email.trim() || !senha.trim()) {
            setMensagem({ texto: "Preencha todos os campos!", tipo: "erro" });
            return;
        }

        if (!validarEmail(email)) {
            setMensagem({ texto: "Email inválido!", tipo: "erro" });
            return;
        }

        try {
            setCarregando(true);
            
            // 1. Chama a API
            const response = await axios.post(API_URL, {
                email: email,
                senha: senha
            });

            // 2. Se deu certo (200 OK), pega o token
            const token = response.data.token;

            // 3. Salva no LocalStorage
            localStorage.setItem("levelup_token", token);

            setMensagem({ texto: "Login realizado com sucesso!", tipo: "sucesso" });

            // 4. Redireciona
            setTimeout(() => {
                navigate("/tarefas");
            }, 1000);

        } catch (error) {
            console.error("Erro no login:", error);
            
            // Tratamento de Erros da API
            if (error.response) {
                // Erro 403 (Senha errada) ou 500
                const msgErro = error.response.data.message || "Erro ao fazer login.";
                setMensagem({ texto: msgErro, tipo: "erro" });
            } else {
                setMensagem({ texto: "Erro de conexão com o servidor.", tipo: "erro" });
            }
        } finally {
            setCarregando(false);
        }
    };

    return (
        <div className="page-login">
            <div className="login-box">
                <h3>Login</h3>
                
                <form onSubmit={handleLogin}>
                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        disabled={carregando}
                    />
                    
                    <input
                        type="password"
                        placeholder="Senha"
                        value={senha}
                        onChange={(e) => setSenha(e.target.value)}
                        disabled={carregando}
                    />

                    <button type="submit" id="btnLogin" disabled={carregando}>
                        {carregando ? "Entrando..." : "Entrar"}
                    </button>
                </form>

                {mensagem.texto && (
                    <p className="mensagem" style={{ color: mensagem.tipo === "erro" ? "red" : "green" }}>
                        {mensagem.texto}
                    </p>
                )}
            </div>
        </div>
    );
}

export default PaginaLogin;