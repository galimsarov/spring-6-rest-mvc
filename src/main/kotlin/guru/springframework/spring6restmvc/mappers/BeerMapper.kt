package guru.springframework.spring6restmvc.mappers

import guru.springframework.spring6restmvc.entities.Beer
import guru.springframework.spring6restmvc.model.BeerDTO

fun Beer.toDto(): BeerDTO = BeerDTO(
    id = id,
    version = version,
    beerName = beerName,
    beerStyle = beerStyle,
    upc = upc,
    quantityOnHand = quantityOnHand,
    price = price,
    createdDate = createdDate,
    updateDate = updateDate
)

fun BeerDTO.toBeer(): Beer = Beer(
    id = id,
    version = version,
    beerName = beerName,
    beerStyle = beerStyle,
    upc = upc,
    quantityOnHand = quantityOnHand,
    price = price,
    createdDate = createdDate,
    updateDate = updateDate
)