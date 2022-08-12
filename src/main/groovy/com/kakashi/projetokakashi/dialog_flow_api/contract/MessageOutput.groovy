package com.kakashi.projetokakashi.dialog_flow_api.contract

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
@JsonIgnoreProperties(ignoreUnknown = true)
class MessageOutput {
    String responseId
    QueryResult queryResult
}

@Canonical
@JsonIgnoreProperties(ignoreUnknown = true)
class QueryResult {
    String queryText
    String action
    Parameters parameters
    String allRequiredParamsPresent
    String fulfillmentText
    List<FulfillmentMessages> fulfillmentMessages
    List<OutputContexts> outputContexts
    Intent intent
    String intentDetectionConfidence
    String languageCode
}

@Canonical
@JsonIgnoreProperties(ignoreUnknown = true)
class Parameters {
    String language

    @JsonProperty("language-programming")
    String languageProgramming
}

@Canonical
@JsonIgnoreProperties(ignoreUnknown = true)
class FulfillmentMessages {
    TextList text
}

@Canonical
@JsonIgnoreProperties(ignoreUnknown = true)
class TextList {
    List<String> text
}

@Canonical
@JsonIgnoreProperties(ignoreUnknown = true)
class OutputContexts {
    String name
    Integer lifespanCount
    ParametersOutputContext parameters
}

@Canonical
@JsonIgnoreProperties(ignoreUnknown = true)
class ParametersOutputContext {
    String language

    @JsonProperty("language.original")
    String languageOriginal

    @JsonProperty("language-programming")
    String languageProgramming

    @JsonProperty("language-programming.original")
    String languageProgrammingOriginal
}

@Canonical
@JsonIgnoreProperties(ignoreUnknown = true)
class Intent {
    String name
    String displayName
}