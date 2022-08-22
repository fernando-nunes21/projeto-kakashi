package com.kakashi.projetokakashi.web_api.repository

import com.kakashi.projetokakashi.web_api.contract.entity.TrainingsEntity
import org.springframework.data.repository.CrudRepository

interface TrainingRepository extends CrudRepository <TrainingsEntity, Integer>{

}