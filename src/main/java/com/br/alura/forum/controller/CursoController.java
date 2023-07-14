package com.br.alura.forum.controller;

import com.br.alura.forum.domain.curso.*;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
@Tag(name = "Cursos", description = "CRUD completo dos Cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;
    @PostMapping
    @Transactional
    @Operation(summary = "Cadastrar um novo curso", description = "Adiciona um novo curso ao forum", security = @SecurityRequirement(name = "bearer-key"))
    @ApiResponse(responseCode = "201", description = "Curso cadastrado com sucesso!", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DadosDetalhamentoCurso.class))})
    @ApiResponse(responseCode = "400", description = "Curso duplicado!", content = @Content)

    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroCurso dadosCadastroCurso, UriComponentsBuilder uriBuilder){
        if (cursoRepository.findByNomeAndCategoria(dadosCadastroCurso.nome(), dadosCadastroCurso.categoria()) == null){
            var curso = new Curso(dadosCadastroCurso);
            cursoRepository.save(curso);
            var uri = uriBuilder.path("cursos/{id}").buildAndExpand(curso.getId()).toUri();

            return ResponseEntity.created(uri).body(new DadosDetalhamentoCurso(curso));
        }
        return ResponseEntity.badRequest().body("Curso duplicado!");
    }

    @GetMapping
    @Operation(summary = "Listar cursos cadastrados", description = "Retorna a listagem de cursos com atributos")
    @ApiResponse(responseCode = "200", description = "Cursos listados com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DadosListagemCursos.class)))
    public ResponseEntity<Page<DadosListagemCursos>> listar(@RequestParam(required = false) String categoria,
            @PageableDefault(page = 0, size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao){
        Page<Curso> paginaCursos;

        if(categoria != null){
            paginaCursos = cursoRepository.findAllByAtivoTrueAndCategoria(categoria, paginacao);
        }
        else{
            paginaCursos = cursoRepository.findAllByAtivoTrue(paginacao);
        }
        return ResponseEntity.ok(paginaCursos.map(DadosListagemCursos::new));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar um curso", description = "Retorna o detalhamento de um curso com atributos", security = @SecurityRequirement(name = "bearer-key"))
    @ApiResponse(responseCode = "200", description = "Cursos listados com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DadosDetalhamentoCurso.class)))
    @ApiResponse(responseCode = "404", description = "Curso não encontrado", content = @Content)
    public ResponseEntity<DadosDetalhamentoCurso> detalhar(@PathVariable Long id){
        var curso = cursoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso));
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Atualizar um curso", description = "Atualiza as informações de um curso existente", security = @SecurityRequirement(name = "bearer-key"))
    @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DadosDetalhamentoCurso.class)))
    @ApiResponse(responseCode = "404", description = "Curso não encontrado", content = @Content)
    public ResponseEntity<DadosDetalhamentoCurso> atualizar(@RequestBody @Valid DadosAtualizacaoCurso dadosAtualizacaoCurso){
        var curso = cursoRepository.getReferenceById(dadosAtualizacaoCurso.id());
        curso.atualizar(dadosAtualizacaoCurso);
        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Excluir um curso", description = "Exclui um curso existente", security = @SecurityRequirement(name = "bearer-key"))
    @ApiResponse(responseCode = "204", content = @Content)
    @ApiResponse(responseCode = "404", description = "Curso não encontrado", content = @Content)
    public ResponseEntity deletar(@PathVariable Long id){
        var curso = cursoRepository.getReferenceById(id);
        curso.excluir();
        return ResponseEntity.noContent().build();
    }

}
