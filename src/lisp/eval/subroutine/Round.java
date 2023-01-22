package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.eval.Float;
import lisp.exception.LispException;

import java.util.ArrayList;

public class Round implements Subroutine {

	/**
	 * <p>小数点以下を四捨五入して返す</p>
	 * <p>(round sexp)</p>
	 *
	 * @param sexp 評価するS式（リスト）
	 * @return 評価されたS式（Int）
	 * @throws LispException
	 */
	public SExpression apply(SExpression sexp) throws LispException {
		ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

		if(sexpList.size() != 1){
			throw new LispException("手続きroundの引数は1個である必要があります");
		}

		SExpression se = sexpList.get(0);
		int result;

		if (se instanceof Int) {
			return se;

		} else if(se instanceof Float) {
			result = Math.round(((Float) se).getValue());
			return Int.valueOf(result);

		} else {
			// 数値でなければエラーを投げる
			throw new LispException("手続きroundの引数は数値である必要があります");
		}
	}

	@Override
	public String toString() {
		return "#<subr round>";
	}
}
