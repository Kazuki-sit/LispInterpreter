package lisp.eval;

import lisp.eval.graphics.Display;
import lisp.exception.LispException;

/**
 * 評価器
 * 
 * @author tetsuya
 *
 */
public class Evaluator {
	/**
	 * <p>
	 * 引数の環境の下で引数のS式を評価する。
	 * </p>
	 * 
	 * <p>
	 * S式xの評価値をE[x]とすると、E[x]は次のように定義される。
	 * </p>
	 * <ul>
	 * <li>xが整数値の場合、E[x] = x</li>
	 * <li>xが真理値の場合、E[x] = x</li>
	 * <li>xが空リストの場合、E[x] = 空リスト</li>
	 * <li>xが未定義値の場合、E[x] = 未定義値</li>
	 * <li>xがクロージャの場合、E[x] = x</li>
	 * <li>xがサブルーチンの場合、E[x] = x</li>
	 * <li>xが特殊形式の場合、E[x] = x</li>
	 * <li>xが記号の場合、E[x] = 環境の下で記号xに束縛された値</li>
	 * <li>xが空リストではないリスト(x0 x1 ... xn)の場合
	 * <ul>
	 * <li>E[x0]が特殊形式の場合、特殊形式を引数x1, ..., xn に適用した結果</li>
	 * <li>E[x0]がサブルーチン（組み込み手続き）の場合、サブルーチンを引数E[x1], ..., E[xn]に適用した結果</li>
	 * <li>E[x0]がクロージャの場合、クロージャを引数E[x1], ..., E[xn]に適用した結果</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * 
	 * @param sexp S式
	 * @param env  環境
	 * @return 評価値(S式)
	 */
	public static SExpression eval(SExpression sexp, Environment env) throws LispException {

		// 空リスト、整数、浮動小数点、真偽値、クロージャ、フレームの評価
		if(sexp instanceof EmptyList    ||
		   sexp instanceof Int          ||
		   sexp instanceof Float        ||
		   sexp instanceof Bool         ||
		   sexp instanceof Subroutine   ||
		   sexp instanceof SpecialForm  ||
		   sexp instanceof Closure      ||
		   sexp instanceof Display        ) {

			return sexp;
		}

		// 記号の評価
		if(sexp instanceof Symbol) {
			return env.getValueOf((Symbol) sexp);
		}

		// ドット対（リスト）で受け取る
		if(sexp instanceof ConsCell){
			SExpression process  = eval(((ConsCell) sexp).getCar(), env);
			SExpression cdr  = ((ConsCell) sexp).getCdr();

			// 特殊形式の評価
			if (process instanceof SpecialForm) {
				return ((SpecialForm) process).apply(cdr, env);
			}

			// 組み込み手続きの評価
			if (process instanceof Subroutine) {
				return ((Subroutine) process).apply(evalList(cdr, env));
			}

			// クロージャの評価
			if (process instanceof Closure) {
				return ((Closure) process).apply(evalList(cdr, env));
			}
		}

		// この上に評価機能を実装していけば、実装されていないものが全てエラーで返される
		// throwされたエラーはMainでキャッチされる
		throw new LispException("評価できませんでした" + " : " + sexp.toString());
	}

	/**
	 * 標準入力から入力されたリストの要素をすべて評価して返す
	 *
	 * @param sexp S式
	 * @param env 環境
	 * @return 評価されたリスト
	 */
	public static SExpression evalList(SExpression sexp, Environment env) throws LispException {
		if (sexp instanceof ConsCell) {
			SExpression car = ((ConsCell) sexp).getCar();
			SExpression cdr = ((ConsCell) sexp).getCdr();
			return ConsCell.getInstance(eval(car, env), evalList(cdr, env));

		} else {
			// cdr部がドット対でなければ最後の要素とみなす
			return eval(sexp, env);
		}
	}
}
