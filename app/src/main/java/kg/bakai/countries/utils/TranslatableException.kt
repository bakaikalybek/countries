package kg.bakai.countries.utils

import android.content.Context

interface TranslatableException {
    fun getDefaultResourceId(): Int
    fun getTranslableMessage(context: Context): String = context.getString(getDefaultResourceId())
}