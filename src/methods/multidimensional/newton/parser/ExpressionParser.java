package methods.multidimensional.newton.parser;

import methods.multidimensional.newton.expressions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ExpressionParser extends BaseParser implements Parser {
    private final static Map<String, Integer> OP_PRIORITIES = Map.of(
            "+", 1,
            "-", 1,
            "*", 2,
            "/", 2,
            "^", 3
    );
    private final static List<String> UNARY_OPS = List.of("ln", "log2", "pow2", "count");
    private final static int START_LEVEL = 0;
    private final static int LAST_LEVEL = 4;

    @Override
    public Expression parse(String expression) {
        setSource(new StringSource(expression));
        nextChar();
        Expression expr = parseLevel(START_LEVEL);
        if (ch != '\0') {
            throw error("Expected end of file, found: " + ch);
        }
        return expr;
    }

    private Expression parseLevel(final int level) {
        if (level == LAST_LEVEL) {
            return parseLexem();
        }
        Expression expression = parseLevel(level + 1);
        skipWhitespaces();
        String nowOperator;
        while ((nowOperator = takeOperator(level)) != null) {
            expression = createExpression(nowOperator, expression, parseLevel(level + 1));
            skipWhitespaces();

        }
        return expression;
    }

    private String takeOperator(int level) {
        for (String anyOp : getSetOfOperators(level)) {
            if (test(anyOp)) {
                return anyOp;
            }
        }
        return null;
    }

    private Expression parseLexem() {
        skipWhitespaces();
        if (test('(')) {
            Expression expression = parseLevel(START_LEVEL);
            expect(')');
//            nextChar();
            return expression;
        } else if (test('-')) {
            if (between('0', '9')) {
                return getConst(true);
            } else {
                return new Negate(parseLexem());
            }
        } else if (between('0', '9')) {
            return getConst(false);
        } else {
            StringBuilder str = new StringBuilder();
            while (between('a', 'z') || between('0', '9') || ch == '_' || ch == '{' || ch == '}') {
                str.append(ch);
                nextChar();
            }
            String lexem = str.toString();
            if (!lexem.isEmpty()) {
                if (UNARY_OPS.contains(lexem)) {
                    return createExpression(lexem, parseLexem());
                } else {
                    final int index = getVariable(lexem);
                    if (index >= 0) {
                        return new Variable(index);
                    } else {
                        throw error("Invalid argument or operator \"" + lexem + "\"");
                    }
                }
            } else {
                throw error("Expected argument, found " + (ch != '\0' ? ("'" + ch + "'") : "end of file"));
            }
        }
    }

    private Expression createExpression(String unaryOp, Expression expression) {
        switch (unaryOp) {
            case "ln":
                return new Ln(expression);
            default:
                throw error("Unrecognized operator: " + unaryOp);
        }
    }

    private Expression createExpression(String binaryOp, Expression a, Expression b) {
        switch (binaryOp) {
            case "+":
                return new Add(a, b);
            case "-":
                return new Subtract(a, b);
            case "*":
                return new Multiply(a, b);
            case "/":
                return new Divide(a, b);
            case "^":
                return new Pow(a, b);
            default:
                throw error("Unrecognized operator: " + binaryOp);
        }
    }

    private Const getConst(boolean isNegative) {
        StringBuilder str = new StringBuilder();
        if (isNegative) {
            str.append("-");
        }
        while (between('0', '9')) {
            str.append(ch);
            nextChar();
        }
        if (test('.')) {
            str.append(".");
            while (between('0', '9')) {
                str.append(ch);
                nextChar();
            }
        }
        try {
            return new Const(Double.parseDouble(str.toString()));
        } catch (NumberFormatException e) {
            throw error("Invalid number");
        }
    }

    private void skipWhitespaces() {
        while (Character.isWhitespace(ch)) {
            nextChar();
        }
    }

    private Set<String> getSetOfOperators(int level) {
        Set<String> ops = new HashSet<>();
        for (Map.Entry<String, Integer> entry : OP_PRIORITIES.entrySet()) {
            if (entry.getValue().equals(level)) {
                ops.add(entry.getKey());
            }
        }
        return ops;
    }

    private int getVariable(final String lexem) {
        // x_{0}, x_{10}
        if (!(lexem.length() >= 5 && lexem.startsWith("x_{") && lexem.endsWith("}"))) {
            return -1;
        } else {
            try {
                return Integer.parseInt(lexem.substring(3, lexem.length() - 1));
            } catch (final NumberFormatException e) {
                return -1;
            }
        }
    }
}
