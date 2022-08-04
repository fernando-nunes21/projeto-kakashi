package com.kakashi.projetokakashi.web_api.contract

import groovy.transform.Canonical

@Canonical
class Message {
    String id
    String originalMessage
    Date date;
}
