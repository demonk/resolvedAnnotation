package cn.demonk.processor;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by ligs on 8/21/16.
 */
public class AnnotatedField {

    private Element mElement;

    public AnnotatedField(Element element) {
        this.mElement = element;
    }

    public String getName() {
        return this.mElement.getSimpleName().toString();
    }

    public String getWrapClass() {
        return ((TypeElement) this.mElement.getEnclosingElement()).getQualifiedName().toString();
    }
}
