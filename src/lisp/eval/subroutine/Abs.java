package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.eval.Float;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Abs implements Subroutine {

    /**
     * <p>引数の絶対値を返す</p>
     * <p>(abs exp)</p>
     *
     * @param sexp S式
     * @return 絶対値
     * @throws LispException 引数、型
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if(sexpList.size() != 1){
            throw new LispException("手続きabsの引数は1個である必要があります");
        }

        if (sexpList.get(0) instanceof Int) {
            int result = Math.abs(((Int) sexpList.get(0)).getValue());
            return Int.valueOf(result);

        } else if(sexpList.get(0) instanceof Float) {
            float result = Math.abs(((Float) sexpList.get(0)).getValue());
            return Float.valueOf(result);
        }else{
            // 数値でなければエラーを返す
            throw new LispException("手続きabsの引数は数値である必要があります");
        }
    }

    @Override
    public String toString() {
        return "#<subr abs>";
    }
}
