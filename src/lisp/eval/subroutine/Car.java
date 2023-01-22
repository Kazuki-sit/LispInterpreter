package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Car implements Subroutine {

    /**
     * <p>ドット対のCarを取り出す</p>
     * <p>(car ConsCell)</p>
     *
     * @param sexp リスト（要素数1）
     * @return リストの1つめの要素のCar部
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if (sexpList.size() != 1) {
            throw new LispException("手続きcarの引数は1個である必要があります");
        }

        if (sexpList.get(0) instanceof ConsCell) {
            // 渡されたドット対のcar部を返す
            return ((ConsCell) sexpList.get(0)).getCar();

        } else {
            // 違うクラスの場合はMainにエラーを投げる
            throw new LispException("手続きcarの引数はドット対である必要があります");
        }
    }

    @Override
    public String toString() {
        return "#<subr car>";
    }
}
