package com.br.alura.forum.controller;

import com.br.alura.forum.domain.curso.DadosDetalhamentoCurso;
import com.br.alura.forum.domain.usuario.DadosAutenticacao;
import com.br.alura.forum.domain.usuario.Usuario;
import com.br.alura.forum.infra.security.DadosTokenJWT;
import com.br.alura.forum.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticação", description = "Autentica um usuário")
public class AutenticacaoController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping
    @Operation(summary = "Realizar login de usuário", description = "Realiza o login de usuário com email e senha")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DadosTokenJWT.class)))
    @ApiResponse(responseCode = "500", description = "Erro de autenticação!", content = @Content)
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){

        UsernamePasswordAuthenticationToken dadosLogin = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        try{
            Authentication authentication = authenticationManager.authenticate(dadosLogin);

            String tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
        }catch (AuthenticationException exception){
            throw new RuntimeException("Erro de autenticação", exception);
        }

    }
}
