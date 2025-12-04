import React, { useEffect, useState } from 'react';
import { ArrowRight, Gem, Target, BarChart3, Trophy, Zap, CheckCircle2 } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import './styles.css'; 

export default function WelcomePage() {
  const navigate = useNavigate();
  const [frase, setFrase] = useState('');
  const [isLoaded, setIsLoaded] = useState(false);

  const handleStart = () => {
    navigate('/login');
  };

  const handleCadastro = () => {
    navigate('/cadastro');
  };

  useEffect(() => {
    // Lógica da Frase Inspiradora
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
    
    // Animação de carregamento
    setTimeout(() => setIsLoaded(true), 500);
  }, []);

  const features = [
    {
      icon: Target,
      title: 'Metas Inteligentes',
      description: 'Defina objetivos claros e acompanhe seu progresso em tempo real com gráficos intuitivos.'
    },
    {
      icon: BarChart3,
      title: 'Análise Detalhada',
      description: 'Visualize suas tarefas, metas e hábitos com dashboards completos e métricas precisas.'
    },
    {
      icon: Zap,
      title: 'Hábitos Poderosos',
      description: 'Construa rotinas consistentes e acompanhe streaks para manter a motivação diária.'
    },
    {
      icon: Trophy,
      title: 'Gamificação',
      title2: 'Gamificação', 
      description: 'Ganhe experiência, desbloqueie conquistas e suba de nível conforme atinge seus objetivos.'
    }
  ];

  return (
    <div className="pagina-bemvindo">
      {/* Elementos decorativos */}
      <div className="bg-decorativo">
        <div className="blob blob-1"></div>
        <div className="blob blob-2"></div>
        <div className="blob blob-3"></div>
      </div>
      <div className="grid-pattern"></div>

      {/* Hero Section - Conteúdo Principal */}
      <div className="hero-section">
        <div className={`conteudo-centro ${isLoaded ? 'loaded' : ''}`}>
          
          {/* Badge */}
          <div className="badge">
            <span className="status-dot"></span>
            Produtividade em primeiro lugar
          </div>

          {/* Main Title */}
          <h1 className="titulo-principal">
            Level UP
          </h1>
          
          {/* Subtitle */}
          <p className="subtitulo">
            Transforme seus objetivos em realidade. Organize sua <span className="titulo-destaque">vida da melhor forma</span>
          </p>

          {/* Frase Inspiradora */}
          <div className="frase-container">
            <p className="frase-inspiradora">"{frase}"</p>
          </div>

          {/* CTA Button */}
          <button
            onClick={handleCadastro}
            className="botao-comecar cta-hero"
          >
            <span>Comece Agora</span>
            <ArrowRight size={20} className="cta-icon" />
          </button>
        </div>
      </div>

      {/* Features Grid Section */}
      <div className="features-secao">
        <div className="secao-header">
          <h2 className="secao-titulo">
            Tudo que você precisa em um só lugar
          </h2>
          <p className="secao-subtitulo">
            Ferramentas completas para gerenciar sua vida, objetivos e desenvolvimento pessoal
          </p>
        </div>

        <div className="features-grid">
          {features.map((feature, index) => {
            const Icon = feature.icon;
            return (
              <div key={index} className="feature-card">
                <div className="feature-content">
                  
                  {/* Ícone */}
                  <div className="feature-icon-wrapper">
                    <Icon className="feature-icon" size={28} />
                  </div>

                  {/* Título */}
                  <h3 className="feature-title">
                    {feature.title}
                  </h3>

                  {/* Descrição */}
                  <p className="feature-description">
                    {feature.description}
                  </p>

                  {/* Seta de Explorar */}
                  <div className="feature-explore">
                    Explorar
                    <ArrowRight size={16} className="explore-icon" />
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      </div>

    </div>
  );
}
