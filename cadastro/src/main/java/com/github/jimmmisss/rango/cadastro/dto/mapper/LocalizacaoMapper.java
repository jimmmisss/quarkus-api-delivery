package com.github.jimmmisss.rango.cadastro.dto.mapper;

import com.github.jimmmisss.rango.cadastro.dto.input.LocalizacaoInput;
import com.github.jimmmisss.rango.cadastro.dto.output.LocalizacaoOutput;
import com.github.jimmmisss.rango.cadastro.entity.Localizacao;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface LocalizacaoMapper {

    Localizacao toLocalizacao(LocalizacaoInput localizacaoInput);

    Localizacao toLocalizacao(LocalizacaoInput localizacaoInput, @MappingTarget Localizacao localizacao);

    LocalizacaoOutput toLocalizacaoOutput(Localizacao localizacao);

}
