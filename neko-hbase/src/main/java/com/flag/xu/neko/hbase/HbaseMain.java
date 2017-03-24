package com.flag.xu.neko.hbase;

import com.flag.xu.neko.hbase.repo.TableRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * main class
 *
 * @author xuj
 * @version V1.0-SNAPSHOT
 * @since 2017-03-22-17:14
 */
public class HbaseMain {

    private static final Logger LOG = LogManager.getLogger(HbaseMain.class);

    public static void main(String[] args) {
        LOG.info("hbase access main start");
        try (TableRepository repository = new TableRepository()) {
            repository.createTable(true);
        } catch (Exception e) {
            LOG.error("main error, {} ", e.getMessage());
        }

    }
}
