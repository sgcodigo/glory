package com.codigo.glory.data.model.data

import com.google.gson.annotations.SerializedName

data class MacData(

    @field:SerializedName("short_description")
    val shortDescription: String? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("price")
    val price: Double? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("currency")
    val currency: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("favourite")
    val favourite: Boolean? = null,

    @field:SerializedName("in_stock")
    val inStock: Boolean? = null,

    @field:SerializedName("new")
    val new: Boolean? = null
)