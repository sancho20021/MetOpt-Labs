package methods.multidimensional.newton.parser;

import methods.multidimensional.newton.expressions.Expression;

public interface Parser {
    Expression parse(String expression);
}
