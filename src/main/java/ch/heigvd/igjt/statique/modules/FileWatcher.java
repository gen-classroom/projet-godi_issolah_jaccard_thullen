package ch.heigvd.igjt.statique.modules;

import ch.heigvd.igjt.statique.data.SiteConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Watches a given directory for file changes
 *
 * @author Basile Thullen
 */
public class FileWatcher {

    String watchPathString;
    Path watchPath;
    SiteConfig siteConfig;
    ContentFileProcessor contentFileProcessor;
    TemplateEngine templateEngine;

    /**
     * The constructor to the FileWatcher class
     * @param watchPathString the path on which to watch for changes.
     *                        Must be the root directory of the site
     */
    public FileWatcher(String watchPathString) {
        this.watchPathString = watchPathString;
        this.watchPath = Paths.get(watchPathString);
        File configFile = new File(watchPathString + "/config.yaml");
        this.contentFileProcessor = new ContentFileProcessor();
        this.siteConfig = this.contentFileProcessor.getSiteConfigFromYaml(configFile);
        this.templateEngine = new TemplateEngine(siteConfig, watchPathString + "/template");
        this.contentFileProcessor = new ContentFileProcessor();
    }

    /**
     * Start the FileWatcher service
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the watch service is interrupted by an exception
     */
    public void start() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(watchPathString);
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
    private void processEvent(WatchEvent<?> event) throws IOException {
        WatchEvent.Kind<?> kind = event.kind();
        Path eventPath = (Path) event.context();
        Path eventPathParent = eventPath;
        File eventFile = eventPath.toFile();
        int depth = eventPath.getNameCount();
        // ./build/a/b/C.java --> getNameCount = 4
        for (int i = 0; i < depth-1; i++) {
            eventPathParent = eventPathParent.getParent();
        }
        // ./build
        if (!eventPathParent.endsWith("build")) {
            if (kind == ENTRY_CREATE) {
                // process new file
                SiteBuilder.buildFile(eventPathParent.toFile(), eventFile, templateEngine, contentFileProcessor);
            } else if (kind == ENTRY_DELETE) {
                // process deleted file
                SiteBuilder.recursiveDelete(eventFile);
            } else if (kind == ENTRY_MODIFY) {
                // process modified file
                SiteBuilder.buildFile(eventPathParent.toFile(), eventFile, templateEngine, contentFileProcessor);
            }
        }
    }

}
