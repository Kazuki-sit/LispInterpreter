package lisp.eval.specialform;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Let implements SpecialForm {

    /**
     * <p>仮引数を局所変数として利用して手続きを作成する</p>
     *
     * @param sexp 評価するS式（リスト）
     * @param env 環境
     * @return 手続き
     * @throws LispException
     */
    public SExpression apply(SExpression sexp, Environment env) throws LispException {
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if(sexpList.size() < 2){
            throw new LispException("特殊形式letの引数は2個以上である必要があります");
        }

        ArrayList<SExpression> list = ConsCell.toArrayList(sexpList.get(0));
        SExpression body = ((ConsCell) sexp).getCdr();
        ArrayList<SExpression> keyList = new ArrayList<>();
        ArrayList<SExpression> valueList = new ArrayList<>();

        for(SExpression pair : list) {
            ArrayList<SExpression> dict = ConsCell.toArrayList(pair);
            keyList.add(dict.get(0));
            valueList.add(Evaluator.eval(dict.get(1),env));
        }

        SExpression formals = ConsCell.toLispList(keyList);
        Closure closure = Closure.getInstance(formals, body, env);

        return closure.apply(ConsCell.toLispList(valueList));
    }

    @Override
    public String toString() {
        return "#<syntax let>";
    }

}
