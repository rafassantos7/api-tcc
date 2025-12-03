import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import PaginaCadastro from './pages/Cadastro';
import PaginaListaUsuarios from './pages/Lista';
import PaginaBemVindo from './pages/Bemvindo';
import PaginaTarefa from './pages/Tarefas';
import PaginaLogin from './pages/Login';
import ListaMetas from './pages/ListaMetas';
import AddTask from './pages/AdicionaTarefas';
import Metas from './pages/ListaMetas';

import './App.css'; // Para estilos globais, se necessário
import TaskForm from './pages/Tarefas/addtask';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/bemvindo" replace />} />
        <Route path="/bemvindo" element={<PaginaBemVindo />} />
        <Route path="/metas" element={<Metas />} />
        <Route path="/cadastro" element={<PaginaCadastro />} />
        <Route path="/tarefas" element={<PaginaTarefa />} />
        <Route path="/metas" element={<ListaMetas />} />
        <Route path="/add-task" element={<TaskForm />} />
        <Route path="/usuarios" element={<PaginaListaUsuarios />} />
        <Route path="/editar-meta/:id" element={<TaskForm />} /> {/* O :id é a chave */}
        <Route path="/login" element={<PaginaLogin />} />

      </Routes>
    </Router>
  );
}

export default App;