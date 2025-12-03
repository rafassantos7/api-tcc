import React, { useState } from "react";
import "./styles.css";
import { useNavigate } from "react-router-dom";

function Metas(){
  return (
    <div>Metas</div>
  )
}

export default Metasimport { useEffect, useState } from "react";
import "./styles.css";

export default function Metas() {
  const [metas, setMetas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [erro, setErro] = useState(false);

  useEffect(() => {
    async function carregarMetas() {
      try {
        const resposta = await fetch("http://localhost:8080/user/metas");

        if (!resposta.ok) {
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
  }, []);

  function getStatusIcon(status) {
    if (status === "CONCLUIDA") return "✔️";
    if (status === "ANDAMENTO") return "⏳";
    return "🔔";
  }

  return (
    <div className="metas-container">
      <h1 className="metas-title">Minhas Metas</h1>

      {loading && (
        <div className="loading-spinner"></div>
      )}

      {erro && (
        <p className="erro-msg">Erro ao carregar metas do servidor.</p>
      )}

      {!loading && !erro && metas.length === 0 && (
        <p className="vazio-msg">Nenhuma meta encontrada.</p>
      )}

      <div className="lista-container">
        {metas.map((meta) => (
          <div key={meta.id} className="meta-card">
            <div className="meta-topo">
              <h2>{meta.titulo}</h2>
              <span className={`status-badge ${meta.status.toLowerCase()}`}>
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
