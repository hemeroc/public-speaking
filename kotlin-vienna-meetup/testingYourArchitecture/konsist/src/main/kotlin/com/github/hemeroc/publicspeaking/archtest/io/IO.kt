package com.github.hemeroc.publicspeaking.archtest.io

import com.github.hemeroc.publicspeaking.archtest.model.Message
import com.github.hemeroc.publicspeaking.archtest.model.Sentence

interface SentenceReader {
    fun read(): Sentence
}

interface MessageWriter {
    fun write(message: Message)
}
