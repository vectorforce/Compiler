package ast;

import vars.Value;

public interface Expression {
    Value evaluate();
}
