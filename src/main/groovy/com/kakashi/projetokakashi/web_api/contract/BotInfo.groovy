package com.kakashi.projetokakashi.web_api.contract

import groovy.transform.Canonical

@Canonical
class BotInfo {
    String botId
    String botName
    String botVersion
    Trainings allTrainings
    String sendMessagesToBotQuantity
}
