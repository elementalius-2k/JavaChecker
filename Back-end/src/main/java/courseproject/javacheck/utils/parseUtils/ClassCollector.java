package courseproject.javacheck.utils.parseUtils;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class ClassCollector extends VoidVisitorAdapter<List<String>> {
    @Override
    public void visit(ClassOrInterfaceDeclaration cid, List<String> collector) {
        super.visit(cid, collector);

        StringBuilder sb = new StringBuilder();

        if (cid.isInterface())
            sb.append("\nInterface: ");
        else if (cid.isInnerClass())
            sb.append("\nInner class: ");
        else
            sb.append("\nClass: ");

        sb.append(cid.getFullyQualifiedName().orElse(cid.getNameAsString()));

        sb.append("\n\tModifiers:");
        NodeList<Modifier> classModifiers = cid.getModifiers();
        classModifiers.forEach(curr -> sb.append(" ").append(curr.getKeyword().toString()));

        sb.append("\n\tExtended types: ");
        NodeList<ClassOrInterfaceType> extendedTypes = cid.getExtendedTypes();
        extendedTypes.forEach(curr -> sb.append(" ").append(curr.getName()));

        sb.append("\n\tImplemented types: ");
        NodeList<ClassOrInterfaceType> implementedTypes = cid.getImplementedTypes();
        implementedTypes.forEach(curr -> sb.append(" ").append(curr.getName()));

        sb.append("\n\tFields:");
        List<FieldDeclaration> fields = cid.getFields();
        fields.forEach(currFiled -> {
            NodeList<Modifier> fieldModifiers = currFiled.getModifiers();
            NodeList<VariableDeclarator> vars = currFiled.getVariables();
            vars.forEach(var -> {
                sb.append("\n\t\t");
                fieldModifiers.forEach(curr -> sb.append(curr.getKeyword()).append(" "));
                sb.append(var.getType().asString()).append(" ").append(var.getName().asString());
            });
        });

        sb.append("\n\tMethods:");
        List<MethodDeclaration> methods = cid.getMethods();
        methods.forEach(currMethod -> sb.append("\n\t\t")
                .append(currMethod.getDeclarationAsString(true,true,true)));

        collector.add(sb.toString());
    }
}
