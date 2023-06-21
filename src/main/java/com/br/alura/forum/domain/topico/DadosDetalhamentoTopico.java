package com.br.alura.forum.domain.topico;

import com.br.alura.forum.domain.curso.Curso;
import com.br.alura.forum.domain.curso.DadosDetalhamentoCurso;
import com.br.alura.forum.domain.curso.DadosListagemCursos;
import com.br.alura.forum.domain.usuario.DadosDetalhamentoUsuario;
import com.br.alura.forum.domain.usuario.DadosListagemUsuarios;
import com.br.alura.forum.domain.usuario.Usuario;
import com.br.alura.forum.modelo.StatusTopico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        DadosListagemUsuarios autor,
        DadosListagemCursos curso,
        boolean ativo) {
    public DadosDetalhamentoTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getStatus(),
                new DadosListagemUsuarios(topico.getAutor()), new DadosListagemCursos(topico.getCurso()), topico.isAtivo());
    }
}
