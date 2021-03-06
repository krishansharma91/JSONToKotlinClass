package com.mighty16.json.generator;

import com.mighty16.json.core.AnnotationGenerator;
import com.mighty16.json.core.FileSaver;
import com.mighty16.json.core.LanguageResolver;
import com.mighty16.json.core.models.ClassModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by krishan on 11/3/18.
 */
public class DomainModelGenerator extends KotlinFileGenerator {

    private String fileName;

    public DomainModelGenerator(String fileName, LanguageResolver resolver, AnnotationGenerator annotations, FileSaver fileSaver) {
        super(resolver, annotations, fileSaver);
        this.fileName = fileName;
    }

    @Override
    public void generateFiles(String packageName, List<ClassModel> classDataList) {

        final StringBuilder resultFileData = generateDataFile(packageName, classDataList);

        fileSaver.saveFile(resolver.getFileName(fileName), resultFileData.toString());

        if (listener != null) {
            listener.onFilesGenerated(2);
        }
    }

    @NotNull
    private StringBuilder generateDataFile(String packageName, List<ClassModel> classDataList) {
        final StringBuilder resultFileData = new StringBuilder();
        resultFileData.append(String.format(PACKAGE_BLOCK, packageName));

        int initialLength = resultFileData.length();

        if (annotations != null) {
            resultFileData.insert(initialLength, "\n" + annotations.getImportString() + "\n\n");
        }

        for (ClassModel classData : classDataList) {
            String content = generateFileContentForClass(classData, annotations) + "\n\n\n";
            resultFileData.append(content);
        }
        return resultFileData;
    }

}
