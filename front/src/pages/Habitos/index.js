import React, { useState, useEffect } from "react";
import AdicionarHabito from "./AdicionarHabito";
import ListaHabitos from "./ListaHabitos";
import styles from "./styles.module.css";

export default function Habitos() {
  const [habits, setHabits] = useState(() => {
    const salvos = localStorage.getItem("meusHabitos");
    return salvos ? JSON.parse(salvos) : [];
  });

  const [showForm, setShowForm] = useState(false);

  const handleAddHabit = (newHabit) => {
    const updatedHabits = [...habits, newHabit];
    setHabits(updatedHabits);
    localStorage.setItem("meusHabitos", JSON.stringify(updatedHabits));
    setShowForm(false);
  };

  const handleDeleteHabit = (id) => {
    if (window.confirm("Tem certeza que deseja excluir este hábito?")) {
      const updatedHabits = habits.filter(h => h.id !== id);
      setHabits(updatedHabits);
      localStorage.setItem("meusHabitos", JSON.stringify(updatedHabits));
    }
  };

  const handleToggleComplete = (id) => {
    const today = new Date().toISOString().split('T')[0];
    const updatedHabits = habits.map(habit => {
      if (habit.id === id) {
        const completedDates = habit.completedDates || [];
        const isCompleted = completedDates.includes(today);
        
        return {
          ...habit,
          completedDates: isCompleted 
            ? completedDates.filter(date => date !== today)
            : [...completedDates, today],
          currentStreak: isCompleted 
            ? Math.max(0, habit.currentStreak - 1)
            : (habit.currentStreak || 0) + 1
        };
      }
      return habit;
    });
    
    setHabits(updatedHabits);
    localStorage.setItem("meusHabitos", JSON.stringify(updatedHabits));
  };

  // Estatísticas
  const stats = {
    total: habits.length,
    completedToday: habits.filter(h => {
      const today = new Date().toISOString().split('T')[0];
      return h.completedDates?.includes(today);
    }).length,
    bestStreak: habits.length > 0 
      ? Math.max(...habits.map(h => h.currentStreak || 0))
      : 0,
  };

  return (
    <div className={styles.container}>
      <header className={styles.header}>
        <h1 className={styles.title}>Meus Hábitos</h1>
        <p className={styles.subtitle}>Construa rotinas positivas todos os dias</p>
        <button 
          className={styles.addButton}
          onClick={() => setShowForm(!showForm)}
        >
          {showForm ? "Cancelar" : "+ Novo Hábito"}
        </button>
      </header>

      {/* Formulário para adicionar hábito */}
      {showForm && (
        <AdicionarHabito onAdd={handleAddHabit} />
      )}

      {/* Estatísticas */}
      <div className={styles.stats}>
        <div className={styles.statCard}>
          <span className={styles.statNumber}>{stats.total}</span>
          <span className={styles.statLabel}>Hábitos</span>
        </div>
        <div className={styles.statCard}>
          <span className={styles.statNumber}>{stats.completedToday}</span>
          <span className={styles.statLabel}>Hoje</span>
        </div>
        <div className={styles.statCard}>
          <span className={styles.statNumber}>{stats.bestStreak}</span>
          <span className={styles.statLabel}>Melhor sequência</span>
        </div>
      </div>

      {/* Lista de hábitos */}
      <ListaHabitos 
        habits={habits}
        onDelete={handleDeleteHabit}
        onToggleComplete={handleToggleComplete}
      />

      {/* Mensagem quando não há hábitos */}
      {habits.length === 0 && !showForm && (
        <div className={styles.emptyState}>
          <p>Nenhum hábito cadastrado ainda.</p>
          <button 
            className={styles.startButton}
            onClick={() => setShowForm(true)}
          >
            Criar meu primeiro hábito
          </button>
        </div>
      )}
    </div>
  );
}