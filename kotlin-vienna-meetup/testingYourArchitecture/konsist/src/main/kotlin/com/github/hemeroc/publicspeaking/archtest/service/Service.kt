package com.github.hemeroc.publicspeaking.archtest.service

import com.github.hemeroc.publicspeaking.archtest.model.Sentence
import com.github.hemeroc.publicspeaking.archtest.model.WordCountResult

interface WordCounter {
    fun count(sentence: Sentence): WordCountResult
}
