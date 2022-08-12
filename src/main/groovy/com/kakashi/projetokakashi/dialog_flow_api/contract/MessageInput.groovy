package com.kakashi.projetokakashi.dialog_flow_api.contract

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class MessageInput {

    @JsonProperty("query_input")
    QueryInput queryInput

}

@Canonical
class QueryInput{

    Text text

}

@Canonical
class Text {

    String text

    @JsonProperty("language_code")
    String languageCode

}
