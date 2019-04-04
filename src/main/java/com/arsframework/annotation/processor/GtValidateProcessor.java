package com.arsframework.annotation.processor;

import javax.annotation.processing.SupportedAnnotationTypes;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.arsframework.annotation.Gt;

/**
 * 参数大于校验注解处理器
 *
 * @author yongqiang.wu
 */
@SupportedAnnotationTypes("com.arsframework.annotation.Gt")
public class GtValidateProcessor extends AbstractValidateProcessor {

    @Override
    protected JCTree.JCIf buildValidateCondition(Symbol.VarSymbol param) {
        Gt gt = Validates.lookupAnnotation(param, Gt.class);
        JCTree.JCExpression expression = Validates.buildGtExpression(maker, names, param, gt.value());
        return expression == null ? null : maker.If(expression,
                maker.Throw(Validates.buildExceptionExpression(maker, names, gt.exception(),
                        String.format(gt.message(), param.name.toString(), gt.value()))), null);
    }
}