package lisp.eval;

import lisp.exception.LispException;

/**
 * 組み込み手続き
 * 
 * @author tetsuya
 *
 */

public interface Subroutine extends SExpression {

    // 手続きを実行するメソッド
    SExpression apply(SExpression sexp) throws LispException;
}
