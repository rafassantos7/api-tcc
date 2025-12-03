import React, { useState } from "react";
import "./styles.css";
import { useNavigate } from "react-router-dom";
import api from '../../api';

function PaginaLogin() {
    const navigate = useNavigate();
    
    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");
    const [mensagem, setMensagem] = useState({ texto: "", tipo: "" });
    const [carregando, setCarregando] = useState(false);

    const emailValido = (valor) => {
        return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(valor);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMensagem({ texto: "", tipo: "" });

        const vEmail = email.trim();
        const vSenha = senha.trim();

        if (!vEmail) {
            setMensagem({ texto: "Preencha o email!", tipo: "erro" });
            return;
        }

        if (!emailValido(vEmail)) {
            setMensagem({ texto: "Digite um email válido!", tipo: "erro" });
            return;
        }

        if (!vSenha) {
            setMensagem({ texto: "Preencha a senha!", tipo: "erro" });
            return;
        }

        try {
            setCarregando(true);
            
            const response = await api.post("/auth/login", {
                email: vEmail,
                senha: vSenha
            });

            const token = response.data.token;
            localStorage.setItem("token", token);

            setMensagem({ texto: "Login realizado com sucesso!", tipo: "sucesso" });
            
            setTimeout(() => {
                navigate("/tarefas");
            }, 600);

        } catch (error) {
            console.error("Erro no login:", error);
            
            if (error.response) {
                const msgErro = error.response.data.message || "Email ou senha incorretos!";
                setMensagem({ texto: msgErro, tipo: "erro" });
            } else {
                setMensagem({ texto: "Erro de conexão com o servidor.", tipo: "erro" });
            }
        } finally {
            setCarregando(false);
        }
    };

    const handleEmailKeyDown = (e) => {
        if (e.key === "Enter") {
            const vEmail = email.trim();
            
            if (!vEmail) {
                setMensagem({ texto: "Preencha o email!", tipo: "erro" });
                return;
            }

            if (!emailValido(vEmail)) {
                setMensagem({ texto: "Digite um email válido!", tipo: "erro" });
                return;
            }

            setMensagem({ texto: "", tipo: "" });
            document.getElementById("senha").focus();
        }
    };

    const handleSenhaKeyDown = (e) => {
        if (e.key === "Enter") {
            handleSubmit(e);
        }
    };

    return (
        <div className="page-login">
            {/* Caixa de login */}
            <div className="login-box">
                <h3>Login</h3>
                
                <form onSubmit={handleSubmit}>
                    <input
                        type="email"
                        id="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        onKeyDown={handleEmailKeyDown}
                        disabled={carregando}
                        className="login-input"
                        autoFocus
                    />
                    
                    <input
                        type="password"
                        id="senha"
                        placeholder="Senha"
                        value={senha}
                        onChange={(e) => setSenha(e.target.value)}
                        onKeyDown={handleSenhaKeyDown}
                        disabled={carregando}
                        className="login-input"
                    />

                    <button 
                        type="submit" 
                        id="btnLogin"
                        disabled={carregando}
                        className="login-button"
                    >
                        {carregando ? "Entrando..." : "Entrar"}
                    </button>
                </form>

                {mensagem.texto && (
                    <p 
                        id="mensagem"
                        className="mensagem" 
                        style={{ color: mensagem.tipo === "erro" ? "#ff6b6b" : "#4ade80" }}
                    >
                        {mensagem.texto}
                    </p>
                )}

                {/* Links adicionais */}
                <div className="login-links">
                    <a href="/recuperar-senha" className="login-link">
                        Esqueci minha senha
                    </a>
                    <span className="link-separator">|</span>
                    <a href="/cadastro" className="login-link">
                        Criar nova conta
                    </a>
                </div>
            </div>
        </div>
    );
}

export default PaginaLogin;