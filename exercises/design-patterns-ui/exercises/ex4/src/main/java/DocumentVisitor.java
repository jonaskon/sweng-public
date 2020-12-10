package ex4;

public interface DocumentVisitor {
    void visit(PlainText part);
    void visit(HyperLink part);
    void visit(BoldText part);
}
