package com.kakashi.projetokakashi.web_api.contract

import groovy.transform.Canonical

@Canonical
class OutgoingMessage {
    String id
    String message
    String responseBotId
    String originalSenderId
    Date date
}
