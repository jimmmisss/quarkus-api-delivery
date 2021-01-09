package com.github.jimmmisss.rango.cadastro.dto.input;

import com.github.jimmmisss.rango.cadastro.entity.Restaurante;
import com.github.jimmmisss.rango.cadastro.infra.DTO;
import com.github.jimmmisss.rango.cadastro.infra.ValidDTO;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ValidDTO
public class RestauranteInput implements DTO {

    public String proprietario;

    @NotNull
    @Pattern(regexp = "[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}\\/[0-9]{4}\\-[0-9]{2}")
    public String cnpj;

    @Size(min = 3, max = 60)
    public String nomeFantasia;
    public LocalizacaoInput localizacao;

    @Override
    public boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (Restaurante.find("cnpj", cnpj).count() > 0) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("CNPJ já cadastrado")
                .addPropertyNode("cnpj")
                .addConstraintViolation();
            return false;
        }
        return true;
    }
}
