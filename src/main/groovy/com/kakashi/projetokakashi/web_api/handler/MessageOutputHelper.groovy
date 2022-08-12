package com.kakashi.projetokakashi.web_api.handler

import com.kakashi.projetokakashi.dialog_flow_api.contract.MessageOutput
import com.kakashi.projetokakashi.web_api.contract.IncomingMessage
import com.kakashi.projetokakashi.web_api.contract.OutgoingMessage

class MessageOutputHelper {
    static OutgoingMessage buildOutgoingMessage(MessageOutput message) {
        return new OutgoingMessage(
                id: message.responseId,
                message: message.queryResult.fulfillmentText,
                date: new Date()
        )
    }

}
