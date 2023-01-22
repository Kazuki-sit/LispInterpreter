package lisp.eval;

import lisp.eval.specialform.*;
import lisp.eval.subroutine.*;
import lisp.eval.subroutine.List;
import lisp.eval.graphics.*;
import lisp.eval.graphics.GraphicsDrawLine;
import lisp.exception.LispException;

import java.util.HashMap;
import java.util.Map;

/**
 * 環境
 * 
 * @author tetsuya
 *
 */
public class Environment{
	// 現在のフレーム
	Map<Symbol, SExpression> envMap = new HashMap<>();
	// 前のフレーム
	Environment before = null;

	// コンストラクタ
	public Environment(Environment before) { // 子の環境を作成する際に利用
		this.before = before;
	}

	// Mainで呼び出されたときに、大域的なフレームに束縛する
	public Environment() {
		// 数値計算
		this.define(Symbol.getInstance("+"),        new Add());
		this.define(Symbol.getInstance("-"),        new Sub());
		this.define(Symbol.getInstance("*"),        new Mul());
		this.define(Symbol.getInstance("/"),        new Div());
		this.define(Symbol.getInstance("cos"),      new Cos());
		this.define(Symbol.getInstance("sin"),      new Sin());
		this.define(Symbol.getInstance("modulo"),   new Mod());
		this.define(Symbol.getInstance("round"),    new Round());
		this.define(Symbol.getInstance("abs"),      new Abs());

		// 条件式
		this.define(Symbol.getInstance(">"),        new More());
		this.define(Symbol.getInstance(">="),       new MoreEqual());
		this.define(Symbol.getInstance("<"),        new Less());
		this.define(Symbol.getInstance("<="),       new LessEqual());
		this.define(Symbol.getInstance("="),        new Equality());
		this.define(Symbol.getInstance("not"),      new Not());

		// ドット対
		this.define(Symbol.getInstance("car"),      new Car());
		this.define(Symbol.getInstance("cdr"),      new Cdr());
		this.define(Symbol.getInstance("cons"),     new Cons());

		// リスト
		this.define(Symbol.getInstance("list"),     new List());
		this.define(Symbol.getInstance("list-ref"), new List_ref());
		this.define(Symbol.getInstance("length"),   new Length());
		this.define(Symbol.getInstance("append"),   new Append());
		this.define(Symbol.getInstance("assoc"),    new Assoc());

		// 述語
		this.define(Symbol.getInstance("eq?"),      new Eq());
		this.define(Symbol.getInstance("null?"),    new IsNull());
		this.define(Symbol.getInstance("list?"),    new IsList());
		this.define(Symbol.getInstance("pair?"),    new IsPair());

		// 条件分岐
		this.define(Symbol.getInstance("if"),       new If());
		this.define(Symbol.getInstance("and"),      new And());
		this.define(Symbol.getInstance("or"),       new Or());
		this.define(Symbol.getInstance("cond"),     new Cond());

		// 特殊形式
		this.define(Symbol.getInstance("define"),   new Define());
		this.define(Symbol.getInstance("set!"),     new Set());
		this.define(Symbol.getInstance("quote"),    new Quote());
		this.define(Symbol.getInstance("begin"),    new Begin());
		this.define(Symbol.getInstance("lambda"),   new Lambda());
		this.define(Symbol.getInstance("let"),      new Let());

		// 高階関数
		this.define(Symbol.getInstance("map"),      new lisp.eval.subroutine.Map());
		this.define(Symbol.getInstance("apply"),    new Apply());

		// その他
		this.define(Symbol.getInstance("exit"),     new Exit());
		this.define(Symbol.getInstance("write"),    new Write());
		this.define(Symbol.getInstance("newline"),  new NewLine());
		this.define(Symbol.getInstance("sleep"),    new Sleep());

		// グラフィック
		this.define(Symbol.getInstance("display"),      new GraphicsDisplayCanvas());
		this.define(Symbol.getInstance("draw-line"),    new GraphicsDrawLine());
		this.define(Symbol.getInstance("draw-turtle"),  new GraphicsDrawTurtle());
		this.define(Symbol.getInstance("set-color"),    new GraphicsSetColor());
		this.define(Symbol.getInstance("canvas-clear"), new GraphicsCanvasClear());

	}

	/**
	 * 変数（シンボル）が束縛されている値を返す。
	 *
	 * @param symbol シンボル
	 * @return シンボルが束縛されている値
	 */
	public SExpression getValueOf(Symbol symbol) throws LispException {
		// 環境内にシンボルに束縛されている値があるか確認する
		if (envMap.containsKey(symbol)) {
			// この環境に束縛された値を返す
			return envMap.get(symbol);

		} else if (this.before != null) {
			// 大域的なフレームまで探索
			return this.before.getValueOf(symbol);

		} else {
			// 束縛された値がない場合、エラーがMainに投げられる
			throw new LispException("記号" + symbol + " に束縛された値がありません");
		}
	}

	/**
	 * 変数束縛
	 * 
	 * @param symbol シンボル
	 * @param sexp   束縛する値
	 */
	public void define(Symbol symbol, SExpression sexp) {
		envMap.put(symbol, sexp);
	}

	/**
	 * 変数の値の再定義
	 * 
	 * @param symbol シンボル
	 * @param sexp   束縛する値
	 */
	public void set(Symbol symbol, SExpression sexp) throws LispException {
		// 環境内にシンボルに束縛されている値があるか確認する
		if(envMap.containsKey(symbol)) {
			envMap.put(symbol, sexp);

		} else if(this.before != null) {
			// 大域的なフレームまで探索
			this.before.set(symbol, sexp);

		} else {
			// 束縛された値がない場合、エラーがMainに投げられる
			throw new LispException("記号" + symbol + " に束縛された値がありません");
		}
	}
}
