package lisp.eval;

import lisp.exception.LispException;

import java.util.ArrayList;

/**
 *
 * クロージャ
 *
 */
public class Closure implements SExpression {

    // 仮引数
    private SExpression formals;

    // 手続き本体
    private SExpression body;

    // 手続きを作成したときのフレームへのポインタ
    private Environment env;

    private Closure(SExpression formals, SExpression body, Environment env) {
        this.formals = formals;
        this.body = body;
        this.env = env;
    }

    /**
     * <p>クロージャのインスタンスを得る</p>
     *
     * @param formals 仮引数
     * @param body 手続き本体
     * @param env 現在の環境
     * @return 作成された手続き
     */
    public static Closure getInstance(SExpression formals, SExpression body ,Environment env) {
        return new Closure(formals, body, env);
    }

    /**
     * <p>渡された実引数で手続きを実行する</p>
     *
     * @param sexp 実引数
     * @return クロージャで作成された手続きを実引数で評価した結果
     * @throws LispException
     */
    public SExpression apply(SExpression sexp) throws LispException{
        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);
        Environment newEnv = new Environment(this.env);

        if (ConsCell.isList(this.formals)) {
            // 仮引数がリストであれば、ArrayListに変換する
            ArrayList<SExpression> formalList = ConsCell.toArrayList(this.formals);

            // 仮引数と実引数の数が違う場合はエラーを返す
            if (formalList.size() != sexpList.size()) {
                throw new LispException("仮引数がリストの場合はクロージャに渡す引数の数は仮引数と同じである必要があります");
            }

            // 仮引数と実引数を束縛する
            for (int i = 0; i < formalList.size(); i++) {
                newEnv.define((Symbol) formalList.get(i), sexpList.get(i));
            }

        } else {
            // 仮引数がリストでなければ、最後の要素に残りの引数を結びつける
            SExpression formalTmp = this.formals;
            SExpression argsTmp = sexp;

            while(true) {
                if (formalTmp instanceof ConsCell) {
                    // 実引数が仮引数より少なかったらエラーを返す
                    if (argsTmp instanceof EmptyList) {
                        throw new LispException("クロージャの実引数は仮引数以上である必要があります");
                    }

                    // 実引数と仮引数のCarを環境に束縛する
                    SExpression symbol = ((ConsCell) formalTmp).getCar();
                    SExpression se = ((ConsCell) argsTmp).getCar();

                    formalTmp = ((ConsCell) formalTmp).getCdr();
                    argsTmp = ((ConsCell) argsTmp).getCdr();

                    newEnv.define((Symbol) symbol, se);

                } else {
                    // 仮引数が最後の要素であれば残りの実引数を渡す
                    newEnv.define((Symbol) formalTmp, argsTmp);
                    break;
                }
            }
        }

        // 手続き本体を実行する
        SExpression result = null;
        if (this.body instanceof ConsCell) {
            ArrayList<SExpression> bodyList = ConsCell.toArrayList(this.body);
            for (SExpression body : bodyList) {
                result = Evaluator.eval(body, newEnv);
            }

        } else {
            result = Evaluator.eval(this.body, newEnv);
        }

        return result;
    }

    @Override
    public String toString() {
        return "#<closure #f>";
    }
    
}
