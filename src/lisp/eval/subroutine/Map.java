package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Map implements Subroutine{

    /**
     * <p>proc は n 引数をとる手続き, 各 list_i は長さ m のリストとする</p>
     * <p>(map proc list1 list2 ... list_n)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return 各リストの評価値
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if(sexpList.size() < 2){
            throw new LispException("手続きmapの引数は2個以上である必要があります");
        }

        // 第一引数を抜き出す
        SExpression proc = sexpList.get(0);
        sexpList.remove(0);

        // 第一引数が手続きでなければエラーを返す
        if (!(proc instanceof Subroutine || proc instanceof Closure)) {
            throw new LispException("手続きmapの第一引数は手続きである必要があります");
        }

        // 引数のリスト、評価結果の格納用
        ArrayList<ArrayList<SExpression>> list = new ArrayList<>();
        ArrayList<SExpression> result = new ArrayList<>();

        // 引数のリストを格納する
        for (SExpression exp : sexpList) {
            list.add(ConsCell.toArrayList(exp));
        }

        // 引数のリストの要素数が合わなければエラーを返す
        int size = 0;
        for (ArrayList<SExpression> exp : list) {
            if (size == 0) {
                size = exp.size();
            } else {
                if (size != exp.size()) {
                    throw new LispException("手続きmapのリストの引数の要素数は同じである必要があります");
                }
            }
        }

        // 渡された手続きを実行する
        for (int i = 0; i < size; i++) {
            ArrayList<SExpression> input = new ArrayList<>();
            for (int j = 0; j < list.size(); j++) {
                input.add(list.get(j).get(i));
            }

            if (proc instanceof Subroutine) {
                result.add(((Subroutine) proc).apply(ConsCell.toLispList(input)));
            }

            if (proc instanceof Closure) {
                result.add(((Closure) proc).apply(ConsCell.toLispList(input)));
            }
        }

        return ConsCell.toLispList(result);
    }

    @Override
    public String toString() {
        return "#<subr map>";
    }
}