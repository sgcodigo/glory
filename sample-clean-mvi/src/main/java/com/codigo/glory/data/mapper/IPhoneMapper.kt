package com.codigo.glory.data.mapper

import com.codigo.glory.data.model.data.IPhoneData
import com.khunzohn.data.model.entity.IPhoneEntity
import com.codigo.glory.domain.model.Product

class IPhoneMapper {

    fun dataToDomain(iPhoneDataList: List<IPhoneData>): List<Product.IPhone> {
        return iPhoneDataList.map {
            Product.IPhone(
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

    fun domainToEntity(iPhones: List<Product.IPhone>): List<IPhoneEntity> {
        return iPhones.map {
            IPhoneEntity(
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

    fun entityToDomain(entities: List<IPhoneEntity>): List<Product.IPhone> {
        return entities.map {
            Product.IPhone(
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