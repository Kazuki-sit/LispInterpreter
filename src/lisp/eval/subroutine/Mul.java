package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.eval.Float;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Mul implements Subroutine {

    /**
     * <p>渡されたリストの要素のすべての積を返す</p>
     * <p>要素数が0のときは1を返す</p>
     * <p>(* sexp1 sexp2 ...)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return 計算されたS式（Int, Float）
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);
        boolean isInteger = true;
        int intResult = 1;
        float floatResult = 1;

        for (SExpression se : sexpList) {
            if (se instanceof Int) {
                if (isInteger) {
                    intResult *= ((Int) se).getValue();
                }
                floatResult *= ((Int) se).getValue();

            } else if (se instanceof Float) {
                floatResult *= ((Float) se).getValue();
                isInteger = false;

            } else {
                // 数値でなければエラーを投げる
                throw new LispException("手続きmulの引数は数値である必要があります");
            }
        }

        if (isInteger) {
            return Int.valueOf(intResult);
        } else {
            return Float.valueOf(floatResult);
        }
    }

    @Override
    public String toString() {
        return "#<subr *>";
    }
}
