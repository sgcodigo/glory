package com.codigo.glory.data.mapper

import com.codigo.glory.data.model.data.MacData
import com.khunzohn.data.model.entity.MacEntity
import com.codigo.glory.domain.model.Product

class MacMapper {

    fun dataToDomain(macDataList: List<MacData>): List<Product.Mac> {
        return macDataList.map {
            Product.Mac(
                id = it.id ?: "",
                name = it.name ?: "",
                favourite = it.favourite ?: false,
                shortDescription = it.shortDescription ?: "",
                description = it.description ?: "",
                price = it.price ?: 0.0,
                currency = it.currency ?: "",
                inStock = it.inStock ?: false,
                imageUrl = it.imageUrl ?: "",
                new = it.new ?: false
            )
        }
    }

    fun domainToEntity(macs: List<Product.Mac>): List<MacEntity> {
        return macs.map {
            MacEntity(
                id = it.id,
                name = it.name,
                favourite = it.favourite,
                shortDescription = it.shortDescription,
                description = it.description,
                price = it.price,
                currency = it.currency,
                inStock = it.inStock,
                imageUrl = it.imageUrl,
                isNew = it.new
            )
        }
    }

    fun entityToDomain(entities: List<MacEntity>): List<Product.Mac> {
        return entities.map {
            Product.Mac(
                id = it.id,
                name = it.name,
                favourite = it.favourite,
                shortDescription = it.shortDescription,
                description = it.description,
                price = it.price,
                currency = it.currency,
                inStock = it.inStock,
                imageUrl = it.imageUrl,
                new = it.isNew
            )
        }
    }
}