import React, { useEffect } from "react";
import "./styles.css";
import { useNavigate } from "react-router-dom";

function PaginaLogin() {
    const navigate = useNavigate();

    useEffect(() => {
        const box = document.createElement("div");
        box.className = "login-box";

        const titulo = document.createElement("h3");
        titulo.textContent = "Login";
        box.appendChild(titulo);

        const email = document.createElement("input");
        email.type = "email";
        email.placeholder = "Email";
        email.id = "email";
        box.appendChild(email);

        const senha = document.createElement("input");
        senha.type = "password";
        senha.placeholder = "Senha";
        senha.id = "senha";
        box.appendChild(senha);

        const botao = document.createElement("button");
        botao.textContent = "Entrar";
        botao.id = "btnLogin";
        box.appendChild(botao);

        const mensagem = document.createElement("p");
        mensagem.id = "mensagem";
        mensagem.className = "mensagem";
        box.appendChild(mensagem);

        const divRoot = document.getElementById("login-root");
        divRoot.appendChild(box);

        // --- VALIDAÇÃO DE EMAIL ---
        function emailValido(valor) {
            return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(valor);
        }

        // --- FUNÇÃO DE LOGIN ---
        function fazerLogin() {
            const vEmail = email.value.trim();
            const vSenha = senha.value.trim();
            mensagem.textContent = "";

            if (!vEmail) {
                mensagem.style.color = "red";
                mensagem.textContent = "Preencha o email!";
                email.focus();
                return;
            }

            if (!emailValido(vEmail)) {
                mensagem.style.color = "red";
                mensagem.textContent = "Digite um email válido!";
                email.focus();
                return;
            }

            if (!vSenha) {
                mensagem.style.color = "red";
                mensagem.textContent = "Preencha a senha!";
                senha.focus();
                return;
            }

            // LOGIN CERTO
            if (vEmail === "usuario@gmail.com" && vSenha === "123456") {
                mensagem.style.color = "green";
                mensagem.textContent = "Login realizado com sucesso!";
                setTimeout(() => navigate("/tarefas"), 600);
                return;
            }

            // LOGIN ERRADO
            mensagem.style.color = "red";
            mensagem.textContent = "Email ou senha incorretos!";
        }

        // --- ENTER NO EMAIL ---
        email.addEventListener("keydown", (e) => {
            if (e.key === "Enter") {
                if (!email.value.trim()) {
                    mensagem.style.color = "red";
                    mensagem.textContent = "Preencha o email!";
                    return;
                }

                if (!emailValido(email.value.trim())) {
                    mensagem.style.color = "red";
                    mensagem.textContent = "Digite um email válido!";
                    return;
                }

                mensagem.textContent = "";
                senha.focus();
            }
        });

        // --- ENTER NA SENHA ---
        senha.addEventListener("keydown", (e) => {
            if (e.key === "Enter") {
                if (!senha.value.trim()) {
                    mensagem.style.color = "red";
                    mensagem.textContent = "Preencha a senha!";
                    return;
                }

                fazerLogin();
            }
        });

        botao.addEventListener("click", fazerLogin);

        return () => {
            botao.removeEventListener("click", fazerLogin);
            divRoot.innerHTML = "";
        };
    }, []);

    return <div id="login-root" className="page-login"></div>;
}

export default PaginaLogin;
