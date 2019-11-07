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

package com.petroun.devourerhizine.provider.cnpc;


import java.util.List;

public interface CnpcProvidable {

    Integer id();

    String name();

    String chineseName();

    List<Director> availableDirectors();

    List<Integer> availableAmounts(Director director);

    boolean supportedAmount(Integer amount, Director director);

    boolean supportedDirector(Director director);

    CnpcProviderResult charge(Director director, Long orderId, String card, Integer amount);

    CnpcProviderInquireResult inquire(Long orderId, String providerOrder, String directorCard);

    //单位分
    Long balance();

}
