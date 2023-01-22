package lisp.eval.specialform;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Quote implements SpecialForm {

    /**
     * <p>1つのS式を受け取り、評価しないでそのまま返す</p>
     * <p>(quote sexp)</p>
     *
     * @param sexp 要素が1つのリスト
     * @return 渡されたS式
     */
    public SExpression apply(SExpression sexp, Environment env) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if (sexpList.size() != 1) {
            throw new LispException("特殊形式quoteの引数は1個である必要があります");
        }

        return sexpList.get(0);
    }

    @Override
    public String toString() {
        return "#<syntax quote>";
    }

}
