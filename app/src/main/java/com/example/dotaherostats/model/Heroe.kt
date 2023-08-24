package com.example.dotaherostats.model

import com.google.gson.annotations.SerializedName

data class Heroe(
    @SerializedName("id"                ) var id              : Int?              = null,
    @SerializedName("name"              ) var name            : String?           = null,
    @SerializedName("localized_name"    ) var localizedName   : String?           = null,
    @SerializedName("primary_attr"      ) var primaryAttr     : String?           = null,
    @SerializedName("attack_type"       ) var attackType      : String?           = null,
    @SerializedName("roles"             ) var roles           : ArrayList<String> = arrayListOf(),
    @SerializedName("img"               ) var img             : String?           = null,
    @SerializedName("icon"              ) var icon            : String?           = null,
    @SerializedName("base_health"       ) var baseHealth      : Int?              = null,
    @SerializedName("base_health_regen" ) var baseHealthRegen : Int?              = null,
    @SerializedName("base_mana"         ) var baseMana        : Int?              = null
)