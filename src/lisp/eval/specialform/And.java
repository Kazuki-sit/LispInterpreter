package lisp.eval.specialform;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class And implements SpecialForm {

    /**
     * <p>評価値が偽になる式があったら、残りの式は評価されず、偽の値が返る</p>
     * <p>すべての式が評価されたら最後の式の評価値が返る</p>
     * <p>式が与えられなければ #t が返る</p>
     * <p>(and test ...)</p>
     *
     * @param sexp 評価するS式（リスト）
     * @param env 環境
     * @return 真偽値
     * @throws LispException
     */
    public SExpression apply(SExpression sexp, Environment env) throws LispException{
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        for(SExpression se : sexpList){
            SExpression test = Evaluator.eval(se, env);

            if (test instanceof Bool) {
                if (((Bool) test).getValue()) continue;
                return Bool.valueOf(false);

            } else {
                // 評価結果が真偽値でなければエラーを返す
                throw new LispException("特殊形式andの引数の評価値は真偽値である必要があります");
            }
        }

        return Bool.valueOf(true);
    }

    @Override
    public String toString() {
        return "#<syntax and>";
    }
}
