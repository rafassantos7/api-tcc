import React, { useState } from 'react';
import './styles.css';

function useCadastro(navigate = () => {}, exibirMensagem = () => {}) {
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [telefone, setTelefone] = useState('');
    const [senha, setSenha] = useState('');
    const [dataNascimento, setDataNascimento] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [erro, setErro] = useState(''); // Para mostrar pop-up de erro
    const [mostrarErro, setMostrarErro] = useState(false);

    const cadastrarUsuario = async () => {
        // Campos obrigatórios
        if (!nome.trim() || !email.trim() || !senha.trim() || !dataNascimento.trim()) {
            setErro('Preencha todos os campos obrigatórios.');
            setMostrarErro(true);
            return;
        }

        // Nome não pode conter números
        if (/\d/.test(nome)) {
            setErro('O nome não pode conter números.');
            setMostrarErro(true);
            return;
        }

        // Senha deve ter no mínimo 8 caracteres
        if (senha.length < 8) {
            setErro('A senha deve ter pelo menos 8 caracteres.');
            setMostrarErro(true);
            return;
        }

        // Telefone — opcional, mas se preenchido precisa ser válido
        if (telefone.trim() && telefone.trim().length < 10) {
            setErro('Digite um número de telefone válido.');
            setMostrarErro(true);
            return;
        }

        try {
            setIsLoading(true);
            await new Promise(resolve => setTimeout(resolve, 2000));

            const [ano, mes, dia] = dataNascimento.split('-');
            const dataFormatada = `${dia}-${mes}-${ano}`;

            console.log('Dados enviados (mock):', {
                nome,
                email,
                telefone,
                senha,
                dataNascimento: dataFormatada,
            });

            exibirMensagem('Usuário cadastrado com sucesso!', 'sucesso');

            // Limpa o formulário
            setNome('');
            setEmail('');
            setTelefone('');
            setSenha('');
            setDataNascimento('');

            // Navega após sucesso
            setTimeout(() => navigate('/tarefas'), 2500);

        } catch (error) {
            setErro('Erro ao conectar ao servidor.');
            setMostrarErro(true);
        } finally {
            setIsLoading(false);
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        cadastrarUsuario();
    };

    return {
        nome, setNome,
        email, setEmail,
        telefone, setTelefone,
        senha, setSenha,
        dataNascimento, setDataNascimento,
        isLoading,
        handleSubmit,
        erro,
        mostrarErro,
        setMostrarErro
    };
}

function FormularioCadastro() {
    const {
        nome, setNome,
        email, setEmail,
        telefone, setTelefone,
        senha, setSenha,
        dataNascimento, setDataNascimento,
        isLoading,
        handleSubmit,
        erro,
        mostrarErro,
        setMostrarErro
    } = useCadastro();

    return (
        <div className="tela-cadastro min-h-screen flex items-center justify-center p-4 bg-gray-900 font-sans relative">
            
            {mostrarErro && (
                <div className="modal-erro fixed inset-0 flex items-center justify-center bg-black/50 z-50">
                    <div className="bg-white p-6 rounded-xl max-w-sm w-full text-center">
                        <p className="text-red-600 font-bold mb-4">{erro}</p>
                        <button
                            className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 transition"
                            onClick={() => setMostrarErro(false)}
                        >
                            Fechar
                        </button>
                    </div>
                </div>
            )}

            <form 
                onSubmit={handleSubmit} 
                className="bg-gray-800 p-8 rounded-2xl shadow-lg w-full max-w-md space-y-4"
            >
                <h2 className="text-2xl font-bold text-white text-center">Cadastrar-se</h2>
                <input
                    type="text"
                    placeholder="Nome completo"
                    value={nome}
                    onChange={e => setNome(e.target.value)}
                    className="w-full p-3 rounded-lg bg-gray-700 text-white placeholder-gray-400"
                />
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                    className="w-full p-3 rounded-lg bg-gray-700 text-white placeholder-gray-400"
                />
                <input
                    type="text"
                    placeholder="Telefone"
                    value={telefone}
                    onChange={e => setTelefone(e.target.value)}
                    className="w-full p-3 rounded-lg bg-gray-700 text-white placeholder-gray-400"
                />
                <input
                    type="password"
                    placeholder="Senha"
                    value={senha}
                    onChange={e => setSenha(e.target.value)}
                    className="w-full p-3 rounded-lg bg-gray-700 text-white placeholder-gray-400"
                />
                <input
                    type="date"
                    value={dataNascimento}
                    onChange={e => setDataNascimento(e.target.value)}
                    className="w-full p-3 rounded-lg bg-gray-700 text-white placeholder-gray-400"
                />
                <button
                    type="submit"
                    disabled={isLoading}
                    className="w-full p-3 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition disabled:opacity-50"
                >
                    {isLoading ? 'Cadastrando...' : 'Criar Conta'}
                </button>
            </form>
        </div>
    );
}

export default FormularioCadastro;
