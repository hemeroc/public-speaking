package com.github.hemeroc.publicspeaking.archtest.service.impl

import com.github.hemeroc.publicspeaking.archtest.model.Sentence
import com.github.hemeroc.publicspeaking.archtest.model.WordCountResult
import com.github.hemeroc.publicspeaking.archtest.service.WordCounter

class SimpleWordCounter : WordCounter {

    private val wordRegex = Regex("[a-zA-Z]+")

    override fun count(sentence: Sentence): WordCountResult {
        val foundWords = wordRegex.findAll(sentence.value)
        return WordCountResult(
            count = foundWords.count()
        )
    }
}
