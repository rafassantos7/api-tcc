import React from "react";
import { useNavigate } from "react-router-dom"; 
import styles from "./styles.module.css";

export default function Home() {
  const navigate = useNavigate(); 
  const [isOpen, setIsOpen] = React.useState(false);
  const [isMobile, setIsMobile] = React.useState(false);
  
  const [tasks, setTasks] = React.useState(() => {
    const salvos = localStorage.getItem("minhasMetas");
    return salvos ? JSON.parse(salvos) : []; 
  });

  React.useEffect(() => {
    const checkMobile = () => {
      const mobile = window.innerWidth < 1024;
      setIsMobile(mobile);
      
      // Se for mobile, fecha o menu
      // Se for desktop, mantém aberto
      if (mobile) {
        setIsOpen(false);
      } else {
        setIsOpen(true);
      }
    };

    const handleKeyDown = (e) => {
      if (e.key === 'Escape' && isOpen) {
        setIsOpen(false);
      }
    };

    const handleClickOutside = (e) => {
      // Fecha o menu se clicar fora e for mobile
      if (isOpen && isMobile && !e.target.closest(`.${styles.sidebar}`) && 
          !e.target.closest(`.${styles["btn-open-menu"]}`)) {
        setIsOpen(false);
      }
    };

    checkMobile(); // Verifica na montagem inicial
    window.addEventListener('keydown', handleKeyDown);
    window.addEventListener('resize', checkMobile);
    window.addEventListener('click', handleClickOutside);

    return () => {
      window.removeEventListener('keydown', handleKeyDown);
      window.removeEventListener('resize', checkMobile);
      window.removeEventListener('click', handleClickOutside);
    };
  }, [isOpen, isMobile]);

  const mediaProgresso = tasks.length
    ? Math.round(tasks.reduce((s, t) => s + t.progress, 0) / tasks.length)
    : 0;

  const totalSubtasks = tasks.reduce((s, t) => s + (t.subtasks || 0), 0);

  const menuItems = [
    { label: "Home", path: "/", icon: "🏠" },
    { label: "Tarefas", path: "/metas", icon: "📝" },
  ];

  const handleDeleteTask = (id) => {
    if (window.confirm("Tem certeza que deseja excluir esta meta?")) {
      const novas = tasks.filter(task => task.id !== id);
      setTasks(novas); 
      localStorage.setItem("minhasMetas", JSON.stringify(novas));
    }
  };

  const getProgressColor = (progress) => {
    if (progress >= 80) return '#10b981';
    if (progress >= 50) return '#f59e0b';
    return '#ef4444';
  };

  // Função para alternar o menu com lógica melhorada
  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  // Função para fechar o menu quando navegar (só em mobile)
  const handleNavigate = (path) => {
    navigate(path);
    if (isMobile) {
      setIsOpen(false);
    }
  };

  return (
    <div className={styles["levelup-container"]}>
      {/* Botão do menu - Só aparece em mobile */}
      {isMobile && (
        <button
          className={`${styles["btn-open-menu"]} ${isOpen ? styles["open-icon"] : styles["closed-icon"]}`}
          onClick={toggleMenu}
          aria-label={isOpen ? "Fechar menu" : "Abrir menu"}
          aria-expanded={isOpen}
        >
          {isOpen ? "✕" : "☰"}
        </button>
      )}

      {/* Backdrop - Só aparece quando menu aberto em mobile */}
      {isMobile && isOpen && (
        <div 
          className={styles.backdrop}
          onClick={() => setIsOpen(false)}
          role="presentation"
          aria-label="Fechar menu"
        />
      )}

      {/* Sidebar */}
      <aside 
  className={`${styles.sidebar} ${isOpen ? styles.open : ""} ${!isMobile ? styles["desktop-open"] : ""}`}
  aria-label="Menu de navegação"
>
  <div className={styles["sidebar-header"]}>
    <div>
      {/* MUDANÇA AQUI: envolva o LEVEL UP em um botão */}
      <button 
        onClick={() => navigate('/')}
        style={{
          background: 'none',
          border: 'none',
          cursor: 'pointer',
          padding: 0,
          textAlign: 'left',
          fontFamily: 'inherit',
          fontSize: 'inherit',
          fontWeight: 'inherit',
          color: 'inherit',
          width: '100%'
        }}
      >
        <div className={styles.titulo}>LEVEL UP</div>
        <div className={styles.subtitulo}>Maria Dias</div>
      </button>
    </div>
    {/* Botão de fechar só em mobile */}
    {isMobile && (
      <button 
        className={styles["close-menu-btn"]}
        onClick={() => setIsOpen(false)}
        aria-label="Fechar menu"
      >
        ✕
      </button>
    )}
    <div className={styles["user-avatar"]}>
      👤
    </div>
  </div>

  <nav className={styles.menu} aria-label="Navegação principal">
    {menuItems.map((item) => (
      <button 
        key={item.label} 
        className={`${styles["menu-item"]} ${window.location.pathname === item.path ? styles.active : ""}`}
        onClick={() => handleNavigate(item.path)}
        aria-current={window.location.pathname === item.path ? "page" : undefined}
      >
        <span className={styles["menu-icon"]}>{item.icon}</span>
        <span className={styles["menu-label"]}>{item.label}</span>
      </button>
    ))}
  </nav>

  <div className={styles["sidebar-footer"]}>
    <div className={styles.usuario}>
      <span className={styles["usuario-nome"]}>Maria Dias</span>
      <span className={styles["usuario-email"]}>maria@email.com</span>
    </div>
    <button 
      className={styles["btn-logout"]}
      onClick={() => console.log("Logout")}
      aria-label="Sair da conta"
    >
      →
    </button>
  </div>
</aside>

      {/* Conteúdo principal */}
      <main className={`${styles.conteudo} ${isOpen && !isMobile ? styles["sidebar-open"] : ""}`}>
        <header className={styles.topo}>
          <div>
            <h1 className={styles["page-title"]}>Minhas Tarefas</h1>
            <p className={styles["page-subtitle"]}>
              {tasks.length > 0 
                ? `${tasks.length} tarefa${tasks.length !== 1 ? 's' : ''} encontrada${tasks.length !== 1 ? 's' : ''}`
                : 'Nenhuma tarefa cadastrada'
              }
            </p>
          </div>
          <div className={styles.acoes}>
            <button 
              className={styles["botao-nova"]} 
              onClick={() => navigate('/add-task')}
              aria-label="Adicionar nova tarefa"
            >
              <span className={styles["btn-icon"]}>+</span>
              Nova Tarefa
            </button>
          </div>
        </header>

        <section className={styles.estatisticas} aria-label="Estatísticas">
          <div className={styles["card-info"]}>
            <span className={styles["card-label"]}>Tarefas</span>
            <h2 className={styles["card-value"]}>{tasks.length}</h2>
            <div className={styles["card-trend"]}>
              {tasks.length > 0 ? "📈 Ativas" : "Sem tarefas"}
            </div>
          </div>
          <div className={styles["card-info"]}>
            <span className={styles["card-label"]}>Progresso médio</span>
            <h2 className={styles["card-value"]}>{mediaProgresso}%</h2>
            <div className={styles["progress-indicator"]}>
              <div 
                className={styles["progress-bar"]}
                style={{ width: `${mediaProgresso}%` }}
              />
            </div>
          </div>
          <div className={styles["card-info"]}>
            <span className={styles["card-label"]}>Subtarefas</span>
            <h2 className={styles["card-value"]}>{totalSubtasks}</h2>
            <div className={styles["card-trend"]}>
              {totalSubtasks > 0 ? "🔗 Vinculadas" : "Simples"}
            </div>
          </div>
        </section>

        {tasks.length === 0 ? (
          <div className={styles["empty-state"]}>
            
            <h3>Nenhuma tarefa cadastrada</h3>
            <p>Comece criando sua primeira tarefa para organizar suas metas</p>
            <button 
              className={styles["botao-nova"]}
              onClick={() => navigate('/add-task')}
            >
              Criar Primeira Tarefa
            </button>
          </div>
        ) : (
          <section className={styles.tarefas}>
            {tasks.map((t) => (
              <div 
                key={t.id} 
                className={`${styles["card-tarefa"]} ${t.priority ? styles[`priority-${t.priority}`] : ''}`}
                role="article"
                aria-label={`Tarefa: ${t.title}, Progresso: ${t.progress}%`}
              >
                <div className={styles["cabecalho-card"]}>
                  <div>
                    <div className={styles["task-header"]}>
                      <h3 className={styles["task-title"]}>{t.title}</h3>
                      {t.priority && (
                        <span className={`${styles["priority-badge"]} ${styles[`priority-${t.priority}`]}`}>
                          {t.priority === 'high' ? 'Urgente' : t.priority === 'medium' ? 'Média' : 'Baixa'}
                        </span>
                      )}
                    </div>
                    <p className={styles["task-description"]}>{t.description}</p>
                    <div className={styles["task-meta"]}>
                      {t.category && (
                        <span className={styles["task-category"]}>{t.category}</span>
                      )}
                      {t.dueDate && (
                        <span className={styles["task-due"]}>
                          ⏰ {new Date(t.dueDate).toLocaleDateString()}
                        </span>
                      )}
                    </div>
                  </div>
                  <span 
                    className={styles.progresso}
                    style={{ color: getProgressColor(t.progress) }}
                  >
                    {t.progress}%
                  </span>
                </div>

                <div className={styles["barra-progresso"]}>
                  <div 
                    className={styles["progresso-preenchido"]} 
                    style={{ 
                      width: `${t.progress}%`,
                      backgroundColor: getProgressColor(t.progress)
                    }}
                    role="progressbar"
                    aria-valuenow={t.progress}
                    aria-valuemin="0"
                    aria-valuemax="100"
                  />
                </div>

                <div className={styles["metas-card"]}>
                  <button 
                    className={styles["botao-tarefas-livre"]}
                    onClick={() => navigate(`/editar-meta/${t.id}`)}
                    aria-label={`Editar tarefa ${t.title}`}
                  >
                    ✏️ Editar
                  </button>

                  <div className={styles["task-actions"]}>
                    <button 
                      className={`${styles["task-action-btn"]} ${styles["complete-btn"]}`}
                      onClick={() => {
                        const novas = tasks.map(task => 
                          task.id === t.id 
                            ? { ...task, progress: Math.min(100, task.progress + 10) }
                            : task
                        );
                        setTasks(novas);
                        localStorage.setItem("minhasMetas", JSON.stringify(novas));
                      }}
                      aria-label={`Aumentar progresso da tarefa ${t.title}`}
                    >
                      +10%
                    </button>
                    
                    <button 
                      className={`${styles["task-action-btn"]} ${styles["delete-btn"]}`}
                      onClick={() => handleDeleteTask(t.id)}
                      aria-label={`Excluir tarefa ${t.title}`}
                    >
                      ✕
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </section>
        )}
      </main>
    </div>
  );
}