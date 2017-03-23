package com.flag.xu.neko.hbase.repo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * hbase table repository
 *
 * @author xuj
 * @version V1.0-SNAPSHOT
 * @since 2017-03-22-17:17
 */
public class TableRepository {

    private static final Logger LOG = LogManager.getLogger(TableRepository.class);

    private static final String TABLE_NAME = "million_row";

    private static final String CF_DEFAULT = "cf";

    private Configuration conf = HBaseConfiguration.create();

    public void saveObjToTable(Object obj){

    }
}
