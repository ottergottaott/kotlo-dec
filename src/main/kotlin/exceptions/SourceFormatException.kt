package exceptions

class SourceFormatException : RuntimeException {

    constructor(msg: String) : super(msg)

    constructor(cause: Throwable) : super(cause)

    constructor(msg: String, cause: Throwable) : super(msg, cause)

    companion object {
        private val serialVersionUID = 1L
    }
}
