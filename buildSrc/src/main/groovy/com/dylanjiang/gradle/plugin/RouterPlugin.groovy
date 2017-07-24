package com.dylanjiang.gradle.plugin

import com.android.build.gradle.api.ApkVariant
import com.android.build.gradle.api.LibraryVariant
import groovy.io.FileType
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

public class RouterPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        if (project.plugins.findPlugin('com.taobao.atlas.application')) {
            hookAppProject(project)
            return
        }

        if (project.plugins.findPlugin('com.taobao.atlas.library')) {
            hookLibraryProject(project)
            return
        }

        if (project.plugins.findPlugin('com.taobao.atlas')) {
            if (project.plugins.findPlugin('com.android.application')) {
                hookAppProject(project)
                return
            }
            if (project.plugins.findPlugin('com.android.library')) {
                hookLibraryProject(project)
            }
        }
    }

    public static void findAllFiles(Project project, ApkVariant variant) {
        List<File> libDBFiles = Utils.findAwbDBFiles(project, variant)
        Utils.renameDbFiles(libDBFiles, project, variant)
     }

    private static void hookAppProject(Project project) {
        project.android.applicationVariants.all { ApkVariant variant ->
            def javac = variant.javaCompiler
            javac.doLast {
                findAllFiles(project, variant)
                String taskName = "atlasRouterJavac${System.currentTimeMillis()}"
                project.task(type: JavaCompile, overwrite: true, taskName) { JavaCompile jc ->
                    jc.with {
                        source = project.files(new File(project.buildDir, 'generated/source/apt/' + variant.name + '/com/alibaba/android/arouter/routes'))
                        destinationDir = javac.destinationDir
                        classpath = javac.classpath.plus(project.files(javac.destinationDir))
                        sourceCompatibility = javac.sourceCompatibility
                        targetCompatibility = javac.targetCompatibility
                    }
                }
                project.tasks.getByName(taskName).execute()
            }
        }
    }

    private static void hookBundleTask(Project project, LibraryVariant variant, File[] files) {
        project.tasks.getByName('bundle' + Utils.toUpperCaseFirstOne(variant.name)).doFirst() {
            def dirName = variant.name == 'release' ? 'default' : variant.name
            for (File file : files) {
                File txtFile = new File(project.buildDir.path + '/intermediates/bundles/' + dirName + '/assets', file.name.replace('.java', '.txt'))
                if (txtFile.exists()) {
                    txtFile.delete()
                }
                txtFile.text = file.text
            }
        }
    }

    private static void hookLibraryProject(Project project) {
        project.afterEvaluate {
            project.android.libraryVariants.all { LibraryVariant variant ->
                variant.javaCompiler.doLast {
                    File routeDir = new File(project.buildDir, 'generated/source/apt/' + variant.name + '/com/alibaba/android/arouter/routes')
                    File[] files = routeDir.listFiles()
                    hookBundleTask(project, variant, files)
                    List<String> filesName = new LinkedList<>()
                    for(File file: files) {
                        filesName.add(file.name.substring(0, file.name.indexOf('.')))
                    }
                    variant.javaCompiler.destinationDir.eachFileRecurse(FileType.FILES) { File f ->
                        def fileName = f.name.substring(0, f.name.indexOf('.'))
                        if (filesName.contains(fileName)) {
                            f.delete()
                        }
                    }
                }
            }
        }
    }
}