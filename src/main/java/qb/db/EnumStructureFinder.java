package qb.db;

import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public final class EnumStructureFinder {
    private final Map<Class<?>, Set<Class<? extends Enum<?>>>> enumClassCache = new ConcurrentHashMap<>();

    @PostConstruct
    public void initialize() {
        loadAndCacheEnumClasses();
    }

    public Set<Class<? extends Enum<?>>> findEnumClassesImplementing(Class<?> interfaceClass) {
        return enumClassCache.getOrDefault(interfaceClass, Collections.emptySet());
    }

    private void loadAndCacheEnumClasses() {
        try {
            String packageName = this.getClass().getPackage().getName();
            String packagePath = this.getClass().getPackage().getName().replace('.', '/');

            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packagePath);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource.getProtocol().equals("file")) {
                    File directory = new File(resource.getFile());
                    scanDirectoryAndCache(directory, packageName);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to scan for enum classes during initialization", e);
        }
    }

    @SuppressWarnings("unchecked")
    private void scanDirectoryAndCache(File directory, String packageName) {
        File[] files = directory.listFiles();
        if (ObjectUtils.isNotEmpty(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    scanDirectoryAndCache(file, packageName + "." + file.getName());
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                    try {
                        Class<?> clazz = Class.forName(className);
                        if (clazz.isEnum()) {
                            Class<? extends Enum<?>> enumClass = (Class<? extends Enum<?>>) clazz;
                            cacheEnumInterfaces(enumClass);
                        }
                    } catch (ClassNotFoundException | NoClassDefFoundError ignored) {
                    }
                }
            }
        }
    }

    private void cacheEnumInterfaces(Class<? extends Enum<?>> enumClass) {
        Class<?>[] interfaces = enumClass.getInterfaces();
        for (Class<?> interfaceClass : interfaces) {
            enumClassCache.computeIfAbsent(interfaceClass, k -> new HashSet<>()).add(enumClass);
        }
        addSuperInterfaces(enumClass, enumClass);
    }
    private void addSuperInterfaces(Class<? extends Enum<?>> enumClass, Class<?> currentClass) {
        for (Class<?> interfaceClass : currentClass.getInterfaces()) {
            enumClassCache.computeIfAbsent(interfaceClass, k -> new HashSet<>()).add(enumClass);
            addSuperInterfaces(enumClass, interfaceClass);
        }
    }
}
