package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.eval.Float;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Sub implements Subroutine {

    /**
     * <p>渡された要素数が1の場合は、要素の符号を反転させて返す</p>
     * <p>要素数が2以上の場合は、最初の要素から2つ目以降の要素を引き続ける</p>
     * <p>(- sexp1 sexp2 ...)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return 計算されたS式（Int, Float）
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);
        boolean isInteger = true;
        int intResult = 0;
        float floatResult = 0;
        int index = 0;

        if (sexpList.size() == 0) {
            throw new LispException("手続きsubの引数は1個以上である必要があります");
        }

        for (SExpression se : sexpList) {
            if(se instanceof Int) {
                if (index == 0) {
                    // 最初の要素は引き算せずに代入
                    intResult = ((Int) se).getValue();
                    floatResult = ((Int) se).getValue();

                } else {
                    // それ以外は最初の要素から引き続ける
                    if (isInteger) {
                        intResult -= ((Int) se).getValue();
                    }
                    floatResult -= ((Int) se).getValue();
                }

            } else if(se instanceof Float) {
                if (index == 0) {
                    // 最初の要素は引き算せずに代入
                    floatResult = ((Float) se).getValue();

                } else {
                    // それ以外は最初の要素から引き続ける
                    floatResult -= ((Float) se).getValue();
                }
                isInteger = false;

            } else {
                // 数値でなければエラーを投げる
                throw new LispException("リストの要素が数値ではありません");
            }

            index++;
        }

        if (sexpList.size() == 1) {
            intResult *= -1;
            floatResult *= -1;
        }

        if (isInteger) {
            return Int.valueOf(intResult);
        } else {
            return Float.valueOf(floatResult);
        }
    }

    @Override
    public String toString() {
        return "#<subr ->";
    }

}
