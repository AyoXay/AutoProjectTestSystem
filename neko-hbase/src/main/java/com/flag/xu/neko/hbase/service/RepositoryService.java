package com.flag.xu.neko.hbase.service;

import com.flag.xu.neko.core.utils.FileOutputUtil;
import com.flag.xu.neko.hbase.repo.TableRepository;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

    private TableRepository repository = new TableRepository();

    private List<String> plates;

    /**
     * save data to ht, will save million row random data
     *
     * @throws IOException
     */
    public void saveData() throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(20);
        List<Put> puts = new ArrayList<>();
        repository.visitTable((table) -> {
            for (int i = 0; i < 1000000; i++) {
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
    }

    /**
     * build a RepositoryService object
     *
     * @return instance of RepositoryService
     * @throws IOException
     */
    public static RepositoryService build() throws IOException {
        return build(10);
    }

    /**
     * build a RepositoryService object
     *
     * @param platLength plateLength
     * @return instance of RepositoryService
     * @throws IOException
     */
    public static RepositoryService build(int platLength) throws IOException {
        RepositoryService repositoryService = new RepositoryService();
        repositoryService.plates = new ArrayList<>(platLength);
        for (int i = 0; i < platLength; i++) {
            repositoryService.plates.add(repositoryService.random());
        }
        FileOutputUtil.output(repositoryService.plates, "copy", true);
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
