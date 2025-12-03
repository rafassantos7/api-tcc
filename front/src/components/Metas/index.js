import React, { useState, useEffect } from "react";
// O useNavigate foi mantido, mas você pode removê-lo se não for usar.
import { useNavigate } from "react-router-dom"; 
import "./styles.css";
import api from '../../api';

// A definição do componente deve ser única.
// A função Metas() inicial e o export final quebrado foram removidos.

export default function Metas() {
  // O hook useEffect precisa ser importado do 'react'
  const [metas, setMetas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [erro, setErro] = useState(false);
  
  // Se fosse usar o useNavigate, a linha seria: const navigate = useNavigate();

  useEffect(() => {
    async function carregarMetas() {
      try {
        const resposta = await api.get("/user/metas");
        
        if (!resposta.ok) {
          // Lança um erro se a resposta HTTP não for bem-sucedida (ex: 404, 500)
          throw new Error("Erro ao buscar dados"); 
        }

        const dados = await resposta.json();
        setMetas(dados);
      } catch (error) {
        console.error("Erro ao carregar metas:", error);
        setErro(true);
      } finally {
        setLoading(false);
      }
    }

    carregarMetas();
    // O array vazio de dependências garante que o fetch ocorra apenas uma vez, 
    // após a montagem inicial do componente.
  }, []); 

  function getStatusIcon(status) {
    if (status === "CONCLUIDA") return "✔️";
    if (status === "ANDAMENTO") return "⏳";
    return "🔔"; // Ícone padrão para PENDENTE/Outros
  }
  
  return (
    <div className="metas-container">
      <h1 className="metas-title">Minhas Metas</h1>

      {loading && (
        // Adicionando uma mensagem de carregamento junto ao spinner
        <div className="loading-spinner">Carregando metas...</div> 
      )}

      {erro && (
        <p className="erro-msg">Erro ao carregar metas do servidor.</p>
      )}

      {/* Exibir mensagem de vazio apenas se não estiver carregando, sem erro e a lista estiver vazia */}
      {!loading && !erro && metas.length === 0 && (
        <p className="vazio-msg">Nenhuma meta encontrada.</p>
      )}

      <div className="lista-container">
        {metas.map((meta) => (
          <div key={meta.id} className="meta-card">
            <div className="meta-topo">
              <h2>{meta.titulo}</h2>
              {/* O `meta.status?.toLowerCase() || ''` garante que toLowerCase() só seja chamado 
                  se meta.status existir, evitando possíveis erros. */}
              <span className={`status-badge ${meta.status?.toLowerCase() || ''}`}> 
                {getStatusIcon(meta.status)} {meta.status}
              </span>
            </div>

            <p className="descricao">{meta.descricao}</p>
          </div>
        ))}
      </div>
    </div>
  );
}