package com.myrran.stockator.infrastructure.spring

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.retry.support.RetryTemplate
import org.springframework.web.client.RequestCallback
import org.springframework.web.client.ResponseExtractor
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate
import java.net.URI

class RestTemplateWithRetry(

    private val restTemplate: RestTemplate,
    private val retryTemplate: RetryTemplate

) : RestOperations {

    override fun <T : Any> getForObject(url: String, responseType: Class<T>, vararg uriVariables: Any?): T? =
        retryTemplate.execute<T, RestClientException> { restTemplate.getForObject(url, responseType, *uriVariables) }

    override fun <T : Any> getForObject(url: String, responseType: Class<T>, uriVariables: Map<String, Any?>): T? =
        retryTemplate.execute<T, RestClientException> { restTemplate.getForObject(url, responseType, uriVariables) }

    override fun <T : Any> getForObject(url: URI, responseType: Class<T>): T? =
        retryTemplate.execute<T, RestClientException> { restTemplate.getForObject(url, responseType) }

    override fun <T : Any> getForEntity(url: String, responseType: Class<T>, vararg uriVariables: Any?): ResponseEntity<T> =
        retryTemplate.execute<ResponseEntity<T>, RestClientException> { restTemplate.getForEntity(url, responseType, *uriVariables) }

    override fun <T : Any> getForEntity(url: String, responseType: Class<T>, uriVariables: Map<String, Any?>): ResponseEntity<T> =
        retryTemplate.execute<ResponseEntity<T>, RestClientException> { restTemplate.getForEntity(url, responseType, uriVariables) }

    override fun <T : Any> getForEntity(url: URI, responseType: Class<T>): ResponseEntity<T> =
        retryTemplate.execute<ResponseEntity<T>, RestClientException> { restTemplate.getForEntity(url, responseType) }

    override fun headForHeaders(url: String, vararg uriVariables: Any?): HttpHeaders =
        retryTemplate.execute<HttpHeaders, RestClientException> { restTemplate.headForHeaders(url, *uriVariables) }

    override fun headForHeaders(url: String, uriVariables: Map<String, Any?>): HttpHeaders =
        retryTemplate.execute<HttpHeaders, RestClientException> { restTemplate.headForHeaders(url, uriVariables) }

    override fun headForHeaders(url: URI): HttpHeaders =
        retryTemplate.execute<HttpHeaders, RestClientException> { restTemplate.headForHeaders(url) }

    override fun postForLocation(url: String, request: Any?, vararg uriVariables: Any?): URI? =
        retryTemplate.execute<URI?, RestClientException> { restTemplate.postForLocation(url, request, *uriVariables) }

    override fun postForLocation(url: String, request: Any?, uriVariables: Map<String, Any?>): URI? =
        retryTemplate.execute<URI?, RestClientException> { restTemplate.postForLocation(url, request, uriVariables) }

    override fun postForLocation(url: URI, request: Any?): URI? =
        retryTemplate.execute<URI?, RestClientException> { restTemplate.postForLocation(url, request) }

    override fun <T : Any> postForObject(url: String, request: Any?, responseType: Class<T>, vararg uriVariables: Any?): T? =
        retryTemplate.execute<T, RestClientException> { restTemplate.postForObject(url, request, responseType, *uriVariables) }

    override fun <T : Any> postForObject(url: String, request: Any?, responseType: Class<T>, uriVariables: Map<String, Any?>): T? =
        retryTemplate.execute<T, RestClientException> { restTemplate.postForObject(url, request, responseType, uriVariables) }

    override fun <T : Any> postForObject(url: URI, request: Any?, responseType: Class<T>): T? =
        retryTemplate.execute<T, RestClientException> { restTemplate.postForObject(url, request, responseType) }

    override fun <T : Any> postForEntity(url: String, request: Any?, responseType: Class<T>, vararg uriVariables: Any?): ResponseEntity<T> =
        retryTemplate.execute<ResponseEntity<T>, RestClientException> { restTemplate.postForEntity(url, request, responseType, *uriVariables) }

    override fun <T : Any> postForEntity(url: String, request: Any?, responseType: Class<T>, uriVariables: Map<String, out Any?>): ResponseEntity<T> =
        retryTemplate.execute<ResponseEntity<T>, RestClientException> { restTemplate.postForEntity(url, request, responseType, uriVariables) }

    override fun <T : Any> postForEntity(url: URI, request: Any?, responseType: Class<T>): ResponseEntity<T> =
        retryTemplate.execute<ResponseEntity<T>, RestClientException> { restTemplate.postForEntity(url, request, responseType) }

    override fun put(url: String, request: Any?, vararg uriVariables: Any?): Unit =
        retryTemplate.execute<Unit, RestClientException> { restTemplate.put(url, request, *uriVariables) }

    override fun put(url: String, request: Any?, uriVariables: Map<String, Any?>): Unit =
        retryTemplate.execute<Unit, RestClientException> { restTemplate.put(url, request, uriVariables) }

    override fun put(url: URI, request: Any?): Unit =
        retryTemplate.execute<Unit, RestClientException> { restTemplate.put(url, request) }

    override fun <T : Any> patchForObject(url: String, request: Any?, responseType: Class<T>, vararg uriVariables: Any?): T? =
        retryTemplate.execute<T, RestClientException> { restTemplate.patchForObject(url, request, responseType, *uriVariables) }

    override fun <T : Any> patchForObject(url: String, request: Any?, responseType: Class<T>, uriVariables: Map<String, Any?>): T? =
        retryTemplate.execute<T, RestClientException> { restTemplate.patchForObject(url, request, responseType, uriVariables) }

    override fun <T : Any> patchForObject(url: URI, request: Any?, responseType: Class<T>): T? =
        retryTemplate.execute<T, RestClientException> { restTemplate.patchForObject(url, request, responseType) }

    override fun delete(url: String, vararg uriVariables: Any?): Unit =
        retryTemplate.execute<Unit, RestClientException> { restTemplate.delete(url, *uriVariables) }

    override fun delete(url: String, uriVariables: Map<String, Any?>): Unit =
        retryTemplate.execute<Unit, RestClientException> { restTemplate.delete(url, uriVariables) }

    override fun delete(url: URI): Unit =
        retryTemplate.execute<Unit, RestClientException> { restTemplate.delete(url) }

    override fun optionsForAllow(url: String, vararg uriVariables: Any?): Set<HttpMethod> =
        retryTemplate.execute<Set<HttpMethod>, RestClientException> { restTemplate.optionsForAllow(url, *uriVariables) }

    override fun optionsForAllow(url: String, uriVariables: Map<String, Any?>): Set<HttpMethod> =
        retryTemplate.execute<Set<HttpMethod>, RestClientException> { restTemplate.optionsForAllow(url, uriVariables) }

    override fun optionsForAllow(url: URI): Set<HttpMethod> =
        retryTemplate.execute<Set<HttpMethod>, RestClientException> { restTemplate.optionsForAllow(url) }

    override fun <T : Any> exchange(url: String, method: HttpMethod, requestEntity: HttpEntity<*>?, responseType: Class<T>, vararg uriVariables: Any?): ResponseEntity<T> =
        retryTemplate.execute<ResponseEntity<T>, RestClientException> { restTemplate.exchange(url, method, requestEntity, responseType, *uriVariables) }

    override fun <T : Any> exchange(url: String, method: HttpMethod, requestEntity: HttpEntity<*>?, responseType: Class<T>, uriVariables: Map<String, Any?>): ResponseEntity<T> =
        retryTemplate.execute<ResponseEntity<T>, RestClientException> { restTemplate.exchange(url, method, requestEntity, responseType, uriVariables) }

    override fun <T : Any> exchange(url: URI, method: HttpMethod, requestEntity: HttpEntity<*>?, responseType: Class<T>): ResponseEntity<T> =
        retryTemplate.execute<ResponseEntity<T>, RestClientException> { restTemplate.exchange(url, method, requestEntity, responseType) }

    override fun <T : Any> exchange(url: String, method: HttpMethod, requestEntity: HttpEntity<*>?, responseType: ParameterizedTypeReference<T>, vararg uriVariables: Any?): ResponseEntity<T> =
        retryTemplate.execute<ResponseEntity<T>, RestClientException> { restTemplate.exchange(url, method, requestEntity, responseType, *uriVariables) }

    override fun <T : Any> exchange(url: String, method: HttpMethod, requestEntity: HttpEntity<*>?, responseType: ParameterizedTypeReference<T>, uriVariables: Map<String, Any?>): ResponseEntity<T> =
        retryTemplate.execute<ResponseEntity<T>, RestClientException> { restTemplate.exchange(url, method, requestEntity, responseType, uriVariables) }

    override fun <T : Any> exchange(url: URI, method: HttpMethod, requestEntity: HttpEntity<*>?, responseType: ParameterizedTypeReference<T>): ResponseEntity<T> =
        retryTemplate.execute<ResponseEntity<T>, RestClientException> { restTemplate.exchange(url, method, requestEntity, responseType) }

    override fun <T : Any> exchange(requestEntity: RequestEntity<*>, responseType: Class<T>): ResponseEntity<T> =
        retryTemplate.execute<ResponseEntity<T>, RestClientException> { restTemplate.exchange(requestEntity, responseType) }

    override fun <T : Any> exchange(requestEntity: RequestEntity<*>, responseType: ParameterizedTypeReference<T>): ResponseEntity<T> =
        retryTemplate.execute<ResponseEntity<T>, RestClientException> { restTemplate.exchange(requestEntity, responseType) }

    override fun <T : Any> execute(uriTemplate: String, method: HttpMethod, requestCallback: RequestCallback?, responseExtractor: ResponseExtractor<T>?, vararg uriVariables: Any?): T? =
        retryTemplate.execute<T, RestClientException> { restTemplate.execute(uriTemplate, method, requestCallback, responseExtractor, *uriVariables) }

    override fun <T : Any> execute(uriTemplate: String, method: HttpMethod, requestCallback: RequestCallback?, responseExtractor: ResponseExtractor<T>?, uriVariables: Map<String, Any?>): T? =
        retryTemplate.execute<T, RestClientException> { restTemplate.execute(uriTemplate, method, requestCallback, responseExtractor, uriVariables) }

    override fun <T : Any> execute(url: URI, method: HttpMethod, requestCallback: RequestCallback?, responseExtractor: ResponseExtractor<T>?): T? =
        retryTemplate.execute<T, RestClientException> { restTemplate.execute(url, method, requestCallback, responseExtractor) }
}
