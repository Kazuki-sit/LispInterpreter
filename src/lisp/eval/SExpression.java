package lisp.eval;

import lisp.exception.LispException;

/*
 * S式
 */
public interface SExpression {

    default Object getValue(){
        return null;
    }
}
