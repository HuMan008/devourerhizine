/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.MybatisGenerator
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 1/4/17 10:14 AM
 *
 */

package com.petroun.devourerhizine;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class MybatisGenerator {

    public static void main(String[] args) throws Exception {
        removeMapperFile();
        cn.gotoil.bill.MybatisGenerator.gen();
    }


    private static void removeMapperFile() throws Exception {
        File file = new File("src/main/resources/generatorConfig.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        Element root = document.getDocumentElement();
        var tables = root.getElementsByTagName("table");

        String mapperLocation = "src/main/resources/mapper/generator/";
        for (int i = 0; i < tables.getLength(); i++) {
            var node = tables.item(i);
            var mapperName = node.getAttributes().getNamedItem("mapperName");
            var location = mapperLocation + mapperName.getNodeValue() + ".xml";
            var f = new File(location);
            if (f.exists()) {
                System.out.println("remove:" + location);
            }
            f.delete();
        }
    }
}