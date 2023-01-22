package lisp.eval.subroutine;

import lisp.eval.SExpression;
import lisp.eval.Subroutine;
import lisp.exception.LispException;

public class Exit implements Subroutine {

    /**
     * <p>プログラムを終了させる</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return null
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {
        System.exit(0);
        return null;
    }

    @Override
    public String toString() {
        return "#<subr exit>";
    }
}
