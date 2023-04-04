package com.nivin.payment.common.errors

import kotlin.reflect.KClass

/**
 * Thrown when response for requested data is empty
 */
class EmptyDataException(dataType:KClass<out Any>)
    :Exception("Requested data ${dataType.simpleName } was not found")