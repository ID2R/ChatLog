package dev.id2r.chatlog.common.dependency;

import dev.id2r.chatlog.common.plugin.PlatformPlugin;
import lombok.Data;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;

/**
 * @author DirectPlan
 */
public class LibraryLoader {

    private final File DEPENDENCY_DIRECTORY = new File("dependencies/");
    private final Deque<CachedDependency> cachedDependencies = new ArrayDeque<>();
    private final PlatformPlugin<?> plugin;

    public LibraryLoader(final PlatformPlugin<?> plugin) {
        this.plugin = plugin;
        if(DEPENDENCY_DIRECTORY.mkdirs()) {
            plugin.getLogger().log(Level.INFO, String.format("Created dependency directory '%s'...", DEPENDENCY_DIRECTORY.getPath()));
        }
    }

    public void loadDependency(Dependency dependency, boolean load) {

        File file = downloadDependency(dependency);
        if(file == null) { // Could be caused by server connectivity or unbound file
            return;
        }
        cachedDependencies.addFirst(new CachedDependency(file));

        if(load) {
            loadDependencyToRuntime(file);
        }
    }

    public void loadDependency(Dependency dependency) {
        loadDependency(dependency, true);
    }

    public void loadDependencyToRuntime(File file) {
        plugin.getLogger().log(Level.INFO, String.format("Loading dependency '%s' to runtime...", file.getName()));
        try {
            java.net.URL url = file.toURI().toURL();
            java.lang.reflect.Method method = java.net.URLClassLoader.
                    class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true); /*promote the method to public access*/
            method.invoke(Thread.currentThread().getContextClassLoader(), url);
        } catch (Exception ex) {
            throw new RuntimeException("Cannot load dependency from jar file '" + file.getAbsolutePath() + "'. Reason: " + ex.getMessage());
        }
    }

    public void loadDependenciesFromFolder(File dependencyDirectory) {

        File[] files = dependencyDirectory.listFiles();
        if(files == null || files.length == 0) {
            plugin.getLogger().log(Level.SEVERE, String.format("The specified directory '%s' does not exist", dependencyDirectory.getPath()));
            return;
        }
        int trackedAmount = 0;
        for (File file : files) {
            delay();
            loadDependencyToRuntime(file);
            trackedAmount++;
        }
        plugin.getLogger().log(Level.INFO, String.format("Loaded %d dependencies!", trackedAmount));
    }
    public void loadDependencies() {
        plugin.getLogger().log(Level.INFO, "Loading libraries...");
        loadDependenciesFromFolder(DEPENDENCY_DIRECTORY);
    }

    public void loadDependencies(Class<?> clazz) {
        plugin.getLogger().log(Level.INFO, "Loading libraries...");

        MavenDependency[] dependencies =  clazz.getAnnotationsByType(MavenDependency.class);
        for (MavenDependency dependency : dependencies) {
            String value = dependency.value();
            String groupId = dependency.groupId();
            String artifactId = dependency.artifactId();
            String version = dependency.version();
            if (!value.isEmpty()) {
                String[] args = value.split("\\|");
                if (args.length == 3) {
                    groupId = args[0];
                    artifactId = args[1];
                    version = args[2];
                }
            }
            loadDependency(groupId, artifactId, version, false);
        }

        for(CachedDependency cachedDependency : cachedDependencies) {
            loadDependencyToRuntime(cachedDependency.getDependencyFile());
            delay();
        }
        plugin.getLogger().log(Level.INFO, String.format("Loaded %d dependencies!", cachedDependencies.size()));
        cachedDependencies.clear();
    }

    private void delay() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void loadDependency(String groupId, String artifactId, String version, boolean load) {
        loadDependency(new Dependency(groupId, artifactId, version, "https://repo1.maven.org/maven2"), load);
    }

    private File downloadDependency(Dependency dependency) {

        String displayName = dependency.getArtifactId() + "-" + dependency.getVersion();
        File file = new File(DEPENDENCY_DIRECTORY, displayName + ".jar");
        boolean existed = true;
        if(!file.exists()) {
            existed = false;
            try {
                plugin.getLogger().log(Level.INFO, String.format("Downloading dependency '%s'...", displayName));
                URL url = dependency.getUrl();
                try (InputStream is = url.openStream()) {
                    Files.copy(is, file.toPath());
                }

                plugin.getLogger().log(Level.INFO, String.format("Download for dependency '%s' has complete.", displayName));
                return file;
            } catch (Exception ignored) {}
        }
        if(!existed) { // This means that it went through the download process and failed
            plugin.getLogger().log(Level.INFO, "Could not download dependency: " + displayName);
            return null;
        }
        return file;
    }

    @Data
    public static class Dependency {

        private final String groupId;
        private final String artifactId;
        private final String version;
        private final String repoUrl;

        public URL getUrl() throws MalformedURLException {
            String repo = this.repoUrl;
            if (!repo.endsWith("/")) {
                repo += "/";
            }
            repo += "%s/%s/%s/%s-%s.jar";

            String url = String.format(repo, this.groupId.replace(".", "/"), this.artifactId, this.version, this.artifactId, this.version);
            return new URL(url);
        }
    }

    @Data
    public static class CachedDependency {
        private final File dependencyFile;
    }
}