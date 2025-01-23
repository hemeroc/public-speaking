package com.github.hemeroc.publicspeaking.archtest.application

import com.github.hemeroc.publicspeaking.archtest.io.MessageWriter
import com.github.hemeroc.publicspeaking.archtest.io.SentenceReader
import com.github.hemeroc.publicspeaking.archtest.model.Message
import com.github.hemeroc.publicspeaking.archtest.service.WordCounter


interface Application {
    fun run()
}

class SimpleApplication(
    private val messageWriter: MessageWriter,
    private val sentenceReader: SentenceReader,
    private val wordCounter: WordCounter,
) : Application {
    override fun run() {
        messageWriter.write(Message("Enter text: "))
        val sentence = sentenceReader.read()
        val wordCountResult = wordCounter.count(sentence)
        messageWriter.write(Message("Number of words: ${wordCountResult.count}"))
    }
}
