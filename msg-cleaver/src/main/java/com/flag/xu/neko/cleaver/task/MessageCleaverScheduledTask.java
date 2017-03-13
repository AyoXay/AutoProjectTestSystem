package com.flag.xu.neko.cleaver.task;

import com.flag.xu.neko.cleaver.amput.AbstractDiscon;
import com.flag.xu.neko.cleaver.amput.DefaultMessageDiacon;
import com.flag.xu.neko.cleaver.util.BytesUtil;
import com.flag.xu.neko.core.utils.PathUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This is a scheduled task for message cleaver
 *
 * @author xuj
 * @version V1.0-SNAPSHOT
 * @since 2017-03-13-10:09
 */
public class MessageCleaverScheduledTask implements Runnable {

    private static final Logger LOG = LogManager.getLogger(MessageCleaverScheduledTask.class);

    private AbstractDiscon<Path> discon = new DefaultMessageDiacon<>();

    public static final Map<String, BlockingQueue<byte[]>> QUEUE_MAP = new ConcurrentHashMap<>();

    public static String realTimeMsgId;

    @Override
    public void run() {
        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(PathUtil.getPath(MessageCleaverScheduledTask.class, "file.properties")));
        } catch (IOException e) {
            LOG.error("load properties failed, cause by {}", e.getMessage());
        }
        String propPath = properties.getProperty("path");
        String fileName = properties.getProperty("name");
        Integer offset = Integer.valueOf(properties.getProperty("offset") != null ? properties.getProperty("offset") : "0");
        realTimeMsgId = propPath + fileName;
        if (QUEUE_MAP.get(realTimeMsgId) == null || QUEUE_MAP.get(realTimeMsgId).isEmpty()) {
            List<String> msg = discon.unpack0(PathUtil.getPath(propPath, fileName), offset);
            BlockingQueue<byte[]> queue = new LinkedBlockingQueue<>();
            submitMsg2Queue(msg, queue);
            QUEUE_MAP.putIfAbsent(realTimeMsgId, queue);
        } else {
            LOG.debug("The message of the file {} already in the queue", realTimeMsgId);
        }
    }

    /**
     * submit message to queue
     *
     * @param lines string list, message lines will be submitted
     * @param queue the queue
     */
    private void submitMsg2Queue(List<String> lines, BlockingQueue<byte[]> queue) {
        lines.stream().filter(StringUtils::isNotEmpty).forEach(s -> {
            try {
                queue.put(BytesUtil.str2Bytes(s, " "));
            } catch (InterruptedException e) {
                LOG.error("blocking queue put error cause by {}", e.getMessage());
            }
        });
    }
}
