package lisp.eval.specialform;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Set implements SpecialForm {

	/**
	 * <p>定義済みの変数 symbol の値を更新する</p>
	 * <p>(set! symbol sexp)</p>
	 *
	 * @param sexp 束縛するシンボルとS式
	 * @param env 環境
	 * @return 束縛したシンボル
	 * @throws LispException
	 */
	public SExpression apply(SExpression sexp, Environment env) throws LispException {
		ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

		if(sexpList.size() != 2){
			throw new LispException("特殊形式set!の引数は2個である必要があります");
		}

		if(sexpList.get(0) instanceof Symbol){
			env.set((Symbol)sexpList.get(0), Evaluator.eval(sexpList.get(1),env));
			return sexpList.get(0);

		}else{
			throw new LispException("特殊形式set!の第一引数はシンボルである必要があります");
		}
	}

	@Override
	public String toString() {
		return "#<syntax set!>";
	}
}
