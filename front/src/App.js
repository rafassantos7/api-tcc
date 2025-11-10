import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import PaginaCadastro from './pages/Cadastro';
import PaginaListaUsuarios from './pages/Lista';
import PaginaBemVindo from './components/Bemvindo';
import PaginaTarefa from './pages/Tarefas';

import './App.css'; // Para estilos globais, se necessário

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/cadastro" element={<PaginaCadastro />} />
        <Route path="/usuarios" element={<PaginaListaUsuarios />} />
        <Route path="/tarefas" element={<PaginaTarefa />} />
        <Route path="/bemvindo" element={<PaginaBemVindo />} />
        <Route path="/" element={<Navigate to="/bemvindo" replace />} />

      </Routes>
    </Router>
  );
}

export default App;