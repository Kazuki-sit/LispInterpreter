package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.exception.LispException;

import java.util.ArrayList;

public class List implements Subroutine {

	/**
	 * <p>引数を並べたリストを返す</p>
	 * <p>(list sexp1 sexp2 ...)</p>
	 *
	 * @param sexp 評価するS式（リスト）
	 * @return S式（リスト）
	 * @throws LispException
	 */
	public SExpression apply(SExpression sexp) throws LispException {
		ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

		if(sexpList.size() < 1){
			throw new LispException("手続きlistの引数は1個以上である必要があります");
		}

		return sexp;
	}

	@Override
	public String toString() {
		return "#<subr list>";
	}
}
