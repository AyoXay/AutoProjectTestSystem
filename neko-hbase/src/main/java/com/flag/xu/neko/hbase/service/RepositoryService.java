package com.flag.xu.neko.hbase.service;

import com.flag.xu.neko.core.utils.FileOutputUtil;
import com.flag.xu.neko.hbase.repo.TableRepository;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * service for repository, this service just for a test experiment
 *
 * @author xuj
 * @version V1.0-SNAPSHOT
 * @since 2017-03-27-11:35
 */
public class RepositoryService {

    private static final Logger LOG = LogManager.getLogger(RepositoryService.class);

    private TableRepository repository;

    private List<String> plates;

    private RepositoryService(){}

    /**
     * save data to ht, will save million row random data
     *
     * @throws IOException
     */
    public void saveData() throws IOException {
        LOG.info("save data start");
        ExecutorService pool = Executors.newFixedThreadPool(20);
        List<Put> puts = new ArrayList<>();
        repository.visitTable((table) -> {
            for (int i = 0; i < 100000; i++) {
                byte[] row = Bytes.add(Bytes.toBytes(plates.get(i % plates.size())), Bytes.toBytes(Integer.MAX_VALUE - LocalDateTime.now().get(ChronoField.MILLI_OF_DAY)));
                Put put = new Put(row);
                for (int j = 0; j < 10; j++) {
                    put.addColumn(Bytes.toBytes(repository.getCfName()), Bytes.toBytes(String.valueOf(j)), Bytes.toBytes(UUID.randomUUID().toString()));
                }
                puts.add(put);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    LOG.error("sleep error, {}", e.getMessage());
                }
            }
            try {
                LOG.info("will save data to hbase {}", puts.size());
                table.put(puts);
                LOG.info("save data done");
            } catch (IOException e) {
                LOG.error("put data to table fail, {}", e.getMessage());
            }
            return table;
        }, pool);
        pool.shutdown();
        FileOutputUtil.appendOutput(plates, "./copy");
    }

    /**
     * scan method for test
     *
     * @throws IOException
     */
    public void scanData() throws IOException {
        LOG.info("scan ht data start");
        long startTime = System.currentTimeMillis();
        ExecutorService pool = Executors.newFixedThreadPool(5);
        List<Map<String, String>> resultList = new ArrayList<>();
        try {
            repository.visitTable(table -> {
                Scan scan = new Scan();

//                scan.addColumn(Bytes.toBytes(repository.getCfName()), Bytes.toBytes("9"));
                scan.addFamily(Bytes.toBytes(repository.getCfName()));

//                FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
//                filterList.addFilter(new SingleColumnValueFilter(Bytes.toBytes(repository.getCfName()), Bytes.toBytes("9"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes("6cec8396-289f-4141-8fc7-9124005dbcbc")));
//                filterList.addFilter(new SingleColumnValueFilter(Bytes.toBytes(repository.getCfName()), Bytes.toBytes("8"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes("1db74629-bdb0-47cb-a0f7-d717f958dc79")));
//                filterList.addFilter(new SingleColumnValueFilter(Bytes.toBytes(repository.getCfName()), Bytes.toBytes("7"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes("2943dc0f-eb55-4afa-a825-1eb4894b3312")));
//                filterList.addFilter(new SingleColumnValueFilter(Bytes.toBytes(repository.getCfName()), Bytes.toBytes("6"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes("e2e686a0-a1ff-4b48-8287-b2b2daff4387")));
//                filterList.addFilter(new SingleColumnValueFilter(Bytes.toBytes(repository.getCfName()), Bytes.toBytes("5"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes("6a18e26b-4aee-4dbf-9643-7b5b0218bd39")));
//                scan.setFilter(filterList);
//                scan.setFilter(new SingleColumnValueFilter(Bytes.toBytes(repository.getCfName()), Bytes.toBytes("9"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes("6cec8396-289f-4141-8fc7-9124005dbcbc")));
                try {
                    ResultScanner results = table.getScanner(scan);
                    results.forEach(result -> {
                        Map<String, String> map = new HashMap<>();
                        result.getFamilyMap(Bytes.toBytes(repository.getCfName())).forEach((k, v) -> {
                            map.put(Bytes.toString(k), Bytes.toString(v));
                        });
                        resultList.add(map);
                    });
                } catch (IOException e) {
                    LOG.error("scan data error {}", e.getMessage());
                    e.printStackTrace();
                }
                return table;
            }, pool);
        } finally {
            pool.shutdown();
        }
        LOG.info("scan data end, data size is {}, cast time {}", resultList.size(), System.currentTimeMillis() - startTime);
    }

    /**
     * build a RepositoryService object
     *
     * @return instance of RepositoryService
     * @throws IOException
     */
    public static RepositoryService build() throws IOException {
        return build(10, new TableRepository());
    }

    public static RepositoryService build(int platLength) throws IOException {
        return build(platLength, new TableRepository());
    }

    public static RepositoryService build(TableRepository repository) throws IOException {
        return build(10, repository);
    }

    /**
     * build a RepositoryService object
     *
     * @param platLength plateLength
     * @return instance of RepositoryService
     * @throws IOException
     */
    public static RepositoryService build(int platLength, TableRepository repository) throws IOException {
        RepositoryService repositoryService = new RepositoryService();
        repositoryService.repository = repository;
        repositoryService.plates = new ArrayList<>(platLength);
        for (int i = 0; i < platLength; i++) {
            repositoryService.plates.add(repositoryService.random());
        }
        return repositoryService;
    }

    private static final char[] CHARS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * random method ,start with two random big case char, append four random digit
     *
     * @return random result string
     */
    private String random() {
        String s = new String(new char[]{CHARS[getCharsRandomIndex()], CHARS[getCharsRandomIndex()]});
        for (int i = 0; i < 4; i++) {
            s += Math.round(Math.random() * 10) % 10;
        }
        return s;
    }

    /**
     * get a random index for CHARS
     *
     * @return random result index
     */
    private int getCharsRandomIndex() {
        return (int) Math.round(Math.random() * 100) % CHARS.length;
    }

}
