package guru.springframework.spring6restmvc.mappers

import guru.springframework.spring6restmvc.entities.Beer
import guru.springframework.spring6restmvc.model.BeerDTO
import org.mapstruct.Mapper

@Mapper
interface BeerMapper {
    fun beerDtoToBeer(dto: BeerDTO): Beer

    fun beerToBeerDto(beer: Beer): BeerDTO
}