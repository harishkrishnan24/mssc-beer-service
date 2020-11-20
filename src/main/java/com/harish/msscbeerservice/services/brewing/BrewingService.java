package com.harish.msscbeerservice.services.brewing;

import com.harish.msscbeerservice.config.JmsConfig;
import com.harish.msscbeerservice.domain.Beer;
import guru.sfg.common.events.BrewBeerEvent;
import com.harish.msscbeerservice.repositories.BeerRepository;
import com.harish.msscbeerservice.services.inventory.BeerInventoryService;
import com.harish.msscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer invQOH =beerInventoryService.getOnhandInventory(beer.getId());
            log.info("****************************************");
            log.info("Min on Hand is: " + beer.getMinOnHand());
            log.info("Inventory is: " + beer.getMinOnHand());
            log.info("****************************************");

            if(beer.getMinOnHand() >= invQOH) {
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }
        });
    }
}
