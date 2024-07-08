package com.example.api.utils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.api.models.Partida;
import com.example.api.repository.PartidaRepository;

@Component
@EnableScheduling
public class EncerrarPartidasSchedule {
    private static final Logger LOGGER = Logger.getLogger(EncerrarPartidasSchedule.class.getName());
    @Autowired
    private PartidaRepository partidaRepository;

    //definindo Time Zone America/Sao_Paulo o agendamento poderá ser executado sem a preocupação de onde o projeto esteja alocado seja no brasil ou em qualquer lugar
    //a cada 15 mins
    @Scheduled(cron = "0 */15 * ? * *", zone = "America/Sao_Paulo")
    public void encerraPartidasExpiradas(){
        LOGGER.info("schedule encerrar partidas expiradas iniciando..." + LocalDateTime.now());
        Collection<Partida> partidas = partidaRepository.findPartidasExpiradas();
        LOGGER.info(partidas.size() + " partidas expiradas encontradas");
        partidas.forEach(p -> {
            p.setEncerrado(true);
        });
        partidaRepository.saveAll(partidas);
    }
}
