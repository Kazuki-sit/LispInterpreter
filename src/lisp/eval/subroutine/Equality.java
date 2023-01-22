package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.eval.Float;
import lisp.exception.LispException;

import java.util.ArrayList;
import java.util.Objects;

public class Equality implements Subroutine {

    /**
     * <p>2つ以上の引数（すべて数値）をとり、引数がすべて等しければtrueを返す</p>
     * <p>(= sexp1 sexp2 ...)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return 真偽値
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if (sexpList.size() < 2) {
            throw new LispException("手続き=の引数は2個以上である必要があります");
        }

        for (int i = 1; i < sexpList.size(); i++) {
            SExpression exp1 = sexpList.get(i-1);
            SExpression exp2 = sexpList.get(i);

            if (exp1 instanceof Int && exp2 instanceof Int) {
                if (Objects.equals(exp1.getValue(), exp2.getValue())) {
                    continue;
                } else {
                    return Bool.valueOf(false);
                }
            }

            if (exp1 instanceof Float && exp2 instanceof Float) {
                if (Objects.equals(exp1.getValue(), exp2.getValue())) {
                    continue;
                } else {
                    return Bool.valueOf(false);
                }
            }

            if (!((exp1 instanceof Int || exp1 instanceof Float) && (exp2 instanceof Int || exp2 instanceof Float))) {
                // 数値ではない場合はエラーを返す
                throw new LispException("手続き=の引数は数値である必要があります");
            }

            return Bool.valueOf(false);
        }

        return Bool.valueOf(true);
    }

    @Override
    public String toString() {
        return "#<subr =>";
    }

}
