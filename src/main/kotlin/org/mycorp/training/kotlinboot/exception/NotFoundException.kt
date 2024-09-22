package org.mycorp.training.kotlinboot.exception

import org.springframework.web.bind.annotation.ResponseStatus

/**
 * An exception to be thrown when a resource is not found.
 */
@ResponseStatus(code = org.springframework.http.HttpStatus.NOT_FOUND)
class NotFoundException(message: String) : RuntimeException(message) {
}