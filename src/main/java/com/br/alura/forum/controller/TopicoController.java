package com.br.alura.forum.controller;

import com.br.alura.forum.domain.curso.CursoRepository;
import com.br.alura.forum.domain.curso.DadosDetalhamentoCurso;
import com.br.alura.forum.domain.topico.*;
import com.br.alura.forum.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder){

        if(topicoRepository.findByTituloAndMensagem(dados.titulo(), dados.mensagem()) == null){

            var autor = usuarioRepository.findByEmail(dados.autor().email());

            var curso = cursoRepository.findByNomeAndCategoria(dados.curso().nome(), dados.curso().categoria());

            var topico = new Topico(dados);
            topico.setAutor(autor);
            topico.setCurso(curso);
            topicoRepository.save(topico);

            var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

            return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
        }
        return ResponseEntity.badRequest().body("Tópicos duplicados!");
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopicos>> listar(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC, sort = {"dataCriacao"}) Pageable paginacao) {
        var topicos = topicoRepository.findAllByAtivoTrue(paginacao);
        return ResponseEntity.ok(topicos.map(DadosListagemTopicos::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id){
        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody DadosAtualizacaoTopico dados){
        var topico = topicoRepository.getReferenceById(dados.id());
        if((topicoRepository.findByTituloAndMensagem(dados.titulo(), dados.mensagem()) == null) ||
        (topicoRepository.findByTituloAndMensagem(dados.titulo(), dados.mensagem())) == topico){
            topico.atualiza(dados);
            return ResponseEntity.ok().body(new DadosDetalhamentoTopico(topico));
        }
        return ResponseEntity.badRequest().body("Tópicos replicados!");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){
        var topico = topicoRepository.getReferenceById(id);
        topico.exclui();
        return ResponseEntity.noContent().build();
    }

}
