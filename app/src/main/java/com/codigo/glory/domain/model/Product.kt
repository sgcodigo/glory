package com.codigo.glory.domain.model

sealed class Product {

    data class IPhone(
        val id: String,
        val imageUrl: String,
        val name: String,
        val favourite: Boolean,
        val shortDescription: String,
        val description: String,
        val price: Double,
        val currency: String,
        val inStock: Boolean,
        val new: Boolean
    ) : Product()

    data class Mac(
        val id: String,
        val imageUrl: String,
        val name: String,
        val favourite: Boolean,
        val shortDescription: String,
        val description: String,
        val price: Double,
        val currency: String,
        val inStock: Boolean,
        val new: Boolean
    ) : Product()
}