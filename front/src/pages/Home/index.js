import React from 'react';
import { Link } from 'react-router-dom';
import './styles.css';

function Home() {
  return (
    <div className="home-container">
      <header className="home-header">
        <div className="logo">LvlUp</div>
        <nav className="home-nav">
          <Link to="/login" className="nav-link">Login</Link>
          <Link to="/cadastro" className="nav-link">Cadastro</Link>
        </nav>
      </header>
      <main className="home-main">
        <h1>Bem-vindo ao LvlUp!</h1>
        <div className="home-actions">
          <Link to="/tarefas" className="action-btn">Criar Tarefas</Link>
          <Link to="/perfil" className="action-btn">Abrir Perfil</Link>
        </div>
      </main>
    </div>
  );
}

export default Home;
