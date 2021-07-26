/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alibaba.mos.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.mos.api.SkuReadService;
import com.alibaba.mos.constants.FileConstant;
import com.alibaba.mos.data.ChannelInventoryDO;
import com.alibaba.mos.data.SkuDO;
import com.alibaba.mos.util.JsonUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TODO: 实现
 * @author superchao
 * @version $Id: SkuReadServiceImpl.java, v 0.1 2019年10月28日 10:49 AM superchao Exp $
 */
@Service
public class SkuReadServiceImpl implements SkuReadService {

    @Value("${file.path}")
    String filePath;

    /**
     * 假设excel数据量很大无法一次性加载到内存中
     * @param handler
     */
    @Override
    public void loadSkus(SkuHandler handler) {
        try {
            // 不一次性加载到内存中，通过指针扫描文件
            LineIterator lineIterator = FileUtils.lineIterator(new File(filePath), "UTF-8");
            AtomicBoolean isHead = new AtomicBoolean(true);
            while (lineIterator.hasNext()) {
                String line = lineIterator.nextLine();
                if (isHead.getAndSet(false)) {
                    continue;
                }
                String[] cells = line.split(FileConstant.SPLIT);
                SkuDO skuDO = new SkuDO();
                skuDO.setId(cells[0]);
                skuDO.setName(cells[1]);
                skuDO.setArtNo(cells[2]);
                skuDO.setSpuId(cells[3]);
                skuDO.setSkuType(cells[4]);
                if (StringUtils.isNotBlank(cells[5])) {
                    BigDecimal price = new BigDecimal(cells[5]);
                    skuDO.setPrice(price);
                }
                if (JsonUtil.isJson(cells[6])) {
                    List<ChannelInventoryDO> channelInventoryDOS = JSON.parseArray(cells[6], ChannelInventoryDO.class);
                    skuDO.setInventoryList(channelInventoryDOS);
                }
                handler.handleSku(skuDO);
            }
        } catch (Exception e) {
            throw new RuntimeException("load skus fail", e);
        }
    }
}