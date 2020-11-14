package com.harish.msscbeerservice.web.mappers;

import com.harish.msscbeerservice.domain.Beer;
import com.harish.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
