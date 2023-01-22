package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Cons implements Subroutine {

    /**
     * <p>渡されたS式のドット対を作成する</p>
     * <p>(cons sexp1 sexp2)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return 評価されたS式（ドット対）
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if(sexpList.size() != 2){
            throw new LispException("手続きconsの引数は2個である必要があります");
        }

        SExpression car = sexpList.get(0);
        SExpression cdr = sexpList.get(1);
        return ConsCell.getInstance(car, cdr);
    }

    @Override
    public String toString() {
        return "#<subr cons>";
    }

}
