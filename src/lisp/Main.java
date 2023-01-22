package lisp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import lisp.eval.Environment;
import lisp.eval.Evaluator;
import lisp.eval.SExpression;
import lisp.exception.EndOfFileException;
import lisp.exception.LispException;
import lisp.reader.Reader;

/**
 * Lispインタプリタ
 * 
 * @author tetsuya
 *
 */
public class Main {
	/**
	 * 言語処理系の名前などを表示する。
	 *
	 * @return なし
	 */
	static void printGreetingMessage() {
		System.out.println("*************************************");
		System.out.println("");
		System.out.println("           A simple Lisp");
		System.out.println("");
		System.out.println("*************************************");
	}

	/**
	 * Lispインタプリタの対話的実行
	 *
	 * @param args コマンドライン引数。与えられても無視される。
	 * @throws IOException 入出力エラー
	 */
	public static void main(String[] args) throws IOException {
		printGreetingMessage();

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Reader reader = new Reader(in);
		Environment env = new Environment();


		// REPL(Read-Eval-Print-Loop)
		try {
			while (true) {
				try {
					System.out.print("lisp> ");
					SExpression exp = reader.read();
					SExpression value = Evaluator.eval(exp, env);
					System.out.println(value);
				} catch (EndOfFileException e) {
					break;
				} catch (LispException e) {
					System.err.println(e.getMessage());
				}
			}
		} catch (Exception e) {
		}
		in.close();
	}

}
