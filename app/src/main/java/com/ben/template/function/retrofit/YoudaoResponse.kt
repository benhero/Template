package com.ben.template.function.retrofit

/**
 * 有道翻译响应
 *
 * @author Benhero
 * @date   12/9/20
 */
data class YoudaoResponse(
    val elapsedTime: Int,
    val errorCode: Int,
    val translateResult: List<List<TranslateResult>>,
    val type: String
)

data class TranslateResult(
    val src: String,
    val tgt: String
)