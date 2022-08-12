package com.kakashi.projetokakashi.dialog_flow_api.service

import com.kakashi.projetokakashi.web_api.contract.OutgoingMessage
import org.springframework.stereotype.Service

@Service
interface DialogFlowService {

    OutgoingMessage sendMessage(String message, String sessionId)

}