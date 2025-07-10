package com.example.hotelbao.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cliente")
public class Cliente extends RepresentationModel<Cliente> {

    @Schema(description = "ID único do cliente gerado pelo banco.", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nome completo do cliente.", example = "João da Silva")
    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @Schema(description = "Endereço de e-mail do cliente (deve ser único).", example = "joao.silva@example.com")
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Schema(description = "Senha de acesso do cliente. Será criptografada no banco.", example = "senha123", accessMode = Schema.AccessMode.WRITE_ONLY)
    @Column(nullable = false)
    private String senha;

    @Schema(description = "Nome de usuário para login (deve ser único).", example = "joao.silva")
    @NotBlank(message = "Login é obrigatório")
    @Column(nullable = false, unique = true, length = 50)
    private String login;

    @Schema(description = "Número de telefone do cliente.", example = "37999887766")
    @NotBlank(message = "Telefone é obrigatório")
    @Column(nullable = false, length = 20)
    private String telefone;

    @Schema(description = "Endereço residencial completo do cliente.", example = "Rua das Flores, 123, Centro, Formiga-MG")
    @NotBlank(message = "Endereço é obrigatório")
    @Column(nullable = false, length = 255)
    private String endereco;

    @PrePersist
    public void definirSenhaPadrao() {
        if (this.senha == null || this.senha.isBlank()) {
            this.senha = this.telefone;
        }
    }
}