import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./styles.module.css";

export default function Habitos() {
  const navigate = useNavigate();
  const [habits, setHabits] = useState(() => {
    const salvos = localStorage.getItem("meusHabitos");
    return salvos ? JSON.parse(salvos) : [];
  });

  const [newHabit, setNewHabit] = useState({
    name: "",
    description: "",
    frequency: "daily", // daily, weekly, monthly
    goal: 30, // dias para completar
    color: "#8b5cf6",
    icon: "🏃"
  });

  const [isAdding, setIsAdding] = useState(false);
  const [isMobile, setIsMobile] = useState(false);

  useEffect(() => {
    const checkMobile = () => {
      setIsMobile(window.innerWidth < 1024);
    };
    checkMobile();
    window.addEventListener("resize", checkMobile);
    return () => window.removeEventListener("resize", checkMobile);
  }, []);

  // Calcula estatísticas
  const getStats = () => {
    const totalHabits = habits.length;
    const activeHabits = habits.filter(h => h.status === "active").length;
    const completedToday = habits.filter(h => {
      const today = new Date().toDateString();
      return h.completedDates && h.completedDates.includes(today);
    }).length;
    
    const streakSum = habits.reduce((sum, h) => sum + (h.currentStreak || 0), 0);
    const avgStreak = totalHabits > 0 ? Math.round(streakSum / totalHabits) : 0;

    return { totalHabits, activeHabits, completedToday, avgStreak };
  };

  const stats = getStats();

  const handleAddHabit = () => {
    if (!newHabit.name.trim()) {
      alert("Digite o nome do hábito!");
      return;
    }

    const newHabitObj = {
      id: Date.now(),
      ...newHabit,
      createdAt: new Date().toISOString(),
      status: "active",
      currentStreak: 0,
      bestStreak: 0,
      completedDates: [],
      progress: 0,
      totalCompletions: 0
    };

    const updatedHabits = [...habits, newHabitObj];
    setHabits(updatedHabits);
    localStorage.setItem("meusHabitos", JSON.stringify(updatedHabits));
    
    setNewHabit({
      name: "",
      description: "",
      frequency: "daily",
      goal: 30,
      color: "#8b5cf6",
      icon: "🏃"
    });
    setIsAdding(false);
  };

  const toggleHabitCompletion = (habitId) => {
    const today = new Date().toDateString();
    const updatedHabits = habits.map(habit => {
      if (habit.id === habitId) {
        const isCompleted = habit.completedDates?.includes(today);
        let completedDates = habit.completedDates || [];
        let currentStreak = habit.currentStreak || 0;
        let totalCompletions = habit.totalCompletions || 0;
        
        if (isCompleted) {
          // Desmarcar
          completedDates = completedDates.filter(date => date !== today);
          currentStreak = Math.max(0, currentStreak - 1);
          totalCompletions = Math.max(0, totalCompletions - 1);
        } else {
          // Marcar como feito
          completedDates = [...completedDates, today];
          currentStreak += 1;
          totalCompletions += 1;
          
          // Atualizar melhor streak
          if (currentStreak > (habit.bestStreak || 0)) {
            habit.bestStreak = currentStreak;
          }
        }

        // Calcular progresso
        const progress = Math.min(100, Math.round((totalCompletions / habit.goal) * 100));

        return {
          ...habit,
          completedDates,
          currentStreak,
          totalCompletions,
          progress,
          lastUpdated: new Date().toISOString()
        };
      }
      return habit;
    });

    setHabits(updatedHabits);
    localStorage.setItem("meusHabitos", JSON.stringify(updatedHabits));
  };

  const deleteHabit = (habitId) => {
    if (window.confirm("Tem certeza que deseja excluir este hábito?")) {
      const updatedHabits = habits.filter(h => h.id !== habitId);
      setHabits(updatedHabits);
      localStorage.setItem("meusHabitos", JSON.stringify(updatedHabits));
    }
  };

  const getStreakMessage = (streak) => {
    if (streak === 0) return "Comece hoje!";
    if (streak < 7) return `${streak} dias seguidos!`;
    if (streak < 30) return `${streak} dias! Continue!`;
    return `${streak} dias! Impressionante! 🎉`;
  };

  const frequencyOptions = [
    { value: "daily", label: "Diário", icon: "📅" },
    { value: "weekly", label: "Semanal", icon: "📆" },
    { value: "monthly", label: "Mensal", icon: "🗓️" }
  ];

  const iconOptions = [
    "🏃", "📚", "💧", "🍎", "💪", "🧘", "🎯", "✍️", "🎨", "🎵",
    "🧹", "💰", "🌱", "☀️", "🌙", "⭐", "❤️", "🧠", "💤", "🚀"
  ];

  return (
    <div className={styles["habitos-container"]}>
      <header className={styles["habitos-header"]}>
        <div>
          <h1 className={styles["page-title"]}>Meus Hábitos</h1>
          <p className={styles["page-subtitle"]}>
            Construa rotinas positivas todos os dias
          </p>
        </div>
        <div className={styles["header-actions"]}>
          <button 
            className={styles["add-habit-btn"]}
            onClick={() => setIsAdding(!isAdding)}
          >
            {isAdding ? "Cancelar" : "+ Novo Hábito"}
          </button>
        </div>
      </header>

      {/* Formulário para adicionar hábito */}
      {isAdding && (
        <div className={styles["add-habit-form"]}>
          <div className={styles["form-header"]}>
            <h3>Criar Novo Hábito</h3>
          </div>
          
          <div className={styles["form-grid"]}>
            <div className={styles["form-group"]}>
              <label>Nome do Hábito *</label>
              <input
                type="text"
                value={newHabit.name}
                onChange={(e) => setNewHabit({...newHabit, name: e.target.value})}
                placeholder="Ex: Beber água, Exercício, Leitura..."
                maxLength={50}
              />
            </div>

            <div className={styles["form-group"]}>
              <label>Descrição (opcional)</label>
              <textarea
                value={newHabit.description}
                onChange={(e) => setNewHabit({...newHabit, description: e.target.value})}
                placeholder="Por que este hábito é importante para você?"
                rows={2}
              />
            </div>

            <div className={styles["form-group"]}>
              <label>Frequência</label>
              <div className={styles["frequency-options"]}>
                {frequencyOptions.map(option => (
                  <button
                    key={option.value}
                    type="button"
                    className={`${styles["frequency-btn"]} ${newHabit.frequency === option.value ? styles["active"] : ""}`}
                    onClick={() => setNewHabit({...newHabit, frequency: option.value})}
                  >
                    {option.icon} {option.label}
                  </button>
                ))}
              </div>
            </div>

            <div className={styles["form-group"]}>
              <label>Meta (dias)</label>
              <div className={styles["goal-slider"]}>
                <input
                  type="range"
                  min="7"
                  max="365"
                  value={newHabit.goal}
                  onChange={(e) => setNewHabit({...newHabit, goal: parseInt(e.target.value)})}
                />
                <span className={styles["goal-value"]}>{newHabit.goal} dias</span>
              </div>
            </div>

            <div className={styles["form-group"]}>
              <label>Ícone</label>
              <div className={styles["icon-picker"]}>
                {iconOptions.slice(0, 10).map(icon => (
                  <button
                    key={icon}
                    type="button"
                    className={`${styles["icon-option"]} ${newHabit.icon === icon ? styles["selected"] : ""}`}
                    onClick={() => setNewHabit({...newHabit, icon})}
                  >
                    {icon}
                  </button>
                ))}
              </div>
            </div>

            <div className={styles["form-group"]}>
              <label>Cor</label>
              <div className={styles["color-picker"]}>
                {["#8b5cf6", "#ec4899", "#3b82f6", "#10b981", "#f59e0b"].map(color => (
                  <button
                    key={color}
                    type="button"
                    className={`${styles["color-option"]} ${newHabit.color === color ? styles["selected"] : ""}`}
                    style={{ backgroundColor: color }}
                    onClick={() => setNewHabit({...newHabit, color})}
                  />
                ))}
              </div>
            </div>
          </div>

          <div className={styles["form-actions"]}>
            <button 
              className={styles["cancel-btn"]}
              onClick={() => setIsAdding(false)}
            >
              Cancelar
            </button>
            <button 
              className={styles["save-btn"]}
              onClick={handleAddHabit}
            >
              Criar Hábito
            </button>
          </div>
        </div>
      )}

      {/* Estatísticas */}
      <section className={styles["habits-stats"]}>
        <div className={styles["stat-card"]}>
          <div className={styles["stat-icon"]}>📊</div>
          <div className={styles["stat-content"]}>
            <h3>{stats.totalHabits}</h3>
            <p>Hábitos Totais</p>
          </div>
        </div>

        <div className={styles["stat-card"]}>
          <div className={styles["stat-icon"]}>⚡</div>
          <div className={styles["stat-content"]}>
            <h3>{stats.activeHabits}</h3>
            <p>Hábitos Ativos</p>
          </div>
        </div>

        <div className={styles["stat-card"]}>
          <div className={styles["stat-icon"]}>✅</div>
          <div className={styles["stat-content"]}>
            <h3>{stats.completedToday}</h3>
            <p>Concluídos Hoje</p>
          </div>
        </div>

        <div className={styles["stat-card"]}>
          <div className={styles["stat-icon"]}>🔥</div>
          <div className={styles["stat-content"]}>
            <h3>{stats.avgStreak}</h3>
            <p>Sequência Média</p>
          </div>
        </div>
      </section>

      {/* Lista de Hábitos */}
      <section className={styles["habits-list"]}>
        {habits.length === 0 ? (
          <div className={styles["empty-habits"]}>
            <div className={styles["empty-icon"]}>📝</div>
            <h3>Nenhum hábito cadastrado</h3>
            <p>Crie seu primeiro hábito para começar a construir uma rotina positiva!</p>
            <button 
              className={styles["start-habit-btn"]}
              onClick={() => setIsAdding(true)}
            >
              Criar Primeiro Hábito
            </button>
          </div>
        ) : (
          <>
            <div className={styles["habits-grid"]}>
              {habits.map(habit => {
                const today = new Date().toDateString();
                const isCompleted = habit.completedDates?.includes(today);
                const completionRate = habit.totalCompletions 
                  ? Math.round((habit.totalCompletions / habit.goal) * 100) 
                  : 0;

                return (
                  <div 
                    key={habit.id} 
                    className={styles["habit-card"]}
                    style={{ 
                      borderLeft: `4px solid ${habit.color}`,
                      backgroundColor: `${habit.color}10`
                    }}
                  >
                    <div className={styles["habit-header"]}>
                      <div className={styles["habit-icon"]}>
                        {habit.icon}
                      </div>
                      <div className={styles["habit-info"]}>
                        <h3 className={styles["habit-name"]}>{habit.name}</h3>
                        <p className={styles["habit-description"]}>
                          {habit.description || "Sem descrição"}
                        </p>
                        <div className={styles["habit-meta"]}>
                          <span className={styles["habit-frequency"]}>
                            {habit.frequency === "daily" ? "Diário" : 
                             habit.frequency === "weekly" ? "Semanal" : "Mensal"}
                          </span>
                          <span className={styles["habit-goal"]}>
                            Meta: {habit.goal} dias
                          </span>
                        </div>
                      </div>
                      <button
                        className={styles["delete-habit-btn"]}
                        onClick={() => deleteHabit(habit.id)}
                        aria-label="Excluir hábito"
                      >
                        ✕
                      </button>
                    </div>

                    <div className={styles["habit-progress"]}>
                      <div className={styles["progress-info"]}>
                        <span>Progresso: {completionRate}%</span>
                        <span>{habit.totalCompletions || 0}/{habit.goal} dias</span>
                      </div>
                      <div className={styles["progress-bar"]}>
                        <div 
                          className={styles["progress-fill"]}
                          style={{ 
                            width: `${completionRate}%`,
                            backgroundColor: habit.color
                          }}
                        />
                      </div>
                    </div>

                    <div className={styles["habit-streak"]}>
                      <div className={styles["streak-info"]}>
                        <div className={styles["current-streak"]}>
                          <span className={styles["streak-label"]}>Sequência Atual</span>
                          <span className={styles["streak-value"]}>
                            🔥 {habit.currentStreak || 0} dias
                          </span>
                        </div>
                        <div className={styles["best-streak"]}>
                          <span className={styles["streak-label"]}>Melhor Sequência</span>
                          <span className={styles["streak-value"]}>
                            ⭐ {habit.bestStreak || 0} dias
                          </span>
                        </div>
                      </div>
                      <p className={styles["streak-message"]}>
                        {getStreakMessage(habit.currentStreak || 0)}
                      </p>
                    </div>

                    <div className={styles["habit-actions"]}>
                      <button
                        className={`${styles["complete-btn"]} ${isCompleted ? styles["completed"] : ""}`}
                        onClick={() => toggleHabitCompletion(habit.id)}
                        style={{ backgroundColor: isCompleted ? habit.color : "transparent" }}
                      >
                        {isCompleted ? "✅ Concluído Hoje" : "Marcar como Feito"}
                      </button>
                      <div className={styles["completion-date"]}>
                        {habit.lastUpdated && (
                          <span>Última atualização: {
                            new Date(habit.lastUpdated).toLocaleDateString()
                          }</span>
                        )}
                      </div>
                    </div>
                  </div>
                );
              })}
            </div>

            {/* Calendário de hábitos (visual simples) */}
            <div className={styles["habits-calendar"]}>
              <h3>Histórico Recente</h3>
              <div className={styles["calendar-grid"]}>
                {Array.from({ length: 7 }).map((_, index) => {
                  const date = new Date();
                  date.setDate(date.getDate() - (6 - index));
                  const dateStr = date.toDateString();
                  const dayName = date.toLocaleDateString('pt-BR', { weekday: 'short' });
                  
                  const habitsCompleted = habits.filter(h => 
                    h.completedDates?.includes(dateStr)
                  ).length;

                  return (
                    <div key={index} className={styles["calendar-day"]}>
                      <div className={styles["day-name"]}>{dayName}</div>
                      <div className={styles["day-date"]}>{date.getDate()}</div>
                      <div className={styles["day-completions"]}>
                        {habitsCompleted > 0 ? `✅ ${habitsCompleted}` : "—"}
                      </div>
                    </div>
                  );
                })}
              </div>
            </div>
          </>
        )}
      </section>
    </div>
  );
}