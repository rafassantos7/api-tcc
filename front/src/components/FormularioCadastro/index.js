import './styles.css';
import { useState } from "react";

function FormularioCadastro() {
    const navigate = useNavigate();
    
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
            
            // Redirecionar após 2 segundos
            setTimeout(() => {
                navigate('/tarefas');
            }, 2000);

        } catch (error) {
            console.error('Erro no cadastro:', error);
            
            let mensagemErro = 'Erro ao conectar ao servidor.';
            
            if (error.response) {
                // Erro da API
                const errorData = error.response.data;
                console.error('Detalhes do erro da API:', errorData);
                
                mensagemErro = errorData.message || errorData.error || 'Falha no cadastro. Verifique os dados.';
                
                // Tratamento específico para email duplicado
                if (mensagemErro.toLowerCase().includes('email') && 
                    (mensagemErro.toLowerCase().includes('já existe') || 
                     mensagemErro.toLowerCase().includes('duplicado'))) {
                    mensagemErro = 'Este email já está cadastrado! Tente recuperar sua senha ou use outro email.';
                }
            } else if (error.request) {
                mensagemErro = 'Erro de conexão. Verifique sua internet e tente novamente.';
            }

            setMensagem({ texto: mensagemErro, tipo: 'erro' });

        } finally {
            setIsLoading(false);
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        cadastrarUsuario();
    };

    const handleVoltar = (e) => {
        e.preventDefault();
        navigate('/bemvindo');
    };

    return (
        <div className="tela-cadastro">
            {/* Elementos decorativos de fundo */}
            <div className="decoracao-fundo">
                <div className="bolha bolha-roxo"></div>
                <div className="bolha bolha-rosa"></div>
                <div className="bolha bolha-azul"></div>
            </div>
            
            {/* Navigation - APENAS ESTA PARTE FOI MODIFICADA */}
            <nav className="barra-navegacao">
                <div className="logo-cadastro">LevelUp</div>
                <a 
                    href="/bemvindo" 
                    className="link-voltar" 
                >
                    ← Voltar
                </button>
            </nav>

            {/* Main content */}
            <div className="conteudo-cadastro">
                <div className="container-cadastro">
                    {/* Formulário */}
                    <div className="formulario-cadastro">
                        <div className="caixa-formulario">
                            {/* Header do formulário */}
                            <div className="cabecalho-formulario">
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
                                        disabled={isLoading}
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
                                        disabled={isLoading}
                                    />
                                </div>
                                <div className="campo-formulario">
                                    <label className="rotulo-campo">Telefone</label>
                                    <input 
                                        type="text" 
                                        placeholder="(11) 99999-9999" 
                                        value={telefone} 
                                        onChange={handleTelefoneChange}
                                        required 
                                        className="input-cadastro"
                                        disabled={isLoading}
                                    />
                                </div>
                                <div className="campo-formulario campo-senha">
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
                                        disabled={isLoading}
                                    />
                                </div>
                                <button 
                                    onClick={handleSubmit}
                                    className="botao-cadastrar-cadastro"
                                    disabled={isLoading}
                                >
                                    {isLoading ? (
                                        <>
                                            <div className="spinner"></div>
                                            Cadastrando...
                                        </>
                                    ) : (
                                        <>
                                            Criar conta
                                            <span className="seta-botao">→</span>
                                        </>
                                    )}
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default FormularioCadastro;