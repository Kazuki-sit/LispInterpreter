package lisp.eval.specialform;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Define implements SpecialForm {

    /**
     * <p>渡された環境で変数にS式を束縛する</p>
     * <p>Lambda式の省略記法では第一引数にシンボルと手続きの引数、第二引数に手続き本体を取る</p>
     * <p>(define symbol sexp)</p>
     * <p>(define (symbol args ...) (process))</p>
     *
     * @param sexp 束縛するシンボルとS式
     * @param env 環境
     * @return 束縛したシンボル
     */
    public SExpression apply(SExpression sexp, Environment env) throws LispException {

        ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

        if(sexpList.size() != 2){
            throw new LispException("特殊形式defineの引数は2個である必要があります");
        }

        SExpression arg1 = sexpList.get(0);
        SExpression arg2 = ((ConsCell) sexp).getCdr();

        // 通常のDefineのとき
        if (arg1 instanceof Symbol) {
            SExpression exp = Evaluator.eval(((ConsCell) arg2).getCar(), env);
            env.define((Symbol) arg1, exp);
            return arg1;
        }

        // Lambda式の省略記法のとき
        if (arg1 instanceof ConsCell) {
            SExpression symbol = ((ConsCell) arg1).getCar();
            SExpression formals = ((ConsCell) arg1).getCdr();
            SExpression closure = Closure.getInstance(formals, arg2, env);

            env.define((Symbol) symbol, closure);
            return symbol;
        }

        throw new LispException("手続きdefineの第一引数はシンボルかリストである必要があります");
    }

    @Override
    public String toString() {
        return "#<syntax define>";
    }
}
