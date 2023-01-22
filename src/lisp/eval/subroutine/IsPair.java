package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class IsPair implements Subroutine{

    /**
     * <p>式 exp がドット対であれば真 (#t)</p>
     * <p>そうでなければ偽 (#f)</p>
     * <p>(null exp)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return 真偽値
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if (sexpList.size() != 1) {
            throw new LispException("手続きpair?の引数は1個である必要があります");
        }

        if (sexpList.get(0) instanceof ConsCell) {
            return Bool.valueOf(true);

        } else {
            return Bool.valueOf(false);
        }
    }

    @Override
    public String toString() {
        return "#<subr pair?>";
    }
}
