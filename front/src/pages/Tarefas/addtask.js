import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./addtask.css"; 

export default function TaskForm() {
  const navigate = useNavigate();
  const { id } = useParams();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  const [type, setType] = useState("normal");

  // Campos automáticos
  const [totalPaginas, setTotalPaginas] = useState("");
  const [paginaAtual, setPaginaAtual] = useState("");

  const [totalAulas, setTotalAulas] = useState("");
  const [aulasConcluidas, setAulasConcluidas] = useState("");

  const [metaValor, setMetaValor] = useState("");
  const [valorAtual, setValorAtual] = useState("");

  const [tarefasTotal, setTarefasTotal] = useState("");
  const [tarefasConcluidas, setTarefasConcluidas] = useState("");

  const [progress, setProgress] = useState(0);

  useEffect(() => {
    if (id) {
      const tarefas = JSON.parse(localStorage.getItem("minhasMetas")) || [];
      const t = tarefas.find((x) => x.id === parseInt(id));

      if (t) {
        setTitle(t.title);
        setDescription(t.description || "");
        setType(t.type || "normal");
        setProgress(t.progress || 0);

        setTotalPaginas(t.totalPaginas || "");
        setPaginaAtual(t.paginaAtual || "");

        setTotalAulas(t.totalAulas || "");
        setAulasConcluidas(t.aulasConcluidas || "");

        setMetaValor(t.metaValor || "");
        setValorAtual(t.valorAtual || "");

        setTarefasTotal(t.tarefasTotal || "");
        setTarefasConcluidas(t.tarefasConcluidas || "");
      }
    }
  }, [id]);

  // CALCULOS AUTOMÁTICOS
  useEffect(() => {
    if (type === "livro" && totalPaginas > 0) {
      setProgress(Math.min(100, Math.round((paginaAtual / totalPaginas) * 100)));
    }

    if (type === "curso" && totalAulas > 0) {
      setProgress(Math.min(100, Math.round((aulasConcluidas / totalAulas) * 100)));
    }

    if (type === "dinheiro" && metaValor > 0) {
      setProgress(Math.min(100, Math.round((valorAtual / metaValor) * 100)));
    }

    if (type === "projeto" && tarefasTotal > 0) {
      setProgress(Math.min(100, Math.round((tarefasConcluidas / tarefasTotal) * 100)));
    }

  }, [
    type,
    totalPaginas, paginaAtual,
    totalAulas, aulasConcluidas,
    metaValor, valorAtual,
    tarefasTotal, tarefasConcluidas
  ]);

  const handleSalvar = (e) => {
    e.preventDefault();

    const tarefas = JSON.parse(localStorage.getItem("minhasMetas")) || [];

    const dados = {
      id: id ? parseInt(id) : Date.now(),
      title,
      description,
      type,
      progress,

      totalPaginas,
      paginaAtual,
      totalAulas,
      aulasConcluidas,
      metaValor,
      valorAtual,
      tarefasTotal,
      tarefasConcluidas,
    };

    let novas;
    if (id) {
      novas = tarefas.map((t) => (t.id === parseInt(id) ? dados : t));
    } else {
      novas = [...tarefas, dados];
    }

    localStorage.setItem("minhasMetas", JSON.stringify(novas));
    navigate("/tarefas");
  };

  return (
    <div className="levelup-container">
      <main className="conteudo" style={{ display: "flex", justifyContent: "center" }}>
        <div className="card-tarefa" style={{ width: "100%", maxWidth: "500px", padding: "2rem" }}>
          
          <h2 style={{ color: "#fff" }}>
            {id ? "Editar Meta" : "Nova Meta"}
          </h2>

          <form onSubmit={handleSalvar} style={{ display: "flex", flexDirection: "column", gap: "1rem" }}>

            <div>
              <label style={{ color: "#aaa" }}>Nome</label>
              <input 
                className="input-pesquisa"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
              />
            </div>

            <div>
              <label style={{ color: "#aaa" }}>Descrição</label>
              <textarea
                className="input-pesquisa"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              />
            </div>

            {/* Tipo da meta */}
            <div>
              <label style={{ color: "#aaa" }}>Tipo</label>
              <select
                className="input-pesquisa"
                value={type}
                onChange={(e) => setType(e.target.value)}
              >
                <option value="normal">Normal</option>
                <option value="livro">Livro</option>
                <option value="curso">Curso</option>
                <option value="dinheiro">Economia / Dinheiro</option>
                <option value="projeto">Projeto (tarefas)</option>
              </select>
            </div>

            {/* --- CAMPOS AUTOMÁTICOS --- */}

            {/* LIVRO */}
            {type === "livro" && (
              <>
                <input placeholder="Total de páginas" type="number" className="input-pesquisa"
                  value={totalPaginas} onChange={(e) => setTotalPaginas(e.target.value)} />
                <input placeholder="Página atual" type="number" className="input-pesquisa"
                  value={paginaAtual} onChange={(e) => setPaginaAtual(e.target.value)} />

                <p style={{ color: "#fff" }}>Progresso: {progress}%</p>
              </>
            )}

            {/* CURSO */}
            {type === "curso" && (
              <>
                <input placeholder="Total de aulas" type="number" className="input-pesquisa"
                  value={totalAulas} onChange={(e) => setTotalAulas(e.target.value)} />

                <input placeholder="Aulas concluídas" type="number" className="input-pesquisa"
                  value={aulasConcluidas} onChange={(e) => setAulasConcluidas(e.target.value)} />

                <p style={{ color: "#fff" }}>Progresso: {progress}%</p>
              </>
            )}

            {/* DINHEIRO */}
            {type === "dinheiro" && (
              <>
                <input placeholder="Meta de valor (R$)" type="number" className="input-pesquisa"
                  value={metaValor} onChange={(e) => setMetaValor(e.target.value)} />

                <input placeholder="Valor atual (R$)" type="number" className="input-pesquisa"
                  value={valorAtual} onChange={(e) => setValorAtual(e.target.value)} />

                <p style={{ color: "#fff" }}>Progresso: {progress}%</p>
              </>
            )}

            {/* PROJETO */}
            {type === "projeto" && (
              <>
                <input placeholder="Total de tarefas" type="number" className="input-pesquisa"
                  value={tarefasTotal} onChange={(e) => setTarefasTotal(e.target.value)} />

                <input placeholder="Tarefas concluídas" type="number" className="input-pesquisa"
                  value={tarefasConcluidas} onChange={(e) => setTarefasConcluidas(e.target.value)} />

                <p style={{ color: "#fff" }}>Progresso: {progress}%</p>
              </>
            )}

            {/* Slider só para metas normais */}
           

            <div style={{ display: "flex", gap: "10px" }}>
              <button type="button" onClick={() => navigate("/tarefas")} className="botao-tarefas-livre">
                Cancelar
              </button>
              <button type="submit" className="botao-nova">Salvar</button>
            </div>

          </form>
        </div>
      </main>
    </div>
  );
}
