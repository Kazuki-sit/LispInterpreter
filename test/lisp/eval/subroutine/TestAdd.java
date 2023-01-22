package lisp.eval.subroutine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import lisp.SetUp;
import lisp.eval.*;
import lisp.eval.Float;
import lisp.exception.LispException;
import lisp.reader.Reader;

public class TestAdd {
	Reader createReader(String stdin) {
		StringReader sr = new StringReader(stdin);
		BufferedReader br = new BufferedReader(sr);
		return new Reader(br);
	}

	@Test
	public void addの束縛() throws IOException, LispException {
		String stdin = "+";

		Environment env = SetUp.getGlobalEnvironment();
		Reader reader = createReader(stdin);
		SExpression sexp = reader.read();

		SExpression actual = Evaluator.eval(sexp, env);

		System.out.println("lisp> " + stdin);
		System.out.println(actual);
	}

	@Test
	public void 引数が0個() throws IOException, LispException {
		String stdin = "(+)";

		Environment env = SetUp.getGlobalEnvironment();
		Reader reader = createReader(stdin);
		SExpression sexp = reader.read();

		SExpression actual = Evaluator.eval(sexp, env);

		System.out.println("lisp> " + stdin);
		System.out.println(actual);

		SExpression expected = Int.valueOf(0);
		assertThat(actual, is(expected));
	}

	@Test
	public void 引数が1個() throws IOException, LispException {
		String stdin = "(+ 1)";

		Environment env = SetUp.getGlobalEnvironment();
		Reader reader = createReader(stdin);
		SExpression sexp = reader.read();

		SExpression actual = Evaluator.eval(sexp, env);

		System.out.println("lisp> " + stdin);
		System.out.println(actual);

		SExpression expected = Int.valueOf(1);
		assertThat(actual, is(expected));
	}

	@Test
	public void 引数が2個() throws IOException, LispException {
		String stdin = "(+ 1 2)";

		Environment env = SetUp.getGlobalEnvironment();
		Reader reader = createReader(stdin);
		SExpression sexp = reader.read();

		SExpression actual = Evaluator.eval(sexp, env);

		System.out.println("lisp> " + stdin);
		System.out.println(actual);

		SExpression expected = Int.valueOf(3);
		assertThat(actual, is(expected));
	}

	@Test
	public void 引数が3個() throws IOException, LispException {
		String stdin = "(+ 1 (+ 2 3) (+ 4 5))";

		Environment env = SetUp.getGlobalEnvironment();
		Reader reader = createReader(stdin);
		SExpression sexp = reader.read();

		SExpression actual = Evaluator.eval(sexp, env);

		System.out.println("lisp> " + stdin);
		System.out.println(actual);

		SExpression expected = Int.valueOf(15);
		assertThat(actual, is(expected));
	}

	@Test
	public void 情報落ちの確認() throws IOException, LispException {
		String stdin = "(+ 123456789 0.123456789)";

		Environment env = SetUp.getGlobalEnvironment();
		Reader reader = createReader(stdin);
		SExpression sexp = reader.read();

		SExpression actual = Evaluator.eval(sexp, env);

		System.out.println("lisp> " + stdin);
		System.out.println(actual);

		SExpression expected = Float.valueOf(123456789.123456789f);
		assertThat(actual, is(expected));
	}

	@Test
	public void 整数と浮動小数点の混在() throws IOException, LispException {
		String stdin = "(+ 1 2.0)";

		Environment env = SetUp.getGlobalEnvironment();
		Reader reader = createReader(stdin);
		SExpression sexp = reader.read();

		SExpression actual = Evaluator.eval(sexp, env);

		System.out.println("lisp> " + stdin);
		System.out.println(actual);

		SExpression expected = Float.valueOf(3.0f);
		assertThat(actual, is(expected));
	}

	@Test
	public void 引数が浮動小数点() throws IOException, LispException {
		String stdin = "(+ 1.0 2.0)";

		Environment env = SetUp.getGlobalEnvironment();
		Reader reader = createReader(stdin);
		SExpression sexp = reader.read();

		SExpression actual = Evaluator.eval(sexp, env);

		System.out.println("lisp> " + stdin);
		System.out.println(actual);

		SExpression expected = Float.valueOf(3.0f);
		assertThat(actual, is(expected));
	}
}
