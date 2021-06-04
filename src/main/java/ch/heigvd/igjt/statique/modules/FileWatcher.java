package ch.heigvd.igjt.statique.modules;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * @author Basile Thullen
 * Watch a given directory for file changes
 */
public class FileWatcher {

    String watchPath;
    ContentFileProcessor processor;

    public FileWatcher(String watchPath) {
        this.watchPath = watchPath;
        this.processor = new ContentFileProcessor();
    }

    /**
     * Start the FileWatcher service
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the watch service is interrupted by an exception
     */
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

    /**
     * Process the given file change notification event
     * @param event the file change event to process
     */
    private void processEvent(WatchEvent<?> event) {
        WatchEvent.Kind<?> kind = event.kind();
        Path eventPath = (Path) event.context();
        Path eventPathParent = eventPath;
        int depth = eventPath.getNameCount();
        // ./build/a/b/C.java --> getNameCount = 4
        for (int i = 0; i < depth-1; i++) {
            eventPathParent = eventPathParent.getParent();
        }
        // ./build
        if (!eventPathParent.endsWith("build")) {
            if (kind == ENTRY_CREATE) {
                // process new file; skeleton
                SiteBuilder.buildFile(eventPath);
            } else if (kind == ENTRY_DELETE) {
                // process deleted file
                SiteBuilder.delete(eventPath);
            } else if (kind == ENTRY_MODIFY) {
                // process modified file
                SiteBuilder.build(eventPath);
            }
        }
    }

}
