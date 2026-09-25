package com.thales.minhaestante.data.model

enum class ReadingStatus {
    WANT_TO_READ,
    READING,
    READ;

    companion object {
        fun fromName(name: String?): ReadingStatus? = entries.firstOrNull { it.name == name }
    }
}
