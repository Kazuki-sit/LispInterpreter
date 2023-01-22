package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.eval.Float;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Div implements Subroutine {

    /**
     * <p>渡された要素数が0の場合は、エラーを返す</p>
     * <p>渡された要素数が1の場合は、渡された要素の逆数を返す</p>
     * <p>要素数が2以上の場合は、最初の要素から2つ目以降の要素を割り続ける</p>
     * <p>Int型の計算では小数点以下は切り捨てされる</p>
     * <p>(/ sexp1 sexp2 ...)</p>
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
            throw new LispException("手続きdivの引数は1個以上である必要があります");
        }

        for (SExpression se : sexpList) {
            if(se instanceof Int) {
                if (index == 0) {
                    // 最初の要素は割り算せずに代入
                    intResult = ((Int) se).getValue();
                    floatResult = ((Int) se).getValue();

                } else if(((Int) se).getValue() == 0) {
                    throw new LispException("手続きdivでは0で割ることはできません");

                } else {
                    // それ以外は最初の要素から割り続ける
                    if (isInteger) {
                        intResult /= ((Int) se).getValue();
                    }
                    floatResult /= ((Int) se).getValue();
                }

            } else if(se instanceof Float) {
                if (index == 0) {
                    // 最初の要素は割り算せずに代入
                    floatResult = ((Float) se).getValue();

                } else if(((Float) se).getValue() == 0) {
                    throw new LispException("手続きdivでは0で割ることはできません");

                } else {
                    // それ以外は最初の要素から割り続ける
                    floatResult /= ((Float) se).getValue();
                }
                isInteger = false;

            } else {
                // 数値でなければエラーを投げる
                throw new LispException("手続きdivの引数は数値である必要があります");
            }

            index++;
        }

        if (isInteger) {
            return Int.valueOf(intResult);
        } else {
            return Float.valueOf(floatResult);
        }
    }

    @Override
    public String toString() {
        return "#<subr />";
    }
}
