package cn.demonk.processor;

import java.lang.reflect.Executable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by ligs on 8/21/16.
 */
public class AnnotationProcessor extends AbstractProcessor {
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager message;

    public synchronized void init(ProcessingEnvironment env) {
        System.out.println("AnnotationProcess running");
        super.init(env);

        elementUtils = env.getElementUtils();
        filer = env.getFiler();
        typeUtils = env.getTypeUtils();
        message = env.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("AnnotationProcess processing");

        Map<String, AnnotatedClass> classmap = new HashMap<>();
        Set<? extends Element> elementSet = roundEnv.getElementsAnnotatedWith(CustomAnnotation.class);

        for (Element e : elementSet) {
            if (e.getKind() != ElementKind.FIELD) {
                log(e, "should add for field");
                return true;
            }

            ExecutableElement element = (ExecutableElement) e;

            AnnotatedClass clazz = classmap.get(element.getSimpleName().toString());
            AnnotatedField field = new AnnotatedField(element);

            String className = field.getWrapClass();

            if (field == null) {
                PackageElement pkg = elementUtils.getPackageOf(element);
                clazz = new AnnotatedClass(pkg.getQualifiedName().toString(), className);
                clazz.addField(field);
                classmap.put(className, clazz);
            } else {
                clazz.addField(field);
            }
        }

        for (AnnotatedClass clazz : classmap.values()) {
            clazz.generateCode(elementUtils, filer);
        }

        return false;
    }

    private void log(Element e, String msg) {
        message.printMessage(Diagnostic.Kind.ERROR, msg);
    }

    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton("cn.demonk.processor.CustomAnnotation");

    }

    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
        return SourceVersion.RELEASE_7;
    }
}
