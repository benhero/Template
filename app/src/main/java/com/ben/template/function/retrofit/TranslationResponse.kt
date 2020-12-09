package com.ben.template.function.retrofit

/**
 * 翻译返回数据
 *
 * @author Benhero
 * @date   12/8/20
 */
data class TranslationResponse(
    val author: Author,
    val code: Int,
    val data: Data,
    val msg: String
)

data class Author(
    val desc: String,
    val name: String
)

data class Data(
    val from: String,
    val to: String,
    val trans_result: List<TransResult>
)

data class TransResult(
    val dst: String,
    val src: String
)