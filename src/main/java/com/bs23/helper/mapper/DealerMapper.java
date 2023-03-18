package com.bs23.helper.mapper;

import com.bs23.domain.entity.Dealer;
import com.bs23.domain.dto.DealerDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DealerMapper {

    public abstract List<DealerDto> toDtoList(List<Dealer> dealers);

    public abstract Dealer toEntity(DealerDto dto);

    public abstract DealerDto toDto(Dealer entity);

}
