package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Write implements Subroutine {

    /**
     * <p>引数で与えられたデータの文字列表現を表示する</p>
     *
     * @param sexp 表示するS式
     * @return 未定義値
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if (sexpList.size() != 1) {
            throw new LispException("手続きwriteの引数は1個である必要があります");
        }

        System.out.print(sexpList.get(0));
        return Undef.getInstance();
    }

    @Override
    public String toString() {
        return "#<subr write>";
    }
}
