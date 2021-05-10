package com.fingerprintjs.android.pro.fingerprint.transport


import com.fingerprintjs.android.pro.fingerprint.logger.Logger
import org.json.JSONObject


interface RequestPerformer {
    fun perform(request: Request, listener: (RawRequestResult) -> (Unit))
}

class RequestPerformerImpl(
        private val httpClient: HttpClient,
        private val endpointURL: String,
        private val logger: Logger
        // jwtClient
        // SSL pinning detector
) : RequestPerformer {

    override fun perform(request: Request, listener: (RawRequestResult) -> Unit) {
        val requestBody = JSONObject(request.bodyAsMap()).toString().toByteArray()
        listener.invoke(httpClient.performRequest(
                request.type,
                "$endpointURL${request.path}",
                request.headers,
                requestBody
        ))
    }
}