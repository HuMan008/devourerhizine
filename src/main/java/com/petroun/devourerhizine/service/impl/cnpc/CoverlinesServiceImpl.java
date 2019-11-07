/*
 * ******************************************************
 *  * Copyright (C) 2018 cluries  <cluries#me.com>
 *  *
 *  * This file is part of Devourer.
 *  *
 *  * Devourer can not be copied and/or distributed without the express
 *  * permission of cluries
 *  ******************************************************
 */

/*
package com.petroun.devourerhizine.service.impl.cnpc;

import cn.gotoil.bill.provider.bill.BillClient;
import cn.gotoil.bill.tools.date.DateUtils;
import cn.gotoil.devourer.model.OptionKeys;
import cn.gotoil.devourer.model.entity.CoverlinesFocusMobile;
import cn.gotoil.devourer.model.mapper.CoverlinesFocusMobileMapper;
import cn.gotoil.devourer.provider.cnpc.CnpcProvidable;
import cn.gotoil.devourer.service.OptionService;
import cn.gotoil.devourer.service.cnpc.CoverlinesService;
import com.petroun.devourerhizine.service.cnpc.CoverlinesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CoverlinesServiceImpl implements CoverlinesService {


    private static Logger logger = LoggerFactory.getLogger(CoverlinesServiceImpl.class);

    @Autowired
    private CoverlinesFocusMobileMapper coverlinesFocusMobileMapper;

    @Autowired
    private OptionService optionService;

    @Autowired
    private List<CnpcProvidable> cnpcProvidables;

    @Override
    public void informAll() {

        List<CoverlinesFocusMobile> mobiles = coverlinesFocusMobileMapper.selectByExample(null);
        if (mobiles == null || mobiles.size() < 1) {
            logger.info("Empty CoverlinesFocusMobile List");
            return;
        }

        if (cnpcProvidables.size() < 1) {
            return;
        }

        String providerPriorities = optionService.get(OptionKeys.CnpcProviderPriorities);
        if (providerPriorities == null) {
            return;
        }

        providerPriorities = providerPriorities.toLowerCase();
        Set<String> allows = new HashSet<>();
        for (String one : providerPriorities.split(",")) {
            String[] kv = one.split(":");
            if (kv.length != 2) {
                logger.error("error for parse {}", one);
                continue;
            }
            try {
                if (Integer.parseInt(kv[1]) > 0) {
                    allows.add(kv[0].trim());
                }
            } catch (Exception ed) {
                logger.error("{}", ed);
            }
        }

        StringBuilder detail = new StringBuilder(64);

        for (CnpcProvidable providable : cnpcProvidables) {
            String name = providable.name().toLowerCase();
            if (!allows.contains(name)) {
                continue;
            }
            Long b = providable.balance();
            String v = String.format("%s:%.2f ", providable.chineseName(), b / 100.0);
            detail.append(v);
        }
        final String details = detail.toString();
        mobiles.forEach((e) -> inform(e.getMobile(), details));
    }

    private void inform(String mobile, String detail) {
        Map<String, Object> body = new HashMap<>();
        body.put("templateId", optionService.get(OptionKeys.CoverlinesServiceMessageID));
        body.put("mobile", mobile);
        Map<String, String> params = new HashMap<>();
        body.put("params", params);

        params.put("detail", detail);
        params.put("time", DateUtils.simpleDatetimeFormatter().format(new Date()));
        String airjet = airjetClient().post("/api/v1/message", body);
        logger.info("CoverlinesFocusMobile:{} Response:{}", mobile, airjet);
    }

    private BillClient airjetClient() {
        BillClient billClient = new BillClient();
        billClient.setHost(optionService.get(OptionKeys.AIRJET_HOST));
        billClient.setXU(optionService.get(OptionKeys.AIRJET_KEY));
        billClient.setSecret(optionService.get(OptionKeys.AIRJET_SECRET));
        return billClient;
    }
}
*/