package com.bloodbank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@Entity
@Table(name = "donor")
@Validated
public class Donor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Nome é obrigatório")
    @JsonProperty("nome")
    private String name;

    @NotBlank(message = "CPF é obrigatório")
    @Column(unique = true, length = 14)
    @JsonProperty("cpf")
    private String cpf;

    @NotBlank(message = "RG é obrigatório")
    @Column(unique = true, length = 12)
    @JsonProperty("rg")
    private String rg;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Column(name = "birth_date")
    @JsonProperty("data_nasc")
    @Pattern(regexp = "^(\\d{2}\\/\\d{2}\\/\\d{4}|\\d{2}\\\\/\\d{2}\\\\/\\d{4})$", message = "Formato de data inválido. Use 'dd/MM/yyyy' ou 'dd\\/MM\\/yyyy'.")
    private String birthDate;

    @JsonProperty("sexo")
    private String gender;

    @NotBlank(message = "Nome da mãe é obrigatório")
    @JsonProperty("mae")
    private String motherName;

    @JsonProperty("nome_pai")
    private String fatherName;

    @NotBlank(message = "E-mail é obrigatório")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+$",
            message = "E-mail inválido"
    )
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "CEP é obrigatório")
    @JsonProperty("cep")
    private String zipCode;

    @NotBlank(message = "Endereço é obrigatório")
    @JsonProperty("endereco")
    private String address;

    @NotNull(message = "Número do endereço é obrigatório")
    @JsonProperty("numero")
    private Integer number;

    @NotBlank(message = "Bairro é obrigatório")
    @JsonProperty("bairro")
    private String neighborhood;

    @NotBlank(message = "Cidade é obrigatória")
    @JsonProperty("cidade")
    private String city;

    @NotBlank(message = "Estado é obrigatório")
    @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres (ex: SP)")
    @JsonProperty("estado")
    private String state;

    @Pattern(regexp = "\\(\\d{2}\\) \\d{4}-\\d{4}", message = "Telefone fixo inválido")
    @JsonProperty("telefone_fixo")
    private String landlinePhone;

    @NotBlank(message = "Celular é obrigatório")
    @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "Celular inválido")
    @JsonProperty("celular")
    private String mobilePhone;

    @Positive(message = "Peso deve ser maior que zero")
    @JsonProperty("peso")
    private double weight;

    @Positive(message = "Altura deve ser maior que zero")
    @JsonProperty("altura")
    private double height;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type")
    @JsonProperty("tipo_sanguineo")
    private BloodType bloodType;

    @Column(name = "created_at")
    @JsonProperty("data_criacao")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Integer getAge() {
        if (birthDate == null) return null;

        String normalizedDate = birthDate.replace("\\/", "/");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthLocalDate = LocalDate.parse(normalizedDate, formatter);

        return Period.between(birthLocalDate, LocalDate.now()).getYears();
    }
}