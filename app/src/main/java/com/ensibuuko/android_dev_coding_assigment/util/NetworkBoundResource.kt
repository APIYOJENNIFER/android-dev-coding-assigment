package com.ensibuuko.android_dev_coding_assigment.util

import kotlinx.coroutines.flow.*

fun <Result, Request> networkBoundResource(
    query: () -> Flow<Result>,
    fetch: suspend () -> Request,
    saveFetchResult: suspend (Request) -> Unit,
    shouldFetch: (Result) -> Boolean = { true }
) = flow {
    val data = query().first()
    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(throwable, it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }
    emitAll(flow)
}