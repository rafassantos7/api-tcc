
import './styles.css';
import { useState } from "react";

function FormularioCadastro() {
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [telefone, setTelefone] = useState('');
    const [senha, setSenha] = useState('');
    const [dataNascimento, setDataNascimento] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    // Mock das funções para demonstração
    const navigate = (path) => console.log('Navegando para:', path);
    const exibirMensagem = (msg, tipo) => console.log(`${tipo.toUpperCase()}: ${msg}`);

    const cadastrarUsuario = async () => {
        try {
            setIsLoading(true);
            // Simular delay da API
            await new Promise(resolve => setTimeout(resolve, 2000));
            
            // Sua lógica original aqui
            const [ano, mes, dia] = dataNascimento.split('-');
            const dataFormatada = `${dia}-${mes}-${ano}`;
            
            exibirMensagem('Usuário cadastrado com sucesso!', 'sucesso');
            
            // Limpa os campos
            setNome(''); setEmail(''); setTelefone(''); setSenha(''); setDataNascimento('');
            
            setTimeout(() => {
                navigate('/tarefas');
            }, 2500);
        } catch (error) {
            exibirMensagem('Erro ao conectar ao servidor.', 'erro');
        } finally {
            setIsLoading(false);
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        cadastrarUsuario();
    };

    return (
    <div className="tela-cadastro">
            {/* Elementos decorativos de fundo */}
            <div className="decoracao-fundo">
                <div className="absolute top-20 left-20 w-72 h-72 bg-purple-500 rounded-full mix-blend-multiply filter blur-xl animate-pulse"></div>
                <div className="absolute top-40 right-20 w-72 h-72 bg-pink-500 rounded-full mix-blend-multiply filter blur-xl animate-pulse delay-1000"></div>
                <div className="absolute -bottom-8 left-40 w-72 h-72 bg-blue-500 rounded-full mix-blend-multiply filter blur-xl animate-pulse delay-2000"></div>
            </div>
            
            {/* Grid pattern */}
            <div className="padrao-grade"></div>

            {/* Navigation */}
            <nav className="barra-navegacao">
                <div className="logo-cadastro">LevelUp</div>
                <a 
                    href="/bemvindo" 
                    className="link-voltar" 
                >
                    ← Voltar
                </a>
            </nav>

            {/* Main content */}

            <div className="conteudo-cadastro">
                <div className="container-cadastro">
                    {/* Formulário */}
                    <div className="formulario-cadastro">
                        <div className="caixa-formulario">
                            {/* Header do formulário */}
                            <div className="cabecalho-formulario">
                                <div className="icone-formulario">
                                    <span className="icone-efeito">🚀</span>
                                </div>
                                <h2 className="titulo-principal">Junte-se ao time</h2>
                                <p className="frase-efeito">
                                    Crie sua conta e comece a organizar suas tarefas de forma inteligente
                                </p>
                            </div>
                            {/* Campos do formulário */}
                            <div className="espacamento-campos">
                                <div className="campo-formulario">
                                    <label className="rotulo-campo">Nome completo</label>
                                    <input 
                                        type="text" 
                                        placeholder="Digite seu nome completo" 
                                        value={nome} 
                                        onChange={(e) => setNome(e.target.value)} 
                                        required 
                                        className="input-cadastro"
                                    />
                                </div>
                                <div className="campo-formulario">
                                    <label className="rotulo-campo">Email</label>
                                    <input 
                                        type="email" 
                                        placeholder="seu@email.com" 
                                        value={email} 
                                        onChange={(e) => setEmail(e.target.value)} 
                                        required 
                                        className="input-cadastro"
                                    />
                                </div>
                                <div className="campo-formulario">
                                    <label className="rotulo-campo">Telefone</label>
                                    <input 
                                        type="text" 
                                        placeholder="(11) 99999-9999" 
                                        value={telefone} 
                                        onChange={(e) => setTelefone(e.target.value)} 
                                        required 
                                        className="input-cadastro"
                                    />
                                </div>
                                <div className="campo-formulario">
                                    <label className="rotulo-campo">Senha</label>
                                    <input 
                                        type="password" 
                                        placeholder="Crie uma senha segura" 
                                        value={senha} 
                                        onChange={(e) => setSenha(e.target.value)} 
                                        required 
                                        className="input-cadastro"
                                    />
                                </div>
                                <div className="campo-formulario">
                                    <label className="rotulo-campo">Data de nascimento</label>
                                    <input
                                        type="date"
                                        value={dataNascimento}
                                        onChange={(e) => setDataNascimento(e.target.value)}
                                        required
                                        className="input-cadastro"
                                    />
                                </div>
                                <button 
                                    onClick={handleSubmit}
                                    className="botao-cadastrar-cadastro"
                                    disabled={isLoading}
                                >
                                    {isLoading ? (
                                        <>
                                            <div className="w-5 h-5 border-2 border-white/20 border-t-white rounded-full animate-spin"></div>
                                            Cadastrando...
                                        </>
                                    ) : (
                                        <>
                                            Criar conta
                                            <span className="group-hover:translate-x-1 transition-transform duration-200">→</span>
                                        </>
                                    )}
                                    <div className="absolute inset-0 bg-gradient-to-r from-purple-700 to-pink-700 rounded-2xl opacity-0 group-hover:opacity-100 transition-opacity duration-300"></div>
                                </button>
                            </div>
                            {/* Link para usuários */}
                            <div className="area-link-usuarios">
                                <button 
                                    onClick={() => navigate('/usuarios')} 
                                    className="link-usuarios"
                                >
                                    Ver usuários cadastrados
                                </button>
                            </div>
                        </div>
                    </div>
                    {/* Features laterais (dentro do container, como no início) */}
                    <div className="informacoes-laterais">
                        <div className="espacamento-infos">
                            <div className="info-item">
                                <div className="info-icone">
                                    <span className="icone-info">✨</span>
                                </div>
                                <div>
                                    <h4 className="titulo-info">Interface intuitiva</h4>
                                    <p className="descricao-info">Design pensado para facilitar sua rotina diária</p>
                                </div>
                            </div>
                            <div className="info-item">
                                <div className="info-icone azul-roxo">
                                    <span className="icone-info">🔒</span>
                                </div>
                                <div>
                                    <h4 className="titulo-info">Dados seguros</h4>
                                    <p className="descricao-info">Suas informações protegidas com criptografia avançada</p>
                                </div>
                            </div>
                            <div className="info-item">
                                <div className="info-icone verde-azul">
                                    <span className="icone-info">📱</span>
                                </div>
                                <div>
                                    <h4 className="titulo-info">Acesso em qualquer lugar</h4>
                                    <p className="descricao-info">Sincronização automática em todos os seus dispositivos</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <style jsx>{`
                .bg-grid-pattern {
                    background-image: 
                        linear-gradient(rgba(255,255,255,0.1) 1px, transparent 1px),
                        linear-gradient(90deg, rgba(255,255,255,0.1) 1px, transparent 1px);
                    background-size: 50px 50px;
                }
            `}</style>
        </div>
    );
}

export default FormularioCadastro;




















// import { useState } from "react";
// import './styles.css';
// import { useNavigate } from "react-router-dom";
// import useMensagem from '../../hooks/useMensagem';
// import MensagemFeedback from '../MensagemFeedback';
// import logo from '../../assets/images/lvlup-icon-re.png';
// import axios from 'axios';

// function FormularioCadastro() {
//     const [nome, setNome] = useState('');
//     const [email, setEmail] = useState('');
//     const [telefone, setTelefone] = useState('');
//     const [senha, setSenha] = useState('');
//     const [dataNascimento, setDataNascimento] = useState('');

//     const navigate = useNavigate();
//     const { exibirMensagem , mensagem, tipoMensagem, visivel, fecharMensagem } = useMensagem();

//     const cadastrarUsuario = async () => {
//         try {
//             const [ano, mes, dia] = dataNascimento.split('-');
//             const dataFormatada = `${dia}-${mes}-${ano}`; // formato que o backend espera: dd-MM-yyyy
    
//             const response = await axios.post('http://localhost:8080/usuarios', {
//                 nome,
//                 email,
//                 telefone,
//                 senha,
//                 dataNascimento: dataFormatada
//             });
    
//             exibirMensagem(response.data.mensagem || 'Usuário cadastrado com sucesso!', 'sucesso');
    
//             // limpa os campos
//             setNome('');
//             setEmail('');
//             setTelefone('');
//             setSenha('');
//             setDataNascimento('');
    
//             setTimeout(() => {
//                 navigate('/tarefas');
//             }, 2500);
//         } catch (error) {
//             let erroMsg = 'Erro ao conectar ao servidor.';
//             if (error.response && error.response.data) {
//                 erroMsg = error.response.data.mensagem;
//                 if (error.response.data.erros) {
//                     erroMsg += ' ' + Object.values(error.response.data.erros).join(', ');
//                 }
//             }
//             exibirMensagem(erroMsg, 'erro');
//         }
//     };
    

//     return (
//         <div className="pagina-cadastro">
//             <video autoPlay loop muted className="video-fundo">
//                 <source src="/videos/video-fundo.mp4" type="video/mp4" />
//             </video>

//             <div className="container">
//                 <img src={logo} style={{ marginBottom: 0 }} alt="Logo da empresa" />
//                 <h2>Cadastro de usuário</h2>
//                 <form onSubmit={(e) => { e.preventDefault(); cadastrarUsuario() }}>
//                     <input type="text" placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} required />
//                     <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
//                     <input type="text" placeholder="Telefone" value={telefone} onChange={(e) => setTelefone(e.target.value)} required />
//                     <input type="password" placeholder="Senha" value={senha} onChange={(e) => setSenha(e.target.value)} required />
//                     <input
//                         type="date"
//                         value={dataNascimento}
//                         onChange={(e) => setDataNascimento(e.target.value)}
//                         required
//                     />
//                     <button type="submit">Cadastrar</button>
//                 </form>

//                 <button onClick={() => navigate('/usuarios')} className="link-usuarios">
//                     Ver usuários cadastrados
//                 </button>

//                 <MensagemFeedback
//                     mensagem={mensagem}
//                     tipo={tipoMensagem}
//                     visivel={visivel}
//                     onclose={fecharMensagem}
//                 />
//             </div>
//         </div>
//     );
// }

// export default FormularioCadastro;
