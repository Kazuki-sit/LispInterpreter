package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Apply implements Subroutine {

    /**
     * <p>第一引数の手続きを第二引数以降に適応する</p>
     * <p>procは手続き、argsはリストとする</p>
     * <p>(apply proc arg ... args )</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return すべての引数を引数の手続きで評価した値
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if (sexpList.size() < 2) {
            throw new LispException("手続きapplyの引数は2個以上である必要があります");
        }

        // 第一引数を抜き出す
        SExpression proc = sexpList.get(0);
        sexpList.remove(0);

        // 第一引数が手続きでなければエラーを返す
        if (!(proc instanceof Subroutine || proc instanceof Closure)) {
            throw new LispException("手続きapplyの第一引数は手続きである必要があります");
        }

        ArrayList<SExpression> result = new ArrayList<>();

        // 渡された手続きを実行する
        for (SExpression se : sexpList) {

            // リスト（ドット対、空リスト）ならそのまま引数として渡す
            if (ConsCell.isList(se)) {
                if (proc instanceof Subroutine) {
                    result.add(((Subroutine) proc).apply(se));
                }

                if (proc instanceof Closure) {
                    result.add(((Closure) proc).apply(se));
                }

            } else {
                if (proc instanceof Subroutine) {
                    result.add(((Subroutine) proc).apply(ConsCell.getInstance(se, EmptyList.getInstance())));
                }

                if (proc instanceof Closure) {
                    result.add(((Closure) proc).apply(ConsCell.getInstance(se, EmptyList.getInstance())));
                }
            }
        }

        // 要素が1つのみであれば、結果が得られているので返す
        if (sexpList.size() == 1) {
            return result.get(0);
        }

        if (proc instanceof Subroutine) {
            return ((Subroutine) proc).apply(ConsCell.toLispList(result));

        } else {
            return ((Closure) proc).apply(ConsCell.toLispList(result));
        }
    }

    @Override
    public String toString() {
        return "#<subr apply>";
    }
}
