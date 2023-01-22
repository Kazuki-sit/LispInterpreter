package lisp.eval;

import lisp.exception.LispException;

/**
 * 特殊形式
 * 
 * @author tetsuya
 *
 */
public interface SpecialForm extends SExpression{

    // 手続きを実行するメソッド
    SExpression apply(SExpression sexp, Environment env) throws LispException;

}
