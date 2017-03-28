package com.flag.xu.neko.hbase.repo;

import com.flag.xu.neko.hbase.visitor.IVisitor;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    /**
     * visit hbase table and operate table
     * this file will open single thread for execute operation
     *
     * @param visitor the table visitor
     * @throws IOException
     */
    public void visitTable(IVisitor<Table> visitor) throws IOException {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        visitTable(visitor, pool);
    }

    /**
     * visit hbase table and operate table
     *
     * @param visitor the table visitor
     * @param pool    execute pool
     * @throws IOException
     */
    public void visitTable(IVisitor<Table> visitor, @NotNull ExecutorService pool) throws IOException {
        TableName name = TableName.valueOf(tableName);
        if (!admin.tableExists(name)) {
            LOG.error("table {} does not exist", tableName);
            System.exit(-1);
        }

        try (Table table = connection.getTable(name, pool)) {
            visitor.visit(table);
        }
    }

    /**
     * create table
     *
     * @throws IOException
     */
    public void createTable() throws IOException {
        createTable(false);
    }

    /**
     * create hbase table
     *
     * @param override override or not
     * @throws IOException
     */
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

    /**
     * set special table name
     *
     * @param tableName table name will be operated
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * set special column family
     *
     * @param cfName column family name
     */
    public void setCfName(String cfName) {
        this.cfName = cfName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getCfName() {
        return cfName;
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

    /**
     * init repository
     */
    private void init() {
        try {
            connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
        } catch (IOException e) {
            LOG.error("connect hbase error {}", e.getMessage());
        }
    }
}
