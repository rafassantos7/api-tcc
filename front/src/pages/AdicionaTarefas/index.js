import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import { Button } from '../../components/UI/Button';
import { Input } from '../../components/UI/Input';
import { Label } from '../../components/UI/Label';
import { Textarea } from '../../components/UI/Textarea';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '../../components/UI/Select';
import { ArrowLeft, Save, Sparkles } from "lucide-react";
import { useToast } from '../../hooks/use-toast';
import './styles.css';

export default function AddTask() {
  const navigate = useNavigate();
  const { toast } = useToast();
  const [taskData, setTaskData] = useState({
    title: "",
    description: "",
    progress: 0,
    subtasks: 0,
    type: "",
    category: "",
  });

  const handleChange = (field, value) => {
    setTaskData({ ...taskData, [field]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!taskData.title || !taskData.type || !taskData.category) {
      toast({
        title: "Campos obrigatórios",
        description: "Preencha título, tipo e categoria.",
        variant: "destructive",
      });
      return;
    }

    toast({
      title: "Tarefa criada! 🎉",
      description: "Você ganhou +50 XP!",
    });

    setTimeout(() => navigate("/tarefas"), 1000);
  };

  return (
    <div className="addtask-container">
      {/* Header */}
      <motion.div initial={{ opacity: 0, y: -20 }} animate={{ opacity: 1, y: 0 }} className="addtask-header">
        <Button variant="ghost" onClick={() => navigate("/tarefas")} className="mb-4 btn-secondary">
          <ArrowLeft size={20} className="mr-2" />
          Voltar
        </Button>

        <div className="flex items-center gap-3 mb-2">
          <div className="header-icon">
            <Sparkles size={24} />
          </div>
          <div>
            <h1 className="text-4xl font-bold text-foreground">Criar Nova Tarefa</h1>
            <p className="text-muted-foreground">Defina sua próxima conquista!</p>
          </div>
        </div>
      </motion.div>

      {/* Form */}
      <motion.div initial={{ opacity: 0, y: 20 }} animate={{ opacity: 1, y: 0 }} transition={{ delay: 0.1 }}>
        <form onSubmit={handleSubmit} className="max-w-3xl">
          <div className="form-card space-y-6 hover-lift">
            {/* Title */}
            <div className="space-y-2">
              <Label htmlFor="title" className="text-base font-semibold">Título da Tarefa *</Label>
              <Input
                id="title"
                placeholder="Ex: Estudar React Hooks"
                value={taskData.title}
                onChange={(e) => handleChange("title", e.target.value)}
                className="input-field text-lg"
                required
              />
            </div>

            {/* Description */}
            <div className="space-y-2">
              <Label htmlFor="description" className="text-base font-semibold">Descrição</Label>
              <Textarea
                id="description"
                placeholder="Descreva os detalhes da sua tarefa..."
                value={taskData.description}
                onChange={(e) => handleChange("description", e.target.value)}
                rows={4}
                className="textarea-field resize-none"
              />
            </div>

            {/* Type and Category */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div className="space-y-2">
                <Label htmlFor="type" className="text-base font-semibold">Tipo da Tarefa *</Label>
                <Select value={taskData.type} onValueChange={(value) => handleChange("type", value)}>
                  <SelectTrigger id="type" className="select-field">
                    <SelectValue placeholder="Selecione o tipo..." />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="progresso">Progresso</SelectItem>
                    <SelectItem value="rotina">Rotina</SelectItem>
                    <SelectItem value="outros">Outros</SelectItem>
                  </SelectContent>
                </Select>
              </div>

              <div className="space-y-2">
                <Label htmlFor="category" className="text-base font-semibold">Categoria *</Label>
                <Select value={taskData.category} onValueChange={(value) => handleChange("category", value)}>
                  <SelectTrigger id="category" className="select-field">
                    <SelectValue placeholder="Selecione a categoria..." />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="treino">Treino</SelectItem>
                    <SelectItem value="livro">Livro / Leitura</SelectItem>
                    <SelectItem value="estudo">Estudo</SelectItem>
                    <SelectItem value="trabalho">Trabalho</SelectItem>
                    <SelectItem value="saude">Saúde</SelectItem>
                    <SelectItem value="habito">Hábito</SelectItem>
                    <SelectItem value="projeto"> Projeto</SelectItem>
                    <SelectItem value="pessoal">Pessoal</SelectItem>
                  </SelectContent>
                </Select>
              </div>
            </div>

            {/* Progress and Subtasks */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div className="space-y-2">
                <Label htmlFor="progress" className="text-base font-semibold">Progresso Inicial (%)</Label>
                <Input
                  id="progress"
                  type="number"
                  min="0"
                  max="100"
                  value={taskData.progress}
                  onChange={(e) => handleChange("progress", parseInt(e.target.value) || 0)}
                  className="input-field"
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="subtasks" className="text-base font-semibold">Número de Subtarefas</Label>
                <Input
                  id="subtasks"
                  type="number"
                  min="0"
                  value={taskData.subtasks}
                  onChange={(e) => handleChange("subtasks", parseInt(e.target.value) || 0)}
                  className="input-field"
                />
              </div>
            </div>

            {/* Reward Preview */}
            <motion.div initial={{ opacity: 0, scale: 0.95 }} animate={{ opacity: 1, scale: 1 }} transition={{ delay: 0.3 }} className="reward-card">
              <div>
                <p className="text-sm text-muted-foreground">Recompensa ao completar</p>
                <p className="text-lg font-bold text-primary">+50 XP</p>
              </div>
              <div className="icon">🎯</div>
            </motion.div>

            {/* Actions */}
            <div className="flex gap-4 pt-4 flex-col md:flex-row">
              <Button type="submit" size="lg" className="btn-primary flex-1">
                <Save size={20} className="mr-2" />
                Salvar Tarefa
              </Button>
              <Button type="button" variant="outline" size="lg" onClick={() => navigate("/tarefas")} className="btn-secondary flex-1">
                Cancelar
              </Button>
            </div>
          </div>
        </form>
      </motion.div>
    </div>
  );
}
