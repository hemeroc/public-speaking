package com.github.hemeroc.publicspeaking.archtest.application

import com.github.hemeroc.publicspeaking.archtest.io.impl.CommandlineMessageWriter
import com.github.hemeroc.publicspeaking.archtest.io.impl.CommandlineSentenceReader
import com.github.hemeroc.publicspeaking.archtest.service.impl.SimpleWordCounter

fun main() {
    val commandlineMessageWriter = CommandlineMessageWriter()
    val commandlineSentenceReader = CommandlineSentenceReader()
    val wordCounter = SimpleWordCounter()

    val application = SimpleApplication(
        messageWriter = commandlineMessageWriter,
        sentenceReader = commandlineSentenceReader,
        wordCounter = wordCounter,
    )
    application.run()
}