package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Length implements Subroutine {

    /**
     * <p>渡されたリストの要素数を返す</p>
     * <p>(length list)</p>
     *
     * @param sexp S式（リスト）
     * @return S式（Int）
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if(sexpList.size() != 1){
            throw new LispException("手続きlengthの引数は1個である必要があります");
        }

        ArrayList<SExpression> list = ConsCell.toArrayList(sexpList.get(0));
        return Int.valueOf(list.size());
    }

    @Override
    public String toString() {
        return "#<subr length>";
    }
}
