package com.kakashi.projetokakashi.web_api.contract.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "bot_config")
class BotConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    @Column
    String name

    @Column
    String version

    @Column(name = "all_messages_send")
    Integer allMessagesSend

}
