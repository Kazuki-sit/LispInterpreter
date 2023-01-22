package lisp.eval.specialform;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class If implements SpecialForm {

    /**
     * <p>条件式が真ならS式thenを評価し、偽ならS式elseを評価する</p>
     * <p>(if predicate then else)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @param env 環境
     * @return S式
     * @throws LispException
     */
    public SExpression apply(SExpression sexp, Environment env) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if ((sexpList.size() < 2) || (3 < sexpList.size())) {
            throw new LispException("特殊形式ifの引数は2個か3個である必要があります");
        }

        SExpression pred = Evaluator.eval(sexpList.get(0), env);

        if (pred instanceof Bool) {
            if(((Bool) pred).getValue()) {
                // 真の場合はthenを評価する
                return Evaluator.eval(sexpList.get(1), env);

            } else {
                // 偽の場合はelseを評価する
                if (sexpList.size() == 2) return Undef.getInstance();
                return Evaluator.eval(sexpList.get(2), env);
            }

        } else {
            // 条件式の戻り値が真偽値でなければエラーを返す
            throw new LispException("条件式の戻り値は真偽値である必要があります");
        }
    }

    @Override
    public String toString() {
        return "#<syntax if>";
    }
}
