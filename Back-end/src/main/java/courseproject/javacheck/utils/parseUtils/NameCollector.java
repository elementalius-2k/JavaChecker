package courseproject.javacheck.utils.parseUtils;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;
import java.util.Set;

public class NameCollector extends VoidVisitorAdapter<Set<String>> {
    @Override
    public void visit(ClassOrInterfaceDeclaration cid, Set<String> collector) {
        super.visit(cid, collector);

        if (cid.isInterface())
            collector.add("Interface_" + cid.getNameAsString());
        else if (!cid.getNameAsString().equals("Main"))
            collector.add("Class_" + cid.getNameAsString());

        List<FieldDeclaration> fields = cid.getFields();
        fields.forEach(field -> {
            NodeList<VariableDeclarator> vars = field.getVariables();
            vars.forEach(var -> collector.add("Field_" + var.getType().asString() + "_" + var.getName().asString()));
        });

        List<MethodDeclaration> methods = cid.getMethods();
        methods.forEach(method -> {
            if (!method.getName().asString().equals("main"))
            collector.add("Method_" + method.getType().asString() + "_" + method.getName().asString());
            NodeList<Parameter> params = method.getParameters();
            params.forEach(param -> collector.add(param.getType().asString() + "_" + param.getName().asString()));
        });
    }
}
