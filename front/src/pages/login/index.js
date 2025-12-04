// src\pages\Cadastro\index.js

import { LogIn } from 'lucide-react'    // ÍCONE
import Login from '../../components/Login' // Seu componente
import './styles.css'


function PaginaLogin() {
<<<<<<< HEAD
    const navigate = useNavigate();

    useEffect(() => {
        // --- 1. CRIAÇÃO DO BOX E ELEMENTOS EXISTENTES ---
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

        // --- 2. NOVO ELEMENTO: ÁREA DE CADASTRO/TOGGLE ---
        const toggleArea = document.createElement("div");
        toggleArea.className = "toggle-area";
        
        const textoNaoTemConta = document.createElement("p");
        textoNaoTemConta.textContent = "Não tem uma conta?";
        toggleArea.appendChild(textoNaoTemConta);
        
        const linkCadastro = document.createElement("button");
        linkCadastro.textContent = "Cadastre-se";
        linkCadastro.className = "toggle-button";
        toggleArea.appendChild(linkCadastro);

        box.appendChild(toggleArea); // Adiciona a nova área ao box

        // --- 3. LÓGICA EXISTENTE DE LOGIN E VALIDAÇÃO (INALTERADA) ---
        function emailValido(valor) {
            return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(valor);
        }

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

        // --- 4. NOVO EVENT LISTENER: REDIRECIONAR PARA /cadastro ---
        const irParaCadastro = () => {
             navigate("/cadastro"); // Redireciona para a rota /cadastro
        };

        // --- 5. CONFIGURAÇÃO DE LISTENERS EXISTENTES E NOVOS ---
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
        linkCadastro.addEventListener("click", irParaCadastro); // NOVO LISTENER

        // --- 6. FUNÇÃO DE LIMPEZA (CLEANUP) ---
        return () => {
            // Remove o listener de login existente
            botao.removeEventListener("click", fazerLogin);
            
            // Remove o NOVO listener de cadastro
            linkCadastro.removeEventListener("click", irParaCadastro); 
            
            // Limpa o DOM
            divRoot.innerHTML = "";
        };
    }, [navigate]); // navigate deve estar nas dependências do useEffect

    return <div id="login-root" className="page-login"></div>;
=======
    return (
        <div className='pagina-login'>
            <LogIn/>
        </div>
    )
>>>>>>> 50a36b4d95646cdad2f31a54c7a6523ecdcc7ee4
}

export default PaginaLogin;