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

    public static final Map<String, BlockingQueue<byte[]>> queueMap = new ConcurrentHashMap<>();

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
        String msgId = propPath + fileName;
        if (queueMap.get(msgId) == null){
            List<String> msg = discon.unpack0(PathUtil.getPath(propPath, fileName), 17);
            BlockingQueue<byte[]> queue = new LinkedBlockingQueue<>();
            submitMsg2Queue(msg, queue);
            queueMap.put(msgId, queue);
        } else {
            LOG.info("The message of the file {} already in the queue", msgId);
        }
    }

    private void submitMsg2Queue(List<String> lines, BlockingQueue<byte[]> queue){
        lines.stream().filter(StringUtils::isNotEmpty).forEach(s -> queue.add(BytesUtil.str2Bytes(s, " ")));
    }
}
