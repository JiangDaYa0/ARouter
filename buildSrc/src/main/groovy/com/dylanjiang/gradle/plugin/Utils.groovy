package com.dylanjiang.gradle.plugin

import com.android.build.gradle.api.ApkVariant
import com.android.build.gradle.api.BaseVariant
import com.google.common.io.Files
import groovy.io.FileType
import org.gradle.api.Project

import java.util.regex.Matcher
import java.util.regex.Pattern

public class Utils {

    public static List<File> findAwbDBFiles(Project project, ApkVariant variant) {
        List<File> libDBFiles = new LinkedList<>()
        File awbDir = new File(project.buildDir, 'intermediates/awb-assets')
        File[] files = awbDir.listFiles(new FilenameFilter() {

            @Override
            boolean accept(File file, String s) {
                return s.equalsIgnoreCase(variant.name)
            }
        });

        files[0].eachFileRecurse(FileType.FILES) { File f ->
            libDBFiles.add(f)
        }
        return libDBFiles
    }

    public static void renameDbFiles(List dbFiles, Project project, BaseVariant variant) {
        int l = dbFiles.size()
        for (int i = 0; i < l; ++i) {
            File dbFile = dbFiles.get(i)
            String pkg = getPkgName(dbFile)
            String targetDir = new File("${project.buildDir}/generated/source/apt",
                    "${variant.dirName}/${pkg.replace('.', '/')}")
            File newFile = new File(targetDir,
                    dbFile.name.substring(0,
                            dbFile.name.lastIndexOf('.')) + '.java')
            Files.copy(dbFile, newFile)
        }
    }

    private static String getPkgName(File javaFile) {
        String pkg
        Pattern pPkg = Pattern.compile(''' *package (?<pkg>.*?);.*''')
        javaFile.eachLine { String line ->
            Matcher m = pPkg.matcher(line)
            if (m.matches()) {
                pkg = m.group('pkg')
                return
            }
        }
        return pkg
    }

    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
