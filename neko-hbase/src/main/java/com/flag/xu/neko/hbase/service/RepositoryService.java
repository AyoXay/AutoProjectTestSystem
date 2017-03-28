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

    public void scanData() throws IOException {
        LOG.info("scan ht data start");
        long startTime = System.currentTimeMillis();
        ExecutorService pool = Executors.newFixedThreadPool(5);
        List<Map<String, String>> resultList = new ArrayList<>();
        try {
            repository.visitTable(table -> {
                Scan scan = new Scan();
                scan.addColumn(Bytes.toBytes(repository.getCfName()), Bytes.toBytes("9"));
                scan.addFamily(Bytes.toBytes(repository.getCfName()));
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
        int size = resultList.size();
        List<String> realResult = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            realResult.add(resultList.get((size / 10) * i).get(String.valueOf(i)));
        }
        FileOutputUtil.output(realResult, "result", true);
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
