package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Append implements Subroutine {

    /**
     * <p>引数のリストを連結した新しいリストを返す</p>
     * <p>(append list ...)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return S式（リスト）
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);
        ArrayList<SExpression> result = new ArrayList<>();

        if(sexpList.size() == 0){
            throw new LispException("手続きappendの引数は1個以上である必要があります");
        }

        for (SExpression se : sexpList) {
            ArrayList<SExpression> sl = ConsCell.toArrayList(se);
            result.addAll(sl);
        }

        return ConsCell.toLispList(result);
    }

    @Override
    public String toString() {
        return "#<subr append>";
    }
}
