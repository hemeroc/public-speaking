package com.github.hemeroc.publicspeaking.archtest.io.impl

import com.github.hemeroc.publicspeaking.archtest.io.MessageWriter
import com.github.hemeroc.publicspeaking.archtest.io.SentenceReader
import com.github.hemeroc.publicspeaking.archtest.model.Message
import com.github.hemeroc.publicspeaking.archtest.model.Sentence

class CommandlineMessageWriter : MessageWriter {
    override fun write(message: Message) =
        print(message.value)
}

class CommandlineSentenceReader : SentenceReader {
    override fun read() =
        Sentence(readlnOrNull() ?: "")
}
