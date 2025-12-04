import './styles.css';
import { useState } from "react";
import { useNavigate } from 'react-router-dom';

function FormularioCadastro() {
    const navigate = useNavigate();
    
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [telefone, setTelefone] = useState('');
    const [senha, setSenha] = useState('');
    const [dataNascimento, setDataNascimento] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [mensagem, setMensagem] = useState({ texto: '', tipo: '' });

    const formatarTelefone = (valor) => {
        const numeros = valor.replace(/\D/g, '');
        if (numeros.length <= 10) {
            return numeros.replace(/(\d{2})(\d{4})(\d{4})/, '($1) $2-$3');
        } else {
            return numeros.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
        }
    };

    const handleTelefoneChange = (e) => {
        const valorFormatado = formatarTelefone(e.target.value);
        setTelefone(valorFormatado);
    };

    const validarEmail = (email) => {
        return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    };

    const validarTelefone = (telefone) => {
        const numeros = telefone.replace(/\D/g, '');
        return numeros.length >= 10 && numeros.length <= 11;
    };

    const cadastrarUsuario = async () => {
        try {
            setIsLoading(true);
            setMensagem({ texto: '', tipo: '' });

            // Validações
            if (!nome.trim()) {
                setMensagem({ texto: 'Preencha o nome completo!', tipo: 'erro' });
                return;
            }

            if (!email.trim()) {
                setMensagem({ texto: 'Preencha o email!', tipo: 'erro' });
                return;
            }

            if (!validarEmail(email)) {
                setMensagem({ texto: 'Digite um email válido!', tipo: 'erro' });
                return;
            }

            if (!telefone.trim()) {
                setMensagem({ texto: 'Preencha o telefone!', tipo: 'erro' });
                return;
            }

            if (!validarTelefone(telefone)) {
                setMensagem({ texto: 'Digite um telefone válido (10 ou 11 dígitos)!', tipo: 'erro' });
                return;
            }

            if (!senha.trim()) {
                setMensagem({ texto: 'Preencha a senha!', tipo: 'erro' });
                return;
            }

            if (senha.length < 6) {
                setMensagem({ texto: 'A senha deve ter pelo menos 6 caracteres!', tipo: 'erro' });
                return;
            }

            if (!dataNascimento) {
                setMensagem({ texto: 'Preencha a data de nascimento!', tipo: 'erro' });
                return;
            }

            // Preparar dados para envio
            const dadosCadastro = {
                nome: nome.trim(),
                email: email.trim().toLowerCase(),
                telefone: telefone.replace(/\D/g, ''),
                senha: senha,
                dataNascimento: dataNascimento
            };

            console.log('Dados para cadastro:', dadosCadastro);
            
            // Simulação de chamada à API
            await new Promise(resolve => setTimeout(resolve, 2000));
            
            // Simulação de sucesso
            setMensagem({ 
                texto: 'Cadastro realizado com sucesso! Redirecionando...', 
                tipo: 'sucesso' 
            });

            // Limpar formulário
            setNome('');
            setEmail('');
            setTelefone('');
            setSenha('');
            setDataNascimento('');
            
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
            
            {/* Navigation */}
            <nav className="barra-navegacao">
                <div className="logo-cadastro">LevelUp</div>
                <button 
                    onClick={handleVoltar}
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
                            
                            {/* Mensagem de feedback */}
                            {mensagem.texto && (
                                <div className={`mensagem-erro ${mensagem.tipo === 'sucesso' ? 'mensagem-sucesso' : ''}`}>
                                    {mensagem.texto}
                                </div>
                            )}
                            
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
                                        placeholder="Crie uma senha segura (mínimo 6 caracteres)" 
                                        value={senha} 
                                        onChange={(e) => setSenha(e.target.value)} 
                                        required 
                                        className="input-cadastro"
                                        disabled={isLoading}
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