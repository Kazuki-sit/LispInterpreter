package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Not implements Subroutine {

    /**
     * <p>test の評価値が#f ならば#t</p>
     * <p>それ以外ならば#f</p>
     * <p>(not test)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return 真偽値
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if (sexpList.size() != 1) {
            throw new LispException("手続きnotの引数は1個である必要があります");
        }

        SExpression test = sexpList.get(0);
        if (test instanceof Bool) {
            if (((Bool) test).getValue()) {
                return Bool.valueOf(false);

            }else {
                return Bool.valueOf(true);
            }

        } else {
            // 引数が真偽値でなければエラーを返す
            throw new LispException("手続きnotの引数は真偽値である必要があります");
        }
    }

    @Override
    public String toString() {
        return "#<subr not>";
    }
}
