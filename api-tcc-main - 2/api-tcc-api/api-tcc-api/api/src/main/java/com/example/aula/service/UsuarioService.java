package com.example.aula.service;
import com.example.aula.exception.EmailJaCadastradoException;
import com.example.aula.model.Habitos;
import com.example.aula.model.Metas;
import com.example.aula.model.Usuario;
import com.example.aula.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Service
@Validated
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario salvar(@Valid Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new EmailJaCadastradoException("Usuário já cadastrado.");
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(@Valid Usuario usuario) {
        Usuario usuarioAtualizar = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario não encontrado."));

        usuarioAtualizar.setNome(usuario.getNome());
        usuarioAtualizar.setEmail(usuario.getEmail());
        usuarioAtualizar.setSenha(usuario.getSenha());
        usuarioAtualizar.setTelefone(usuario.getTelefone());


        return usuarioRepository.save(usuarioAtualizar);
    }

    public void excluir(Long id) {
        Usuario usuarioExcluir = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioRepository.deleteById(usuarioExcluir.getId());
    }

    public Usuario adicionarMeta(Long usuarioId, List<Metas> metas) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        usuario.setMetas(metas);
        return usuarioRepository.save(usuario);
    }

    public Usuario adicionarHabito(Long usuarioId, List<Habitos> habitos) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        usuario.setHabitos(habitos);
        return usuarioRepository.save(usuario);
    }
}

