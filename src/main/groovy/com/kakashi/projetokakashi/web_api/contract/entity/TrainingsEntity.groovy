package com.kakashi.projetokakashi.web_api.contract.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "trainings")
class TrainingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    @Column
    String question

    @Column
    String answer

    @Column(name = "flow_id")
    String flowId
}
