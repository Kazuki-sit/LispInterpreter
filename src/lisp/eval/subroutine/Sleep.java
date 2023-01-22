package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Sleep implements Subroutine {
    /**
     * <p>引数の時間（ms）だけsleepを実行する</p>
     * <p>(sleep time_ms)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @return 停止時間
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException {

        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if (sexpList.size() != 1) {
            throw new LispException("手続きsleepの引数は1個である必要があります");
        }

        if (sexpList.get(0) instanceof Int) {
            try {
                Thread.sleep(((Int)sexpList.get(0)).getValue());
            } catch (InterruptedException e) {
                System.out.println("割り込み発生");
            }

        } else {
            throw new LispException("手続きsleepの引数はInt型である必要があります");
        }

        return sexpList.get(0);
    }

    @Override
    public String toString() {
        return "#<subr sleep>";
    }
}
