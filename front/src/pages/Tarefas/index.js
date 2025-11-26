import React from "react";
import { useNavigate } from "react-router-dom"; 
import styles from "./styles.module.css"; // CSS isolado

export default function Home() {
  const navigate = useNavigate(); 

  const [isOpen, setIsOpen] = React.useState(false);

  const [tasks, setTasks] = React.useState(() => {
    const salvos = localStorage.getItem("minhasMetas");
    return salvos ? JSON.parse(salvos) : []; 
  });

  const mediaProgresso = tasks.length
    ? Math.round(tasks.reduce((s, t) => s + t.progress, 0) / tasks.length)
    : 0;

  const menuItems = [
    { label: "Home", path: "/" },
    { label: "Metas", path: "/metas" },
  ];

  return (
    <div className={styles["levelup-container"]}>

      <button
        className={`${styles["btn-open-menu"]} ${isOpen ? styles["open-icon"] + " " + styles["sidebar-open"] : styles["closed-icon"]}`}
        onClick={() => setIsOpen(!isOpen)}
      ></button>

      {isOpen && <div className={styles.backdrop} onClick={() => setIsOpen(false)}></div>}

      <aside className={`${styles.sidebar} ${isOpen ? styles.open : ""}`}>
        <div className={styles["sidebar-header"]}>
          <div>
            <div className={styles.titulo}>LEVEL UP</div>
            <div className={styles.subtitulo}>Maria Dias</div>
          </div>
        </div>

        <nav className={styles.menu}>
          {menuItems.map((item) => (
            <button 
              key={item.label} 
              className={styles["menu-item"]}
              onClick={() => { 
                navigate(item.path);
                setIsOpen(false);
              }}
            >
              <span className={styles.dot}></span>
              {item.label}
            </button>
          ))}
        </nav>

        <div className={styles.usuario}>
          <span className={styles.nome}>Maria Dias</span>
        </div>
      </aside>

      <main className={styles.conteudo}>
        <header className={styles.topo}>
          <h1>Minhas Tarefas</h1>
          <div className={styles.acoes}>
            <button className={styles["botao-nova"]} onClick={() => navigate('/add-task')}>
              Adicionar Tarefa +
            </button>
          </div>
        </header>

        <section className={styles.estatisticas}>
          <div className={styles["card-info"]}>
            <span>Tarefas</span>
            <h2>{tasks.length}</h2>
          </div>
          <div className={styles["card-info"]}>
            <span>Progresso médio</span>
            <h2>{mediaProgresso}%</h2>
          </div>
          <div className={styles["card-info"]}>
            <span>Subtarefas</span>
            <h2>{tasks.reduce((s, t) => s + t.subtasks, 0)}</h2>
          </div>
        </section>

        <section className={styles.tarefas}>
          {tasks.map((t) => (
            <div key={t.id} className={styles["card-tarefa"]}>
              <div className={styles["cabecalho-card"]}>
                <div>
                  <h3>{t.title}</h3>
                  <p>{t.description}</p> 
                </div>
                <span className={styles.progresso}>{t.progress}%</span>
              </div>

              <div className={styles["barra-progresso"]}>
                <div 
                  className={styles["progresso-preenchido"]} 
                  style={{ width: `${t.progress}%` }}
                ></div>
              </div>

              <div className={styles["metas-card"]}>
                <button 
                  className={styles["botao-tarefas-livre"]} 
                  onClick={() => navigate(`/editar-meta/${t.id}`)}
                >
                  Editar
                </button>

                <button 
                  className={styles["botao-tarefas-livre"]}
                  style={{ background: '#ff4444', color: 'white' }}
                  onClick={() => {
                    if(window.confirm("Deletar esta meta?")) {
                      const novas = tasks.filter(task => task.id !== t.id);
                      setTasks(novas); 
                      localStorage.setItem("minhasMetas", JSON.stringify(novas)); 
                    }
                  }}
                >
                  ✕
                </button>
              </div>
            </div>
          ))}
        </section>
      </main>
    </div>
  );
}
