package cn.demonk.processor;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;

/**
 * Created by ligs on 8/21/16.
 */
public class AnnotatedClass {
    private String className;
    private String packageName;
    private List<AnnotatedField> fields = new LinkedList<AnnotatedField>();

    public AnnotatedClass(String packageName, String className) {
        this.className = className;
        this.packageName = packageName;
    }

    public void generateCode(Elements elementUtils, Filer filer) {
        //使用javapoet生成代码
        TypeSpec.Builder customInterface = TypeSpec.interfaceBuilder("I" + className).addModifiers(Modifier.PUBLIC);

        for (AnnotatedField field : fields) {
            FieldSpec.Builder myField = FieldSpec.builder(TypeName.OBJECT, field.getName(), Modifier.PROTECTED);
            customInterface.addField(myField.build());
        }

        JavaFile javaFile = JavaFile.builder(packageName, customInterface.build()).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addField(AnnotatedField field) {
        this.fields.add(field);
    }
}
