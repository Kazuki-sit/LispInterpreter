package lisp.eval.specialform;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Cond implements SpecialForm{

    /**
     * <p>はじめて偽 (#f) でない値となる test があったら、その clause の exp を順に評価する</p>
     * <p>最後に評価した exp の値を、この特殊形式の評価値とする</p>
     * <p>もし全ての test の評価値が偽 (#f) となり、最後の clause が (else exp ...) でなければ、未定義の値をこの特殊形式の評価値とする</p>
     * <p>もし全ての test の評価値が偽 (#f) となり、最後の clause が (else exp ...) であれば、その clause の exp を順に評価する. </p>
     * <p>最後に評価した exp の値を、この特殊形式の評価値とする</p>
     * <p>(cond (clause) ...)</p>
     * <p>clauseは(test exp ...) (else exp ...) のいずれかの形をとる</p>
     *
     * @param sexp 評価するS式（リスト）
     * @param env 環境
     * @return 真であったときの式の評価値
     * @throws LispException
     */
    public SExpression apply(SExpression sexp, Environment env) throws LispException{
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if(sexpList.size() < 1){
            throw new LispException("特殊形式cond引数は1個以上である必要があります");
        }

        for(SExpression se : sexpList){
            ArrayList<SExpression> clause = ConsCell.toArrayList(se);

            if(clause.size() < 2){
                throw new LispException("特殊形式condのclauseは2個以上である必要があります");
            }

            SExpression pred = clause.get(0);
            clause.remove(0);

            // clauseがelseだったとき
            if (pred instanceof Symbol && ((Symbol) pred).getName().equals("else")) {
                SExpression result = null;
                for (SExpression exp : clause) result = Evaluator.eval(exp, env);
                return result;
            }

            // 条件式を評価する
            pred = Evaluator.eval(pred, env);

            if (pred instanceof Bool) {
                if (((Bool) pred).getValue()) {
                    // 真なら、式を評価して値を返す
                    SExpression result = null;
                    for (SExpression exp : clause) result = Evaluator.eval(exp, env);
                    return result;
                }

            } else {
                // 真偽値でなければエラーを返す
                throw new LispException("特殊形式condのclauseの第一要素は述語である必要があります");
            }
        }

        return Undef.getInstance();
    }

    @Override
    public String toString() {
        return "#<syntax cond>";
    }
}
