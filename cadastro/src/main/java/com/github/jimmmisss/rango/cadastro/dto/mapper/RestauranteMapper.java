package com.github.jimmmisss.rango.cadastro.dto.mapper;

import com.github.jimmmisss.rango.cadastro.entity.Restaurante;
import com.github.jimmmisss.rango.cadastro.dto.input.RestauranteInput;
import com.github.jimmmisss.rango.cadastro.dto.output.RestauranteOutput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {

    @Mapping(target = "nome", source = "nomeFantasia")
    Restaurante toRestaurante(RestauranteInput restauranteInput);

    @Mapping(target = "nome", source = "nomeFantasia")
    Restaurante toRestaurante(RestauranteInput restauranteInput, @MappingTarget Restaurante restaurante);

    RestauranteOutput toRestauranteOutput(Restaurante restaurante);

}
