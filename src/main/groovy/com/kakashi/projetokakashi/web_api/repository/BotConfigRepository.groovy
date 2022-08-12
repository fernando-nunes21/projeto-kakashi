package com.kakashi.projetokakashi.web_api.repository

import com.kakashi.projetokakashi.web_api.contract.entity.BotConfig
import org.springframework.data.repository.CrudRepository

interface BotConfigRepository extends CrudRepository<BotConfig, Integer> {

}
