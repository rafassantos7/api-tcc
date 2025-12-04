import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import PaginaCadastro from './pages/Cadastro';
import PaginaListaUsuarios from './pages/Lista';
import PaginaBemVindo from './pages/Bemvindo';
import PaginaTarefa from './pages/Tarefas';
import PaginaLogin from './pages/Login'; // Corrigido: removida importação duplicada
import AddTask from './pages/AdicionaTarefas'; // Corrigido: mantido apenas um
import ListaMetas from './pages/ListaMetas';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/bemvindo" replace />} />
        <Route path="/bemvindo" element={<PaginaBemVindo />} />
        <Route path="/metas" element={<ListaMetas />} /> 
        <Route path="/cadastro" element={<PaginaCadastro />} />
        <Route path="/tarefas" element={<PaginaTarefa />} />
        <Route path="/add-task" element={<AddTask />} />
        <Route path="/usuarios" element={<PaginaListaUsuarios />} />
        <Route path="/editar-meta/:id" element={<AddTask />} />
        <Route path="/login" element={<PaginaLogin />} />
      </Routes>
    </Router>
  );
}

export default App;