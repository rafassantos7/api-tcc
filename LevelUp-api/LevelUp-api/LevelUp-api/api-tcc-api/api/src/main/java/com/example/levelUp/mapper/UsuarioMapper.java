package main.java.com.example.levelUp.mapper;


import com.example.levelUp.dto.UsuarioDTO;
import com.example.levelUp.dto.UsuarioResponseDTO;
import com.example.levelUp.model.Usuario;

public class UsuarioMapper {

    // DTO → Entidade (para criação)
    public static Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setSenha(dto.getSenha());
        usuario.setDataNascimento(dto.getDataNascimento());
        return usuario;
    }

    // Atualização: mescla dados do DTO com o usuário existente
    public static void updateEntity(Usuario usuario, UsuarioDTO dto) {

        if (dto.getNome() != null) {
            usuario.setNome(dto.getNome());
        }

        if (dto.getEmail() != null) {
            usuario.setEmail(dto.getEmail());
        }

        if (dto.getTelefone() != null) {
            usuario.setTelefone(dto.getTelefone());
        }

        if (dto.getSenha() != null) {
            usuario.setSenha(dto.getSenha());
        }

        if (dto.getDataNascimento() != null) {
            usuario.setDataNascimento(dto.getDataNascimento());
        }
    }

    // Entidade → ResponseDTO
    public static UsuarioResponseDTO toResponse(Usuario usuario) {
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getTelefone(),
            usuario.getDataNascimento()
        );
    }
}
