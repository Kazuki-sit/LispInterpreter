package lisp.eval.subroutine;

import lisp.eval.*;
import lisp.eval.Float;
import lisp.exception.LispException;

import java.util.ArrayList;
import java.util.Objects;

public class Eq implements Subroutine {

	/**
	 * <p>obj1 と obj2 とが主記憶の同じ場所に記録される同じ型のデータならtrueを返す</p>
	 * <p>obj1 と obj2 とが両方とも等しい数値, 等しい真理値, 等しい記号, (), ならばtrueを返す</p>
	 * <p>(eq? obj1 obj2)</p>
	 *
	 * @param sexp 評価するS式（リスト）
	 * @return 真偽値
	 * @throws LispException
	 */
	public SExpression apply(SExpression sexp) throws LispException {
		ArrayList<SExpression> sexpList = ConsCell.toArrayList(sexp);

		if (sexpList.size() != 2) {
			throw new LispException("手続きeq?の引数は2個である必要があります");
		}

		SExpression obj1 = sexpList.get(0);
		SExpression obj2 = sexpList.get(1);

		if(obj1 == obj2) {
			return Bool.valueOf(true);
		}

		if (obj1 instanceof Int && obj2 instanceof Int) {
			if (Objects.equals(((Int) obj1).getValue(), ((Int) obj2).getValue())) {
				return Bool.valueOf(true);
			}
		}

		if (obj1 instanceof Float && obj2 instanceof Float) {
			if (Objects.equals(((Float) obj1).getValue(), ((Float) obj2).getValue())) {
				return Bool.valueOf(true);
			}
		}

		if (obj1 instanceof Bool && obj2 instanceof Bool) {
			if (((Bool) obj1).getValue() == ((Bool) obj2).getValue()) {
				return Bool.valueOf(true);
			}
		}

		if (obj1 instanceof Symbol && obj2 instanceof Symbol) {
			if (Objects.equals(((Symbol) obj1).getName(), ((Symbol) obj2).getName())) {
				return Bool.valueOf(true);
			}
		}

		if (obj1 instanceof EmptyList && obj2 instanceof EmptyList) {
			return Bool.valueOf(true);
		}

		// 上記以外のパターンはすべてfalseを返す
		return Bool.valueOf(false);
	}

	@Override
	public String toString() {
		return "#<subr eq?>";
	}

}
