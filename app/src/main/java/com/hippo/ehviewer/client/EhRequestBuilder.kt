/*
 * Copyright 2016 Hippo Seven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hippo.ehviewer.client

import io.ktor.http.HttpMessageBuilder
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

inline fun ehRequest(url: String, referer: String? = null, origin: String? = null, builder: Request.Builder.() -> Unit = {}) = Request.Builder().url(url).apply {
    addHeader("User-Agent", CHROME_USER_AGENT)
    addHeader("Accept", CHROME_ACCEPT)
    addHeader("Accept-Language", CHROME_ACCEPT_LANGUAGE)
    referer?.let { addHeader("Referer", it) }
    origin?.let { addHeader("Origin", it) }
}.apply(builder).build()

inline fun Request.Builder.formBody(builder: FormBody.Builder.() -> Unit) = post(FormBody.Builder().apply(builder).build())

inline fun Request.Builder.multipartBody(builder: MultipartBody.Builder.() -> Unit) = post(MultipartBody.Builder().apply(builder).build())

val MEDIA_TYPE_JSON: MediaType = "application/json; charset=utf-8".toMediaType()

inline fun JSONObject.array(name: String, builder: JSONArray.() -> Unit): JSONObject = put(name, JSONArray().apply(builder))

fun jsonArrayOf(vararg element: Any?) = JSONArray().apply { element.forEach { put(it) } } // Should ensure it is inlined

inline fun Request.Builder.jsonBody(builder: JSONObject.() -> Unit) = post(JSONObject().apply(builder).toString().toRequestBody(MEDIA_TYPE_JSON))

fun HttpMessageBuilder.referer(value: String?): Unit = value?.let { headers.append("Referer", it) } ?: Unit

const val CHROME_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36"
const val CHROME_ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"
const val CHROME_ACCEPT_LANGUAGE = "en-US,en;q=0.5"
