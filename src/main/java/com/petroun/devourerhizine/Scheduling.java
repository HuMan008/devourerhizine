package com.petroun.devourerhizine;

import com.petroun.devourerhizine.model.OptionKeys;
import com.petroun.devourerhizine.provider.petroun.Rhizine;
import com.petroun.devourerhizine.service.OptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduling {

    private static Logger logger = LoggerFactory.getLogger(Scheduling.class);

    @Autowired
    private OptionService optionService;

    @Scheduled(initialDelay = 1000, fixedRate = 60000)
    public void reloadCache() {
        logger.info("Scheduling reloadCache");

        optionService.reload();


        Rhizine.configure(optionService.get(OptionKeys.RHIZINE_HOST),
                optionService.get(OptionKeys.RHIZINE_XU),
                optionService.get(OptionKeys.RHIZINE_SECRET));
    }
}
