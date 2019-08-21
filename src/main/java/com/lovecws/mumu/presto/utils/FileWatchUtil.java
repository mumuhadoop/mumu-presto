package com.lovecws.mumu.presto.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.nio.file.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: mumu-presto
 * @description: ${description}
 * @author: 甘亮
 * @create: 2019-08-19 09:26
 **/
public class FileWatchUtil extends Thread {

    private static final Logger log = Logger.getLogger(FileWatchUtil.class);

    private static final Map<String, FileWatchUtil> fileWatchMap = new ConcurrentHashMap<>();
    private String watchPath;

    public FileWatchUtil(String watchPath) {
        this.watchPath = watchPath;
    }


    @Override
    public void run() {
        log.info("watch " + watchPath);
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Paths.get(watchPath).register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.OVERFLOW);
            fileWatchMap.put(watchPath, this);
            File[] files = new File(watchPath).listFiles();
            if (files != null && files.length > 0) {
                for (File file : new File(watchPath).listFiles()) {
                    if (file.isDirectory()) {
                        FileWatchUtil fileWatchUtil = new FileWatchUtil(file.getAbsolutePath());
                        fileWatchMap.put(watchPath, fileWatchUtil);
                        fileWatchUtil.start();
                    }
                }
            }

            while (true) {
                WatchKey watchKey = watchService.take();
                for (WatchEvent watchEvent : watchKey.pollEvents()) {
                    Path path = Paths.get(watchPath, watchEvent.context().toString()).toAbsolutePath();
                    if (path.toFile().isFile()) {
                        watchFileEvent(watchKey, watchEvent, path);
                    } else if (path.toFile().isDirectory()) {
                        watchDirEvent(watchKey, watchEvent, path);
                    }
                    log.info(watchEvent.kind().name() + " " + Paths.get(watchPath, watchEvent.context().toString()).toAbsolutePath());
                }
                boolean isKeyValid = watchKey.reset();
                if (!isKeyValid) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public void watchFileEvent(WatchKey watchKey, WatchEvent watchEvent, Path path) {
        if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {

        }
        if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_DELETE) {

        }
        if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {

        }
        if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {

        }
    }

    public void watchDirEvent(WatchKey watchKey, WatchEvent watchEvent, Path path) {
        if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
            FileWatchUtil fileWatchUtil = new FileWatchUtil(path.toFile().getAbsolutePath());
            fileWatchUtil.start();
        }
        if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
            path.toFile().deleteOnExit();
            watchKey.cancel();
        }
        if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
        }
        if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
        }
    }

    public static void main(String[] args) {
        FileWatchUtil fileWatchUtil = new FileWatchUtil("D:\\data\\PC-201903031412_48435a1fde62\\20190428180901245000\\201905081104");
        fileWatchUtil.start();
    }
}
