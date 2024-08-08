package uz.abubakir_khakimov.simple_taxi.core.common

data class Result<out T>(
    private val status: Status,
    private val data: T? = null,
    private val message: String? = null,
    private val error: Throwable? = null,
    private val loading: Boolean = false
) {

    companion object {

        fun <T> success(data: T?, message: String? = null): Result<T> = Result(
            status = Status.SUCCESS,
            data = data,
            message = message
        )

        fun <T> error(error: Throwable?, message: String? = null): Result<T> = Result(
            status = Status.ERROR,
            message = message,
            error = error
        )

        fun <T> loading(isLoading: Boolean): Result<T> = Result(
            status = Status.LOADING,
            loading = isLoading
        )

        fun <T, K> Result<T>.letter(
            body: (isSuccess: Boolean, data: T?, error: Throwable?) -> K
        ): K = body(isSuccess(), data, error)

        suspend fun <T, K> Result<T>.asyncLetter(
            body: suspend (isSuccess: Boolean, data: T?, error: Throwable?) -> K
        ): K = body(isSuccess(), data, error)

        fun <T> Result<T>.success(body: (data: T?, message: String?) -> Unit) {
            if (status == Status.SUCCESS) body(data, message)
        }

        fun <T, K> Result<T>.successLet(body: (data: T?, message: String?) -> K): K? =
            if (status == Status.SUCCESS) body(data, message) else null

        suspend fun <T> Result<T>.asyncSuccess(body: suspend (data: T?, message: String?) -> Unit) {
            if (status == Status.SUCCESS) body(data, message)
        }

        suspend fun <T, K> Result<T>.asyncSuccessLet(body: suspend (data: T?, message: String?) -> K): K? =
            if (status == Status.SUCCESS) body(data, message) else null

        fun <T> Result<T>.error(body: (error: Throwable?, message: String?) -> Unit) {
            if (status == Status.ERROR) body(error, message)
        }

        suspend fun <T> Result<T>.asyncError(body: suspend (error: Throwable?, message: String?) -> Unit) {
            if (status == Status.ERROR) body(error, message)
        }

        fun <T> Result<T>.loading(body: (loading: Boolean) -> Unit) {
            if (status == Status.LOADING) body(loading)
        }

        suspend fun <T> Result<T>.asyncLoading(body: suspend (loading: Boolean) -> Unit) {
            if (status == Status.LOADING) body(loading)
        }

        fun <T> Result<T>.isSuccess() = (status == Status.SUCCESS)

        fun <T> Result<T>.isError() = (status == Status.ERROR)

        fun <T> Result<T>.isLoading() = (status == Status.LOADING)

        fun <A, B> Result<A>.map(body: (data: A) -> B): Result<B> = Result(
            status = status,
            data = data?.let { body(data) },
            message = message,
            error = error,
            loading = loading
        )
    }

}