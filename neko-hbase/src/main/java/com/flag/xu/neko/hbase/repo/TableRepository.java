package com.flag.xu.neko.hbase.repo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * hbase table repository
 *
 * @author xuj
 * @version V1.0-SNAPSHOT
 * @since 2017-03-22-17:17
 */
public class TableRepository implements AutoCloseable {

    private static final Logger LOG = LogManager.getLogger(TableRepository.class);

    private String tableName = "million_row";

    private String cfName = "cf";

    private Configuration conf = HBaseConfiguration.create();

    private Admin admin;

    private Connection connection;

    public TableRepository() {
        init();
    }

    public TableRepository(String tableName, String cfName) {
        this.tableName = tableName;
        this.cfName = cfName;
        init();
    }

    public void saveObjToTable(Object obj) {

    }

    public void createTable(boolean override) throws IOException {
        HTableDescriptor table = new HTableDescriptor(TableName.valueOf(tableName));
        table.addFamily(new HColumnDescriptor(cfName).setCompressionType(Compression.Algorithm.GZ));
        if (admin.tableExists(table.getTableName())) {
            if (override) {
                admin.disableTable(table.getTableName());
                admin.deleteTable(table.getTableName());
                admin.createTable(table);
            } else {
                LOG.warn("table {} already exists", tableName);
            }
        } else {
            admin.createTable(table);
        }
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setCfName(String cfName) {
        this.cfName = cfName;
    }

    @Override
    public void close() throws Exception {
        if (admin != null) {
            admin.close();
        }

        if (connection != null) {
            connection.close();
        }
    }

    private void init() {
        try {
            connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
        } catch (IOException e) {
            LOG.error("connect hbase error {}", e.getMessage());
        }
    }
}
