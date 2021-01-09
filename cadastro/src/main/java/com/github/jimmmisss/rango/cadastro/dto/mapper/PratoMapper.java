package com.github.jimmmisss.rango.cadastro.dto.mapper;

import com.github.jimmmisss.rango.cadastro.dto.input.PratoInput;
import com.github.jimmmisss.rango.cadastro.dto.input.PratoUpdateInput;
import com.github.jimmmisss.rango.cadastro.dto.output.PratoOutput;
import com.github.jimmmisss.rango.cadastro.entity.Prato;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface PratoMapper {

    Prato toPrato(PratoInput pratoInput);
    Prato toPrato(PratoUpdateInput pratoUpdateInput, @MappingTarget Prato prato);
    PratoOutput toPratoOutput(Prato prato);

}
