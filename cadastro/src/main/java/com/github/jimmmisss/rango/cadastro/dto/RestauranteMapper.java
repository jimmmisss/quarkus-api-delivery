package com.github.jimmmisss.rango.cadastro.dto;

import com.github.jimmmisss.rango.cadastro.Restaurante;
import com.github.jimmmisss.rango.cadastro.dto.input.RestauranteInput;
import com.github.jimmmisss.rango.cadastro.dto.input.RestauranteUpdateInput;
import com.github.jimmmisss.rango.cadastro.dto.output.RestauranteOutput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {

    @Mapping(target = "nome", source = "nomeFantasia")
    Restaurante toRestaurante(RestauranteInput restauranteInput);

    @Mapping(target = "nome", source = "nomeFantasia")
    Restaurante toRestaurante(RestauranteUpdateInput restauranteUpdateInput, @MappingTarget Restaurante restaurante);

    RestauranteOutput toRestauranteOutput(Restaurante restaurante);

}
