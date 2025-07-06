package qb.definition.db.system;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

@Component
public class EnumStructureFinder {

    @SuppressWarnings("unchecked")
    public Set<Class<? extends Enum<?>>> findEnumClassesImplementing(Class<?> interfaceClass) {
        Set<Class<? extends Enum<?>>> enumClasses = new HashSet<>();

        try {
            String packageName = this.getClass().getPackage().getName();
            String packagePath = packageName.replace('.', '/');

            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packagePath);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource.getProtocol().equals("file")) {
                    File directory = new File(resource.getFile());
                    scanDirectory(directory, packageName, interfaceClass, enumClasses);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to scan for enum classes implementing " + interfaceClass.getSimpleName(), e);
        }

        return enumClasses;
    }

    @SuppressWarnings("unchecked")
    private void scanDirectory(File directory, String packageName, Class<?> interfaceClass, Set<Class<? extends Enum<?>>> enumClasses) {
        File[] files = directory.listFiles();
        if (ObjectUtils.isNotEmpty(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    scanDirectory(file, packageName + "." + file.getName(), interfaceClass, enumClasses);
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                    try {
                        Class<?> clazz = Class.forName(className);
                        if (clazz.isEnum() && interfaceClass.isAssignableFrom(clazz)) {
                            enumClasses.add((Class<? extends Enum<?>>) clazz);
                        }
                    } catch (ClassNotFoundException | NoClassDefFoundError ignored) {
                    }
                }
            }
        }
    }
}
