package lisp.eval.specialform;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Begin implements SpecialForm {

    /**
     * <p>与えられたS式を評価し、最後に評価した式の評価値をこの特殊形式の評価値とする</p>
     * <p>(begin sexp1 sexp2 ...)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @param env 環境
     * @return 評価された最後の値
     * @throws LispException
     */
    public SExpression apply(SExpression sexp, Environment env) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if(sexpList.size() < 1){
            throw new LispException("特殊形式beginの引数は1個以上である必要があります");
        }

        SExpression result = null;

        for (SExpression se : sexpList) {
            result = Evaluator.eval(se, env);
        }

        return result;
    }

    @Override
    public String toString() {
        return "#<syntax begin>";
    }
}
