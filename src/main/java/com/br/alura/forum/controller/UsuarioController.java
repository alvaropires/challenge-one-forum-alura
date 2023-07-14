package com.br.alura.forum.controller;

import com.br.alura.forum.domain.curso.DadosDetalhamentoCurso;
import com.br.alura.forum.domain.curso.DadosListagemCursos;
import com.br.alura.forum.domain.usuario.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "CRUD completo dos Usuários")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastrar um novo usuário", description = "Adiciona um novo usuário ao forum")
    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso!", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DadosDetalhamentoUsuario.class))})
    @ApiResponse(responseCode = "400", description = "Usuário já cadastrado com este email!", content = @Content)
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario, UriComponentsBuilder uriBuilder){

        if(usuarioRepository.findByEmail(dadosCadastroUsuario.email()) == null){
            var senhaCriptografada = new BCryptPasswordEncoder().encode(dadosCadastroUsuario.senha());
            var usuario = new Usuario(dadosCadastroUsuario);
            usuario.setSenha(senhaCriptografada);
            usuarioRepository.save(usuario);
            var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

            return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
        }
        return ResponseEntity.badRequest().body("Já existe um usuário cadastrado com este email!");
    }

    @GetMapping
    @Operation(summary = "Listar usuarios cadastrados", description = "Retorna a listagem de usuarios com atributos", security = @SecurityRequirement(name = "bearer-key"))
    @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DadosListagemUsuarios.class)))
    public ResponseEntity<Page<DadosListagemUsuarios>> listar(@PageableDefault(page = 0, size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao ){
        var usuarios = usuarioRepository.findAllByAtivoTrue(paginacao);
        return ResponseEntity.ok(usuarios.map(DadosListagemUsuarios::new));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar um usuário", description = "Retorna o detalhamento de um usuário com atributos", security = @SecurityRequirement(name = "bearer-key"))
    @ApiResponse(responseCode = "200", description = "Usuário detalhado com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DadosDetalhamentoUsuario.class)))
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    public ResponseEntity<DadosDetalhamentoUsuario> detalhar(@PathVariable Long id){
        var usuario = usuarioRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Atualizar um usuário", description = "Atualiza as informações de um usuário existente", security = @SecurityRequirement(name = "bearer-key"))
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DadosDetalhamentoUsuario.class)))
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    public ResponseEntity<DadosDetalhamentoUsuario> atualizar(@RequestBody @Valid DadosAtualizacaoUsuario dadosAtualizacaoUsuario){
        var usuario = usuarioRepository.getReferenceById(dadosAtualizacaoUsuario.id());
        usuario.atualizar(dadosAtualizacaoUsuario);

        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Excluir um usuário", description = "Exclui um usuário existente", security = @SecurityRequirement(name = "bearer-key"))
    @ApiResponse(responseCode = "204", content = @Content)
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    public ResponseEntity deletar(@PathVariable Long id){
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.excluir();
        return ResponseEntity.noContent().build();
    }
}
