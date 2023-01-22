package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;
import java.util.HashMap;

public class Assoc implements Subroutine {

    /**
     * <p>指定したキーを持つ対を連想リストから検索する</p>
     * <p>(assoc key list)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return キーに紐づけられたS式
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if(sexpList.size() != 2){
            throw new LispException("手続きassocの引数は2個である必要があります");
        }

        SExpression key = sexpList.get(0);
        HashMap<SExpression, SExpression> alist = ConsCell.toHashMap(sexpList.get(1));

        // 要素が見つかれば、値を返す
        if (alist.containsKey(key)) {
            return ConsCell.getInstance(key, alist.get(key));
        }

        // 要素が見つからなければ偽を返す
        return Bool.valueOf(false);
    }

    @Override
    public String toString() {
        return "#<subr assoc>";
    }
}
