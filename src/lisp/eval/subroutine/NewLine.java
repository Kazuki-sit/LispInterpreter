package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class NewLine implements Subroutine {

    /**
     * <p>改行する</p>
     *
     * @param sexp 引数はなし
     * @return 未定義値
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if (sexpList.size() != 0) {
            throw new LispException("手続きnewlineの引数は0個である必要があります");
        }

        System.out.println("");
        return Undef.getInstance();
    }

    @Override
    public String toString() {
        return "#<subr newline>";
    }
}
