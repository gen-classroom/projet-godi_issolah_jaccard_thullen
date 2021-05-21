package ch.heigvd.igjt.statique.modules;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileWatcher {

    String watchPath;

    public FileWatcher(String watchPath) {
        this.watchPath = watchPath;
    }

    public void start() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(watchPath);
        path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        WatchKey key;
        while((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println("[FileWatcher] new event: " + event.kind() +". File affected: " + event.context());
                processEvent(event);
            }
            key.reset();
        }
    }

    private void processEvent(WatchEvent<?> event) {
        WatchEvent.Kind<?> kind = event.kind();
        if (kind == ENTRY_CREATE) {
            // process new file
        } else if (kind == ENTRY_DELETE) {
            // process deleted file
        } else if (kind == ENTRY_MODIFY) {
            // process modified file
        }
    }

}
