package lisp.eval.specialform;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Lambda implements SpecialForm {

    /**
     * <p>仮引数と本体から手続きを作成する</p>
     *
     * @param sexp 評価するS式（リスト）
     * @param env 現在の環境
     * @return 手続き
     * @throws LispException
     */
    public SExpression apply(SExpression sexp, Environment env) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if(sexpList.size() < 2){
            throw new LispException("特殊形式lambdaの引数は2個以上である必要があります");
        }

        SExpression formals = sexpList.get(0);
        SExpression body = ((ConsCell) sexp).getCdr();

        return Closure.getInstance(formals, body, env);
    }

    @Override
    public String toString() {
        return "#<syntax lambda>";
    }

}
