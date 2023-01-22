package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.eval.Float;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Sin implements Subroutine {

    /**
     * <p>引数で渡された角度（S式）に対する正弦Sinを返す</p>
     * <p>引数の単位は弧度法で渡すこと</p>
     * <p>(sin theta)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return 角度に対する正弦（Float）
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if(sexpList.size() != 1){
            throw new LispException("手続きsinの引数は1個である必要があります");
        }

        SExpression theta = sexpList.get(0);
        float result;

        if (theta instanceof Int) {
            result = (float) Math.sin(((Int) theta).getValue());

        } else if(theta instanceof Float) {
            result = (float) Math.sin(((Float) theta).getValue());

        } else {
            // 数値でなければエラーを投げる
            throw new LispException("手続きsinの引数は数値である必要があります");
        }

        return Float.valueOf(result);
    }

    @Override
    public String toString() {
        return "#<subr sin>";
    }
}
