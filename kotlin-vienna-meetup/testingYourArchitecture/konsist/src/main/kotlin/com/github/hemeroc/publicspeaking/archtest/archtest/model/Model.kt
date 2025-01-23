package com.github.hemeroc.publicspeaking.archtest.model

@JvmInline
value class Sentence(val value: String)

@JvmInline
value class Message(val value: String)

data class WordCountResult(
    val count: Int,
)
