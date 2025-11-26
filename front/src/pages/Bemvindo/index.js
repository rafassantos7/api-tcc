// PaginaBemvindo.jsx
import React, { useEffect, useState } from 'react';
import './styles.css';
import { useNavigate } from 'react-router-dom';

function PaginaBemvindo() {
  const navigate = useNavigate();
  const [frase, setFrase] = useState('');
  const [isLoaded, setIsLoaded] = useState(false);

  useEffect(() => {
    const frasesInspiradoras = [
      "A jornada de mil milhas começa com um único passo.",
      "Não espere por oportunidades. Crie-as.",
      "Tudo parece impossível até que seja feito.",
      "Grandes conquistas começam com pequenos passos.",
      "Disciplina vence talento quando o talento não trabalha duro.",
      "O suor de hoje é a vitória de amanhã.",
      "A vida é 10% o que acontece com você e 90% como você reage a isso.",
      "Você é mais forte do que pensa e está mais perto do que imagina.",
      "Coragem não é a ausência do medo, é a decisão de que algo é mais importante do que o medo.",
    ];
    const aleatoria = frasesInspiradoras[Math.floor(Math.random() * frasesInspiradoras.length)];
    setFrase(aleatoria);
    setTimeout(() => setIsLoaded(true), 500);
  }, []);

  return (
    <div className="pagina-bemvindo">
      {/* Elementos decorativos */}
      <div className="bg-decorativo">
        <div className="blob blob-1"></div>
        <div className="blob blob-2"></div>
        <div className="blob blob-3"></div>
      </div>

      <div className="grid-pattern"></div>

      {/* Navigation */}
      <nav className="navbar">
        <div className="logo">LevelUp</div>
        <div className="versao">Versão 1.0</div>
        <div className="navbar-actions">
          <button onClick={() => navigate('/login')} className="botao-login">Login</button>
        </div>
      </nav>

      {/* Main content */}
      <div className="conteudo-principal">
        <div className={`conteudo-centro ${isLoaded ? 'loaded' : ''}`}>
          <div className="badge">
            <span className="status-dot"></span>
            Produtividade em primeiro lugar
          </div>

          <h1 className="titulo-principal">
            Organize sua <span className="titulo-destaque">vida da melhor forma</span>
          </h1>

          <p className="subtitulo">
            Transforme suas ideias em ações concretas. Gerencie tarefas, projetos e metas de forma intuitiva e eficiente.
          </p>

          <div className="frase-container">
            <p className="frase-inspiradora">"{frase}"</p>
          </div>

          <div className="botoes-cta">
            <button onClick={() => navigate('/cadastro')} className="botao-comecar">
              <span>Começar agora</span>
            </button>
            <button className="botao-sabermais">Saber mais</button>
           
          </div>

          <div className="features">
            <div className="botao-tarefa">
              <div className="botao-tarefa-icon">✅</div>
              <h3>Botão Tarefa</h3>
              <p>Organize suas atividades com prioridades e lembretes automáticos</p>
            </div>
            <div className="botao-relatorio-visual">
              <div className="botao-relatorio-visual-icon">📊</div>
              <h3>Botão Relatório Visual</h3>
              <p>Acompanhe seu progresso com gráficos e estatísticas detalhadas</p>
            </div>
            <div className="botao-meta">
              <div className="botao-meta-icon">🎯</div>
              <h3>Botão Meta</h3>
              <p>Defina objetivos claros e acompanhe cada conquista</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default PaginaBemvindo;
