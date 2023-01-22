package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class List_ref implements Subroutine {

    /**
     * <p>引数のリストのindex番目の要素を返す</p>
     * <p>(list-ref list index)</p>
     *
     * @param sexp リストとインデックス
     * @return リストの要素
     * @throws LispException 引数、型、要素の範囲外
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if(sexpList.size() != 2){
            throw new LispException("手続きlist-refの引数は2個である必要があります");
        }

        // 変数の宣言
        int index;
        ArrayList<SExpression> list;

        if (sexpList.get(1) instanceof Int) {
            // Int型なら要素の番号をindexに代入する
            index = ((Int) sexpList.get(1)).getValue();

        } else {
            // 整数でなければエラーを返す
            throw new LispException("手続きlist-refの要素数の引数はInt型である必要があります");
        }

        list = ConsCell.toArrayList(sexpList.get(0));

        if(!(0 <= index && index < list.size())) {
            // indexが要素の範囲内でなければエラーを返す
            throw new LispException("手続きlist-refの要素数の引数はリストの範囲内である必要があります");
        }

        return list.get(index);
    }

    @Override
    public String toString() {
        return "#<subr list-ref>";
    }

}
