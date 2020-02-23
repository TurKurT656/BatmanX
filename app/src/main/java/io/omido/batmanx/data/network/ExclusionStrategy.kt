package io.omido.batmanx.data.network

import com.google.gson.FieldAttributes
import com.google.gson.ExclusionStrategy

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Exclude

object ExclusionStrategy : ExclusionStrategy {

    override fun shouldSkipField(f: FieldAttributes): Boolean {
        return f.getAnnotation(Exclude::class.java) != null
    }

    override fun shouldSkipClass(clazz: Class<*>): Boolean {
        return false
    }
}