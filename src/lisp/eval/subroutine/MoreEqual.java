package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.eval.Float;
import lisp.exception.LispException;

import java.util.ArrayList;

public class MoreEqual implements Subroutine {

    /**
     * <p>二つ以上の引数 (全て数値) をとる</p>
     * <p>引数が単調非増加 (exp1 ≥ exp2 ≥ · · ·) ならば#t、そうでなければ#f.</p>
     * <p>(>= sexp1 sexp2 ...)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return 真偽値
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException{
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if(sexpList.size() < 2){
            throw new LispException("手続き>=の引数は2個以上である必要があります");
        }

        for (int i = 1; i < sexpList.size(); i++) {
            SExpression exp1 = sexpList.get(i-1);
            SExpression exp2 = sexpList.get(i);

            if (exp1 instanceof Int && exp2 instanceof Int) {
                if (((Int) exp1).getValue() >= ((Int) exp2).getValue()) {
                    continue;
                } else {
                    return Bool.valueOf(false);
                }
            }

            if (exp1 instanceof Float && exp2 instanceof Float) {
                if (((Float) exp1).getValue() >= ((Float) exp2).getValue()) {
                    continue;
                } else {
                    return Bool.valueOf(false);
                }
            }

            if (!((exp1 instanceof Int || exp1 instanceof Float) && (exp2 instanceof Int || exp2 instanceof Float))) {
                // 数値ではない場合はエラーを返す
                throw new LispException("手続き>=の引数は数値である必要があります");
            }

            return Bool.valueOf(false);
        }

        return Bool.valueOf(true);
    }

    @Override
    public String toString() {
        return "#<subr >=>";
    }
}
