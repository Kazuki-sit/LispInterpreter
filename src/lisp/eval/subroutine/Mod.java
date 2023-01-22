package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Mod implements Subroutine {

    /**
     * <p>引数のS式の剰余を求める</p>
     * <p>(mod sexp1 sexp2)</p>
     *
     * @param sexp リスト（要素数2）
     * @return それぞれの要素の余り
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if(sexpList.size() != 2){
            throw new LispException("手続きmoduloの引数は2個である必要があります");
        }

        SExpression x = sexpList.get(0);
        SExpression y = sexpList.get(1);

        if (x instanceof Int && y instanceof Int) {
            int result = ((Int) x).getValue() % ((Int) y).getValue();
            return Int.valueOf(result);

        } else {
            // Int型でなければエラーを返す
            throw new LispException("手続きmoduloの引数はInt型である必要があります");
        }
    }

    @Override
    public String toString() {
        return "#<subr modulo>";
    }
}
