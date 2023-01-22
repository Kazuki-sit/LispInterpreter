package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.eval.Float;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Add implements Subroutine {

    /**
     * <p>渡されたリストの要素を全て足し合わせる</p>
     * <p>(+ sexp1 sexp2 ...)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return リストの要素の総和
     */
    public SExpression apply(SExpression sexp) throws LispException{
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);
        boolean isInteger = true;
        int intResult = 0;
        float floatResult = 0;

        for(SExpression se : sexpList) {
            if (se instanceof Int) {
                if (isInteger) {
                    intResult += ((Int) se).getValue();
                }
                floatResult += ((Int) se).getValue();

            } else if (se instanceof Float) {
                floatResult += ((Float) se).getValue();
                isInteger = false;

            } else {
                // 数値でなければエラーを投げる
                throw new LispException("手続きaddの引数は数値である必要があります");
            }
        }

        // 答えに合わせてS式を返す
        if (isInteger) {
            return Int.valueOf(intResult);
        } else {
            return Float.valueOf(floatResult);
        }
    }

    @Override
    public String toString() {
        return "#<subr +>";
    }
}
